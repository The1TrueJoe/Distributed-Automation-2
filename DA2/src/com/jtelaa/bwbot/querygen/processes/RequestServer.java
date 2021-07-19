package com.jtelaa.bwbot.querygen.processes;

import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.da2.director.botmgmt.Bot;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * This process accepts the requests for the query.
 * It enques them to the query server if the request is valid.
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.da2.querygen.QueryServer.java
 * @see com.jtelaa.da2.querygen.QueryGenerator.java
 */

public class RequestServer extends Thread {

    public void run() {
        // Setup server
        ServerUDP server = new ServerUDP(BWPorts.QUERY_REQUEST.getPort());
        
        // If server is ready
        if (server.startServer()) {
            while (run) {
    
                // If a request messahe is receiver
                String response = server.getMessage();
                if (response.equals(BWMessages.QUERY_REQUEST_MESSAGE.getMessage())) {
                    QueryServer.addBot(new Bot(server.getClientAddress()));
                
                }
            }

        } else {
            // Is not ready
            run = false;

        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopServer() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean serverReady() { return run; }
    
}
