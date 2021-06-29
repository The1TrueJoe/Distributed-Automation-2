package com.jtelaa.da2.querygen;

import com.jtelaa.da2.lib.misc.MiscUtil;
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
        while(run) {
            if (QueryServer.readyForQuery()) {
                QueryServer.addQuery(generate());

            } else {
                MiscUtil.waitasec();

            }
        }
    }

    private boolean run = true;
    public synchronized void stopGen() { run = false; }

    private Query generate() {
        return new Query("test");
    }
    
}
