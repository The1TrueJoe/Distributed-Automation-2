package com.jtelaa.da2.querygen.processes;

import java.util.Random;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.querygen.searches.SearchHandler;
import com.jtelaa.da2.querygen.util.Query;

/**
 * This process generates random search queries to be searched by clients
 * 
 * @since 2
 * @author Josepj
 * 
 * @see com.jtelaa.da2.querygen.QueryServer.java
 * @see com.jtelaa.da2.querygen.RequestServer.java
 */

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

    private boolean run = true;
    public synchronized void stopGen() { run = false; }

    private Query generate() {
        return SearchHandler.getRandomSearch();
    }

    private Query[] generate(int count) {
        return SearchHandler.loadSearches(count);

    }
    
}
