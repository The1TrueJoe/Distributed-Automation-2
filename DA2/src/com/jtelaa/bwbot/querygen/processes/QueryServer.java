package com.jtelaa.bwbot.querygen.processes;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.bwbot.querygen.Main;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;

/**
 * This process serves the requested queries
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.bwbot.querygen.processes.RequestClient
 * @see com.jtelaa.bwbot.querygen.processes.QueryGenerator
 */

public class QueryServer extends Thread {
    
    /** */
    private static String log_prefix = "Query Server: ";

    /** Bot queue */
    public volatile static Queue<Bot> bot_queue;

    /** UDP Client  */
    private ClientUDP cmd_tx;

    /**
     * Adds a query into the queue <p>
     * Many queries are added. They will be served to the bots as needed.
     * 
     * @param query Search query to enque
     * 
     * @deprecated No lpnger needed on this class
     */

    @Deprecated
    public synchronized static void addQuery(Query query) {
        if (QueryGenerator.query_queue.size() < QueryGenerator.MAX_QUERY_QUEUE_SIZE) {
            // Add to queue if the que is under specified size
            QueryGenerator.query_queue.add(query);

        } 
    }

    /**
     * Checks if the queue size is small enough to
     * where it could accept another query
     * 
     * @return if the query queue size is less than the max
     * @deprecated No longer necessary
     */

    @Deprecated
    public synchronized static boolean readyForQuery() { return QueryGenerator.query_queue.size() > QueryGenerator.MAX_QUERY_QUEUE_SIZE; }

    /**
     * Adds a bot into the queue <p>
     * Whenever the request server sees a new request, it is enqued
     * 
     * @param bot bot to enque
     */
    
    public synchronized static void addBot(Bot bot) {
        if (NetTools.isValid(bot.ip)) {
            // If bot has valid ip, add it to the queue
            bot_queue.add(bot);

        }
    }

    public void run() {
        // Ready
        Log.sendMessage("Query Server: Ready", ConsoleColors.GREEN);

        // Setup lists
        bot_queue = new LinkedList<>();

        // Constantly fill requests
        while (run) {
            fillRequest();
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean receiverReady() { return run; }

    /**
     * Fills the search query request by establishing a connection
     * with the bot
     */

    private void fillRequest() {
        // If no requests or queries, wait
        if (bot_queue.size() == 0 || QueryGenerator.query_queue.size() == 0) {
            MiscUtil.waitasec(.10);
            return;
        } 

        // Pick top off queue
        Query query_to_send = QueryGenerator.query_queue.poll();
        Bot bot_to_serve = bot_queue.poll();

        // Notification
        Log.sendMessage(log_prefix + "Serving " + bot_to_serve.ip, ConsoleColors.YELLOW);

        // Setup client
        cmd_tx = new ClientUDP(bot_to_serve.ip, BWPorts.QUERY_RECEIVE.checkForPreset(Main.my_config, "receive_port"), log_prefix, ConsoleColors.YELLOW);

        // Send and then close
        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(query_to_send.getQuery());
            cmd_tx.closeClient();
            Log.sendMessage(log_prefix + "Done serving " + bot_to_serve.ip, ConsoleColors.YELLOW);

        }
    }
    
}
