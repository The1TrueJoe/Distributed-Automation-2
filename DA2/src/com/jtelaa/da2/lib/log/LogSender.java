package com.jtelaa.da2.lib.log;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;

/**
 * New process for sending logging messages
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.da2.lib.log.Log
 */

public class LogSender extends Thread {

    public void run() {
        // Wait until ready
        while (!run) {
            MiscUtil.waitasec();
            
        }

        while (run) {
            sendMessage();
        }
    }

    /** Boolean to control the receiver */
    private volatile boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopSender() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean senderReady() { return run; }

    /**
     * Sends the log messages
     */

    public void sendMessage() {
        // Get most recent message
        String message = Log.logging_queue.poll();
        if (message == null) { return; }

        // Add local ip to message
        message = NetTools.getLocalIP() + ">" + message;

        // Add message to queue
        Log.logging_client.sendMessage(message);

    }
    
}
