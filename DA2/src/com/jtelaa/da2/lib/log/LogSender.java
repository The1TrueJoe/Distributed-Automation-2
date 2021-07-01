package com.jtelaa.da2.lib.log;

import com.jtelaa.da2.bot.main.Main;

/**
 * New process for sending logging messages
 * 
 * @since 2
 * @author Joseph
 */

public class LogSender extends Thread {

    public void run() {
        while (run) {
            sendMessage();
        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }

    public void sendMessage() {
        String message = Log.logging_queue.poll();
        if (message == null) { return; }

        message = Main.me.getIP() + ">" + message;
        Log.logging_client.sendMessage(message);

    }
    
}
