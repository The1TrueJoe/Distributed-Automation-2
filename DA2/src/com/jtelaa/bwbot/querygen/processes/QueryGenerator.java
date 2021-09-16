package com.jtelaa.bwbot.querygen.processes;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.bwbot.querygen.Main;
import com.jtelaa.bwbot.querygen.searches.SearchHandler;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;

/**
 * This process generates random search queries to be searched by clients
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.bwbot.querygen.processes.QueryServer
 * @see com.jtelaa.bwbot.querygen.processes.RequestServer
 */

public class QueryGenerator extends Thread {

    /** Query queue */
    public volatile static Queue<Query> query_queue;

    /** */
    private static String log_prefix = "Query Generator: ";

    /** Maximum size of the query queu */
    public volatile static int MAX_QUERY_QUEUE_SIZE = 10000;

    public void run() {
        // Random
        Random rand = new Random();
        query_queue = new LinkedList<>();
        int rng;

        // Wait
        Log.sendMessage(log_prefix + "Generator Wait 30s", ConsoleColors.GREEN_BRIGHT);
        MiscUtil.waitasec(15);
        Log.sendMessage(log_prefix + "Generator Wait 15s", ConsoleColors.GREEN_BRIGHT);
        MiscUtil.waitasec(15);
        Log.sendMessage(log_prefix + "Generator Wait Time Complete", ConsoleColors.GREEN_BRIGHT);

        // Max Query Size
        MAX_QUERY_QUEUE_SIZE = Integer.parseInt(Main.my_config.getProperty("query_queue_size", "1000"));
        Log.sendMessage(log_prefix + "Queue size set to " + MAX_QUERY_QUEUE_SIZE, ConsoleColors.PURPLE_BOLD_BRIGHT);

        // While running
        run = true;
        while(run) {
            // If ready for a query
            if (query_queue.size() < MAX_QUERY_QUEUE_SIZE) {
                // Generate random case
                rng = rand.nextInt(100);

                if (rng <= 50) {
                    // Add a single query
                    Log.sendMessage(log_prefix + "Generating (1) - " + query_queue.size() + "/" + MAX_QUERY_QUEUE_SIZE, ConsoleColors.PURPLE);
                    query_queue.add(generate());

                } else {
                    int count = rand.nextInt(rng);

                    // Generate random query
                    Log.sendMessage(log_prefix + "Generating ("+count+") - " + query_queue.size() + "/" + MAX_QUERY_QUEUE_SIZE, ConsoleColors.PURPLE);
                    Query[] queries = generate(count);
                    
                    // Add queries
                    for (Query query : queries) {
                        query_queue.add(query);

                    }
                }

            } else {
                if (Log.history.get(Log.history.size()-1).contains("Generation Stopped")) {
                    MiscUtil.waitamoment(50000);

                } else {
                    // Wait if not ready
                    Log.sendMessage(log_prefix + "Generation Stopped (" + query_queue.size() + ")", ConsoleColors.PURPLE_BOLD_BRIGHT);
                    MiscUtil.waitamoment(10000);

                }

                /*

                Log.sendSysMessage(log_prefix + "Reading 10 Example Queries");

                for (int i = 0; i < 10; i ++) {
                    Log.sendSysMessage(log_prefix + query_queue.poll());

                }

                Log.sendSysMessage(log_prefix + "Done with examples");

                */

            }
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopGen() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean generatorReady() { return run; }
    
    /**
     * Generates a random search query
     * 
     * @return Random query
     */

    private Query generate() {
        return SearchHandler.getRandomSearch();
    }

    /**
     * Generates a list of random search queries
     * 
     * @param count Size of lisr
     * 
     * @return Array of searches
     */

    private Query[] generate(int count) {
        return SearchHandler.getRandomSearches(count);

    }
    
}
