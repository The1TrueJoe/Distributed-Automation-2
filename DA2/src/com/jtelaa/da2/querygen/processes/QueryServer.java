package com.jtelaa.da2.querygen.processes;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.bot.plugin.bw.util.BWPorts;
import com.jtelaa.da2.director.botmgmt.Bot;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.querygen.util.Query;

/**
 * This process serves the requested queries
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.da2.redemption_manager.processes.RequestClient.java
 * @see com.jtelaa.da2.querygen.QueryGenerator.java
 */

 // TODO comment

public class QueryServer extends Thread {

    private volatile static Queue<Query> query_queue;
    private volatile static Queue<Bot> bot_queue;

    private ClientUDP cmd_tx;

    /**
     * Adds a query into the queue <p>
     * Many queries are added. They will be served to the bots as needed.
     * 
     * @param query Search query to enque
     */

    public synchronized static void addQuery(Query query) {
        if (query_queue.size() < QueryGenerator.MAX_QUERY_QUEUE_SIZE) {
            query_queue.add(query);

        } else {
            Log.sendMessage("Max queue reached!");
            
        }
    }

    /**
     * Checks if the queue size is small enough to
     * where it could accept another query
     * 
     * @return if the query queue size is less than the max
     */

    public synchronized static boolean readyForQuery() { return query_queue.size() < QueryGenerator.MAX_QUERY_QUEUE_SIZE; }

    /**
     * Adds a bot into the queue <p>
     * Whenever the request server sees a new request, it is enqued
     * 
     * @param bot bot to enque
     */
    
    public synchronized static void addBot(Bot bot) {
        if (NetTools.isValid(bot.getIP())) {
            bot_queue.add(bot);

        }
    }

    public void run() {
        query_queue = new LinkedList<>();
        bot_queue = new LinkedList<>();

        while (run) {
            fillRequest();
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized static boolean receiverReady() { return run; }
    // TODO Implement

    /**
     * Fills the search query request by establishing a connection
     * with the bot
     */

    private void fillRequest() {
        if (bot_queue.size() == 0) {
            MiscUtil.waitasec(.10);
            return;
        } 

        if (query_queue.size() == 0 && bot_queue.size() > 0) {
            query_queue.add(new Query("google"));       // Default search query
        }

        Query query_to_send = query_queue.poll();
        Bot bot_to_serve = bot_queue.poll();

        cmd_tx = new ClientUDP(bot_to_serve.getIP(), BWPorts.QUERY_RECEIVE.getPort());

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(query_to_send.getQuery());
            cmd_tx.closeClient();

        }
    }
    
}
