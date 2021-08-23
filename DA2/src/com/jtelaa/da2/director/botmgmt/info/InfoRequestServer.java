package com.jtelaa.da2.director.botmgmt.info;

import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * This process accepts the requests for info.
 * It enques them to the info response server if the request is valid.
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.bwbot.bwlib.bw.util.BWMessages
 * @see com.jtelaa.bwbot.bw_manager.processes.InfoResponseClient
 */

 // TODO comment

public class InfoRequestServer extends Thread {

    public void run() {
        ServerUDP server = new ServerUDP(SysPorts.INFO);
        
        if (server.startServer()) {
            while (run) {
                String response = server.getMessage();

                if (MiscUtil.notBlank(response)) {
                    InfoResponseClient.addRequest(response, new Bot(server.getClientAddress()));
                    
                }
            }
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean receiverReady() { return run; }
    // TODO Implement
    
}
