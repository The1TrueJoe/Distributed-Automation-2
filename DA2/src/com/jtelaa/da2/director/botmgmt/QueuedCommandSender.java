package com.jtelaa.da2.director.botmgmt;

import java.util.LinkedList;
import java.util.Queue;

public class QueuedCommandSender extends Thread {
    
    private static Queue<String> command_queue;
    private static Queue<String> bot_queue;

    public static void add(String ip, String command) {

    }

    public void run() {
        command_queue = new LinkedList<>();
        bot_queue = new LinkedList<>();

        while (run) {
            sendMessage();
        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }

    public void sendMessage() {
        String message = command_queue.poll();
        if (message == null) { return; }


        sendMessage(message);

    }

}
