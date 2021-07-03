package com.jtelaa.da2.director.botmgmt;

import java.util.ArrayList;

// TDOD comment

public class HeartbeatServer extends Thread {

    private volatile static ArrayList<Bot> active_bots;
    
    public synchronized static boolean isAlive(Bot bot) {
        for (Bot bot_to_check : active_bots) {
            if (bot.getID() == bot_to_check.getID()) {
                return true;

            }
        }

        return false;
    }

    public void run() {
        active_bots = new ArrayList<>();

        while(run) {

        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }

}
