package com.jtelaa.da2.bw_manager.redemptions.processes;
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