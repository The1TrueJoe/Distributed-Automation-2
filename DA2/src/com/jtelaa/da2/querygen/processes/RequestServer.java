package com.jtelaa.da2.querygen.processes;

import com.jtelaa.da2.bot.plugin.bw.util.BWPorts;
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

    public static final String QUERY_REQUEST_MESSAGE = "Gimme da Query!";

    public void run() {
        ServerUDP server = new ServerUDP(BWPorts.QUERY_REQUEST.getPort());
        
        if (server.startServer()) {
            while (run) {
                String response = server.getMessage();

                if (response.equals(QUERY_REQUEST_MESSAGE)) {
                    QueryServer.addBot(new Bot(server.getClientAddress()));
                
                }
            }
        }
    }

    private boolean run = true;
    public synchronized void stopServer() { run = false; }
    
}
