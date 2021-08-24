package com.jtelaa.bwbot.querygen.processes;

import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
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
        ServerUDP server = new ServerUDP(BWPorts.QUERY_REQUEST, "Query Request Server: ", ConsoleColors.GREEN);
        
        // Bot address var
        String bot_address;

        // If server is ready
        if (server.startServer()) {
            while (run) {
    
                // If a request messahe is receiver
                String response = server.getMessage();
                if (response.equals(BWMessages.QUERY_REQUEST_MESSAGE.getMessage())) {
                    bot_address = server.getClientAddress();

                    QueryServer.addBot(new Bot(bot_address));
                    Log.sendSysMessage("Request Server: Request from " + bot_address, ConsoleColors.YELLOW);
                
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
