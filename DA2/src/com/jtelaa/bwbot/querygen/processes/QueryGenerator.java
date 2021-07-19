package com.jtelaa.bwbot.querygen.processes;

import java.util.Random;

import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.bwbot.querygen.searches.SearchHandler;
import com.jtelaa.da2.lib.misc.MiscUtil;

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

    /** Maximum size of the query queu */
    public volatile static int MAX_QUERY_QUEUE_SIZE = 10000;

    public void run() {
        // Random
        Random rand = new Random();
        int rng;

        // While running
        while(run) {
            // If ready for a query
            if (QueryServer.readyForQuery()) {
                // Generate random case
                rng = rand.nextInt(100);

                if (rng <= 90) {
                    // Add a single query
                    QueryServer.addQuery(generate());

                } else {
                    // Generate random query
                    Query[] queries = generate(rand.nextInt(rand.nextInt(rng)));
                    
                    // Add queries
                    for (Query query : queries) {
                        QueryServer.addQuery(query);

                    }
                }

            } else {
                // Wait if not ready
                MiscUtil.waitasec();

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
