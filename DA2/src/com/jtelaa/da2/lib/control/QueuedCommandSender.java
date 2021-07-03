package com.jtelaa.da2.lib.control;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.ClientUDP;

/**
 * Sends commands in batches <p>
 * <b> Sends response to log only </b>
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class QueuedCommandSender extends Thread {
    
    private volatile static Queue<Command> command_queue;

    private ClientUDP cmd_tx;

    public synchronized void add(Command command) {
        if (command.isValid()) { 
            command_queue.add(command);
        }
    }

    public synchronized void add(String server, String command) {
        add(new Command(server, command, false));
    }

    public void run() {
        command_queue = new LinkedList<>();
        Command command;

        while (run) {
            command = command_queue.poll();

            if (command != null && command.isValid()) {
                sendMessage(command);
                
            }
        }
    }

    private boolean run = true;
    public synchronized void stopSender() { run = false; }

    private void sendMessage(Command command_to_send) {
        String message = command_to_send.command();
        String server = command_to_send.destination();

        cmd_tx = new ClientUDP(server, Ports.CMD.getPort());

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(message);
            cmd_tx.closeClient();

        }
    }

}
