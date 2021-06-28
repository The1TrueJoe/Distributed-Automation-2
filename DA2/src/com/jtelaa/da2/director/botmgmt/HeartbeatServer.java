package com.jtelaa.da2.director.botmgmt;

public class HeartbeatServer extends Thread {
    
    public void run() {
        
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }

}
