package com.jtelaa.da2.bot.deprecated;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.SysPorts;

/**
 * Sends out a signal to show the bot is alive
 * 
 * @since 2
 * @author Joseph
 * 
 * @deprecated NEW Heartbeat will be based off of ICMP
 */

 @Deprecated
public class Heartbeat extends Thread {

    /** Heartbeat Interval */
    public static int HEARTBEAT_INTERVAL = 60000;

    /** Client */
    private ClientUDP heart;
    
    public void run() {
        // Setup
        heart = new ClientUDP(Main.me.config.getProperty("heart_beat_ip", "172.16.2.1"), SysPorts.HEARTBEAT);
        heart.startClient();

        // Send beats
        while (run) {
            heart.sendMessage(MgmtMessages.BEAT.getMessage() + " " + Main.me.id);
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
