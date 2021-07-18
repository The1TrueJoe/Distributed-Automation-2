package com.jtelaa.da2.director.botmgmt;

import java.util.ArrayList;

// TDOD comment

public class HeartbeatServer extends Thread {

    private volatile ArrayList<Bot> active_bots;

    public ArrayList<Bot> getActiveBots() { return active_bots; }
    
    public synchronized boolean isAlive(Bot bot) {
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

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean receiverReady() { return run; }
    // TODO Implement

}
