package com.jtelaa.da2.bot.util;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Receives logs and repeates them
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class LogRepeater extends Thread {

    private ServerUDP cmd_rx;

    public void run() {
        cmd_rx = new ServerUDP(SysPorts.RESPONSE);

        if (cmd_rx.startServer()) {
            while (run) {
                String response = cmd_rx.getMessage();

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
   // TODO Implement

    
}