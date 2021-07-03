package com.jtelaa.da2.redemption_manager.coms;
// TODO comment

public class PointAnnouncementListener extends Thread {

    public void run() {
        // TODO add setup

        while(run) {
            // TODO program process
        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }
    
}
