package com.jtelaa.da2.bot.util;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Receives logs from and repeates them to the main server
 * 
 * @since 2
 * @author Joseph
 */

public class LogRepeater extends Thread {

    /** Local logging server */
    private ServerUDP cmd_rx;

    public void run() {
        // Server
        cmd_rx = new ServerUDP(SysPorts.LOCAL_LOG);

        // Start Server
        if (cmd_rx.startServer()) {
            // While run
            while (run) {
                // Response
                String response = cmd_rx.getMessage();

                // If not blank, send
                if (MiscUtil.notBlank(response)) {
                    Log.sendMessage(response);
                
                }
            }
        }
    }

   /** Boolean to control the receiver */
   private boolean run = true;

   /** Stops the command receiver */
   public synchronized void stopRepeater() { run = false; }

   /** Checks if the receier is ready */
   public synchronized boolean repeaterReady() { return run; }
    
}