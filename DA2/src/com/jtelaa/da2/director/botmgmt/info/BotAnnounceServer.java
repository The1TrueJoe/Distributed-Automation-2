package com.jtelaa.da2.director.botmgmt.info;

import com.jtelaa.da2.director.Main;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * This process accepts the bot announcements.
 * It adds them to the list of managed bots
 * 
 * @since 2
 * @author Joseph
 * 
 */

public class BotAnnounceServer extends Thread {

    public void run() {
        // Server
        ServerUDP server = new ServerUDP(SysPorts.ENROLL);

        while (!run) {
            MiscUtil.waitasec();
            
        }
        
        // If ready
        if (server.startServer()) {
            while (run) {
                // Get message
                Object response = server.getObject();

                // Add it to the list
                if (Bot.class.isInstance(response)) {
                    Main.bt_mgmt.add((Bot) response);
                    
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

    
}
