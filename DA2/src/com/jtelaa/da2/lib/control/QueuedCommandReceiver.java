package com.jtelaa.da2.lib.control;

import java.util.Queue;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Receives commands and enques theme
 * <p> Useful as an additional process so it can control the execution of commands
 * while the system is currently performing its automatic duties
 * 
 * @since 2
 * @author Joseph
 */

public class QueuedCommandReceiver extends Thread {

    /** Command queue FIFO */
    private volatile static Queue<Command> command_queue;

    /** CMD Reception server (UDP) */
    private ServerUDP cmd_rx;

    /** Gets the next command to be executed */
    public synchronized Command getLatest() { return command_queue.poll(); }

    /** Gets the next command to be executed */
    public synchronized Command getMessage() { return command_queue.poll(); }

    public void run() {
        // Open UDP server
        Log.sendMessage("Starting command receiver");
        cmd_rx = new ServerUDP(Ports.CMD.getPort());

        // Check if the server is ready
        if (!cmd_rx.startServer()) { 
            boolean started = false;

            // Continually wait and then try again
            do {
                MiscUtil.waitasec();
                started = cmd_rx.startServer();

            } while (!started);
        }

        // Server setup complete
        Log.sendMessage("Command receiver done");
        while (run) {                       
            String response = cmd_rx.getMessage();

            // If command is not blank, add it into the queue
            if (MiscUtil.notBlank(response)) {
                command_queue.add(new Command(response, NetTools.getLocalIP(), cmd_rx.getClientAddress()));
            
            }
        }
    }

    /**
     * Manually adds a command into the queue
     * 
     * <p> Mainly for taking commands from the local system and adding them into the cli
     * 
     * @param command
     */

    public synchronized void add(String command) {
        // Creates a new command that originated from the local system and is meant for the local system
        add(new Command(command, NetTools.getLocalIP(), NetTools.getLocalIP()));

    }

    /**
     * Manually adds a command into the queue
     * 
     * <p> Mainly for taking commands from the local system and adding them into the cli
     * 
     * @param command
     */

    public synchronized void add(Command command) {
        command_queue.add(command);

    }

    /** Boolean to control the receiver */
    private boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean receiverReady() { return run; }
    // TODO Implement

    
}