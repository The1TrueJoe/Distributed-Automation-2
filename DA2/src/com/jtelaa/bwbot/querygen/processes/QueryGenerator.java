package com.jtelaa.bwbot.querygen.processes;

import java.util.Random;

import com.jtelaa.bwbot.querygen.searches.SearchHandler;
import com.jtelaa.bwbot.querygen.util.Query;
import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 * This process generates random search queries to be searched by clients
 * 
 * @since 2
 * @author Josepj
 * 
 * @see com.jtelaa.da2.querygen.QueryServer.java
 * @see com.jtelaa.da2.redemption_manager.processes.RequestClient.java
 */

 // TODO comment

public class QueryGenerator extends Thread {

    public volatile static int MAX_QUERY_QUEUE_SIZE = 10000;

    public void run() {
        Random rand = new Random();
        int rng;

        while(run) {
            if (QueryServer.readyForQuery()) {
                rng = rand.nextInt(100);

                if (rng <= 50) {
                    QueryServer.addQuery(generate());

                } else {
                    Query[] queries = generate(rand.nextInt(rand.nextInt(rng)));
                    
                    for (Query query : queries) {
                        QueryServer.addQuery(query);

                    }
                }

            } else {
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
    // TODO Implement
    
    private Query generate() {
        return SearchHandler.getRandomSearch();
    }

    private Query[] generate(int count) {
        return SearchHandler.loadSearches(count);

    }
    
}
