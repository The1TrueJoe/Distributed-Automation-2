package com.jtelaa.da2.bot.util;

import com.jtelaa.da2.bot.main.Main;
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
    public static String HEARTBEAT_MESSAGE = "Alive!";

    private ClientUDP heart;
    
    public void run() {
        // Setup
        heart = new ClientUDP(Main.me.getHearbeatIP(), SysPorts.HEARTBEAT);
        heart.startClient();

        while (run) {
            heart.sendMessage(HEARTBEAT_MESSAGE);
        }
    }

    private boolean run = true;
    public synchronized void stopHeart() { run = false; }
    public synchronized boolean heartReady() { return run; }


}
