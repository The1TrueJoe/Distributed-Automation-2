package com.jtelaa.da2.lib.log;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.SysPorts;
import com.jtelaa.da2.lib.net.client.ClientUDP;

/**
 * New process for sending logging messages
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.da2.lib.log.Log
 */

public class LogSender extends Thread {

    /** Logging client */
    private ClientUDP logging_client;

    /** Is the loggin client established */
    public boolean log_established = false;

    /** Local ip (For logging purposes) */
    private String local_ip;

    /** Local ip (For logging purposes) */
    private String logging_server_ip;

    /**
     * Constructor
     * 
     * @param logging_server_ip Log server ip to use
     */

    public LogSender(String logging_server_ip) {
        this.logging_server_ip = logging_server_ip;

    }

    public void run() {
        // Wait until ready
        while (!run) {
            MiscUtil.waitasec();
            
        }

        // Store local ip
        local_ip = NetTools.getLocalIP();

        // Start logging client
        logging_client = new ClientUDP(logging_server_ip, SysPorts.LOG);
        log_established = logging_client.startClient();

        while (run) {
            sendMessage();
        }
    }

    /** Boolean to control the receiver */
    private volatile boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopSender() { 
        logging_client.closeClient();
        run = false; 

    }

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
        message = local_ip + ">" + message;

        // Add message to queue
        logging_client.sendMessage(message);

    }
    
}
