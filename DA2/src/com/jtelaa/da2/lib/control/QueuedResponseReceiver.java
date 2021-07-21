package com.jtelaa.da2.lib.control;

import java.util.Queue;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Receives command responses
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class QueuedResponseReceiver extends Thread {

    private volatile static Queue<String> response_queue;

    private ServerUDP cmd_rx;

    public synchronized String getLatest() { return response_queue.poll(); }
    public synchronized String getMessage() { return response_queue.poll(); }

    public void run() {
        cmd_rx = new ServerUDP(SysPorts.RESPONSE);

        if (cmd_rx.startServer()) {
            while (run) {
                String response = cmd_rx.getMessage();

                if (MiscUtil.notBlank(response)) {
                    response_queue.add(response);
                
                }
            }
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