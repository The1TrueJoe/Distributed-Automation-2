package com.jtelaa.da2.redemption_manager.coms;

public class AccountAnnouncementListener extends Thread {

    public void run() {

    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }
    
}