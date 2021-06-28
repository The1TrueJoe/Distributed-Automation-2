package com.jtelaa.da2.lib.control;

import java.util.Queue;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.server.Server;

/**
 * Receives command responses
 * 
 * @since 2
 * @author Joseph
 */

public class QueuedResponseReceiver extends Thread {

    private volatile static Queue<String> response_queue;

    private Server cmd_rx;

    public synchronized String getLatest() { return response_queue.poll(); }
    public synchronized String getMessage() { return response_queue.poll(); }

    public void run() {
        cmd_rx = new Server(Ports.RESPONSE.getPort());

        if (cmd_rx.startServer()) {
            while (run) {
                String response = cmd_rx.getMessage();

                if (MiscUtil.notBlank(response)) {
                    response_queue.add(response);
                
                }
            }
        }
    }

    private boolean run = true;
    public synchronized void stopReceiver() { run = false; }

    
}