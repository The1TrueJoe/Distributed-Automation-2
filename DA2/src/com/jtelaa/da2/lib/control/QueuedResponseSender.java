package com.jtelaa.da2.lib.control;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.Client;

/**
 * Sends responses in batches <p>
 * 
 * @since 2
 * @author Joseph
 */

public class QueuedResponseSender extends Thread {
    
    private volatile static Queue<String> response_queue;
    private volatile static Queue<String> response_server_queue;

    private Client cmd_tx;

    public synchronized void add(String response_ip, String response) {
        if (MiscUtil.notBlank(response)) {
            response_queue.add(response);
            response_server_queue.add(response_ip);
        }
    }

    public void run() {
        response_queue = new LinkedList<>();
        response_server_queue = new LinkedList<>();

        while (run) {
            sendMessage();
        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }

    private void sendMessage() {
        String server = response_server_queue.poll();
        String message = response_queue.poll();

        if (server.equals(NetTools.getLocalIP()) || server.equals("127.0.0.1")) {
            Log.sendSysMessage(message);
            return;
            
        }

        cmd_tx = new Client(server, Ports.RESPONSE.getPort());

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(message);
            cmd_tx.closeClient();

        }
    }

}