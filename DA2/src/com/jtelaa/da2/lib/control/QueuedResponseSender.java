package com.jtelaa.da2.lib.control;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.ManualPort;
import com.jtelaa.da2.lib.net.ports.Ports;
import com.jtelaa.da2.lib.net.ports.SysPorts;

/**
 * Sends responses in batches <p>
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class QueuedResponseSender extends Thread {

    public QueuedResponseSender(int port) { this(new ManualPort(port)); }

    public QueuedResponseSender(Ports port) { this.port = port; }

    public QueuedResponseSender() { this(default_port); }

    private Ports port;
    public static volatile Ports default_port = SysPorts.RESPONSE;
    
    private volatile static Queue<String> response_queue;
    private volatile static Queue<String> response_server_queue;

    private ClientUDP cmd_tx;

    public synchronized void add(String response_ip, String response) {
        if (MiscUtil.notBlank(response) && NetTools.isAlive(response_ip)) {
            response_queue.add(response);
            response_server_queue.add(response_ip);
        }
    }

    public void run() {
        response_queue = new LinkedList<>();
        response_server_queue = new LinkedList<>();

        while (!run) {
            MiscUtil.waitasec();
            
        }

        while (run) {
            sendMessage();
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopSender() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean senderReady() { return run; }
    // TODO Implement

    private void sendMessage() {
        if (!(response_server_queue.size() > 0 && response_queue.size() > 0)) { return; }

        String server = response_server_queue.poll();
        String message = response_queue.poll();

        if (server.equals(NetTools.getLocalIP()) || server.equals("127.0.0.1")) {
            Log.sendSysMessage(message);
            return;
            
        }

        cmd_tx = new ClientUDP(server, port, "Response Sender: ");

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(message);
            cmd_tx.closeClient();

        }
    }

}
