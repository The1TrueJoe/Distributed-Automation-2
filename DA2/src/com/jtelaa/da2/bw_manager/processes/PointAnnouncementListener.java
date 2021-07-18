package com.jtelaa.da2.bw_manager.processes;
// TODO comment

public class PointAnnouncementListener extends Thread {

    public void run() {
        // TODO add setup

        while(run) {
            // TODO program process
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
