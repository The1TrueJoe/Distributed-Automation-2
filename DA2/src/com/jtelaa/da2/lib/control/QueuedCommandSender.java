package com.jtelaa.da2.lib.control;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.ManualPort;
import com.jtelaa.da2.lib.net.ports.Ports;
import com.jtelaa.da2.lib.net.ports.SysPorts;

/**
 * Sends commands in batches <p>
 * <b> Sends response to log only </b>
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class QueuedCommandSender extends Thread {

    public QueuedCommandSender(int port) { this(new ManualPort(port)); }

    public QueuedCommandSender(Ports port) { this.port = port; }

    public QueuedCommandSender() { this(default_port); }

    private Ports port;
    public static volatile Ports default_port = SysPorts.CMD;
    
    private volatile static Queue<Command> command_queue;

    private ClientUDP cmd_tx;

    public synchronized void add(Command command) {
        if (command.isValid() && NetTools.isAlive(command.destination())) { 
            command_queue.add(command);
        }
    }

    public synchronized void add(String server, String command) {
        add(new Command(server, command, false));
    }

    public void run() {
        command_queue = new LinkedList<>();
        Command command;

        while (!run) {
            MiscUtil.waitasec();
            
        }

        while (run) {
            if (command_queue.size() > 0) {
                command = command_queue.poll();

                if (command != null && command.isValid()) {
                    sendMessage(command);
                
                }
            }
        }
    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopSender() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean senderReady() { return run; }
    // TODO Implement

    private void sendMessage(Command command_to_send) {
        String message = command_to_send.command();
        String server = command_to_send.destination();

        cmd_tx = new ClientUDP(server, port, "Command Sender: ");

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(message);
            cmd_tx.closeClient();

        }
    }

}
