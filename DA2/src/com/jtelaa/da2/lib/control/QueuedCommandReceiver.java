package com.jtelaa.da2.lib.control;

import java.util.Queue;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Receives commands and enques theme
 * 
 * @since 2
 * @author Joseph
 */

public class QueuedCommandReceiver extends Thread {

    private volatile static Queue<Command> command_queue;

    private ServerUDP cmd_rx;

    public synchronized Command getLatest() { return command_queue.poll(); }
    public synchronized Command getMessage() { return command_queue.poll(); }

    public void run() {
        cmd_rx = new ServerUDP(Ports.CMD.getPort());

        if (cmd_rx.startServer()) {
            while (run) {
                String response = cmd_rx.getMessage();

                if (MiscUtil.notBlank(response)) {
                    command_queue.add(new Command(response, NetTools.getLocalIP(), cmd_rx.getClientAddress()));
                
                }
            }
        }
    }

    private boolean run = true;
    public synchronized void stopReceiver() { run = false; }

    
}