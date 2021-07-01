package com.jtelaa.da2.redemption_manager.coms;

public class PointAnnouncementListener extends Thread {

    public void run() {
        

        while(run) {
            
        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }
    
}
