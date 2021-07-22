package com.jtelaa.da2.bot.util;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.director.botmgmt.MgmtMessages;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.SysPorts;
import com.jtelaa.da2.lib.net.client.ClientUDP;

/**
 * Sends out a signal to show the bot is alive
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class Heartbeat extends Thread {

    public static int HEARTBEAT_INTERVAL = 60000;

    private ClientUDP heart;
    
    public void run() {
        // Setup
        heart = new ClientUDP(Main.me.getHearbeatIP(), SysPorts.HEARTBEAT);
        heart.startClient();

        while (run) {
            heart.sendMessage(MgmtMessages.BEAT.getMessage() + " " + Main.me.getID());
            MiscUtil.waitamoment(HEARTBEAT_INTERVAL);

        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopHeart() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean heartReady() { return run; }


}
