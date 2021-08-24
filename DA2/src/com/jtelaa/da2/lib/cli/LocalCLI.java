package com.jtelaa.da2.lib.cli;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.ports.Ports;
import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandReceiver;
import com.jtelaa.da2.lib.control.QueuedCommandSender;
import com.jtelaa.da2.lib.control.QueuedResponseReceiver;
import com.jtelaa.da2.lib.control.QueuedResponseSender;
import com.jtelaa.da2.lib.log.Log;

/**
 * Class that contains the necessary parts for a CLI
 * 
 * @since 2
 * @author Joseph
 */

 // TDOD comment

public abstract class LocalCLI extends Thread {

    /** Abstract method for running the thread */
    public abstract void run();

    /** Abstract method for containing the command structure (Make syncronized in inherited class) */
    public abstract String terminal(Command command);

    /** Boolean that controls wether or not the thread runs */
    public volatile boolean run;

    /** Tells the internal logic to start */
    public synchronized void runCLI() { run = true; }

    /** Tells the thread to stop (Waits untilt the iteration on the while loop is complete) */
    public synchronized void stopCLI() { run = false; }

    /** Tells the process that the cli is ready to be used */
    public volatile boolean cli_enabled;

    /* Accessible CMD Sender/Receiver */

    /** Receives commands from an external source */
    public QueuedCommandReceiver cmd_rx;
    /** Sends commands to an external source*/
    public QueuedCommandSender cmd_tx;

    /** Receives reponses from an external source */
    public QueuedResponseReceiver resp_rx;
    /** Sends reponses to an external source*/
    public QueuedResponseSender resp_tx;


    /**
     * Run cmd as tx (Use this instead of own code)
     * 
     * <p> Constantly receives commands and then runs them
     * <p> No enable message required (Default enables CLI)
     */

    public void runRX() { runRX(MgmtMessages.NONE); }

    /**
     * Run cmd as tx (Use this instead of own code)
     * 
     * <p> Constantly receives commands and then runs them
     * 
     * @param enable_message Message to listen for before enabling CLI
     */

    public void runRX(MgmtMessages enable_message) { runRX(enable_message, QueuedCommandReceiver.default_port); }

    public void runRX(MgmtMessages enable_message, Ports port) {

        // Open rx
        openOnewayRX(port);

        String local_ip = NetTools.getLocalIP();
        Command command;

        cli_enabled = false;

        while (!cli_enabled && run) {
            if (cmd_rx.getLatest().equals(enable_message) || enable_message.equals(MgmtMessages.NONE)) {
                // Notification
                Log.sendSysMessage("CLI Ready");

                cli_enabled = true;

            } else {
                MiscUtil.waitasec();
                
            }
        }

        // While run
        while (run && cli_enabled) {
            command = cmd_rx.getLatest();
 
            // If command is valid
            if (command.isValid()) { 
                // If it is headless
                if (command.isHeadless()) {
                    resp_tx.add(command.origin(), terminal(command));
                
                // Otherwise
                } else {
                    // If the origin is the local pc
                    if (command.origin().equals("127.0.0.1") || command.origin().equals(local_ip)) {
                        Log.sendSysMessage(terminal(command));

                        // Else
                    } else {
                        Log.sendMessage(terminal(command));

                    }
                } 
            }
        }
    }

    /**
     * Adds command to the local queue
     * 
     * @param command Command to run on remote system
     */

    public void addCommandRX(String command) {
        cmd_rx.add(command);

    }

    /**
     * Adds command to the local queue
     * 
     * @param command Command to run on remote system
     */

    public void addCommandRX(Command command) {
        cmd_rx.add(command);

    }

    /**
     * Run cmd sender
     */

    public void runTX() {
        openOnewayTX();

    }

    /**
     * Opens cmd receiver
     */

    public void openOnewayRX() {
        cmd_rx = new QueuedCommandReceiver();
        cmd_rx.start();
        
    }

    public void openOnewayRX(Ports port) {
        cmd_rx = new QueuedCommandReceiver(port);
        cmd_rx.start();
        
    }

    /**
     * Opens servers and clients for cmd transmission
     */

    public void openOnewayTX() {
        cmd_tx = new QueuedCommandSender();
        cmd_tx.start();
        
    }

    public void openOnewayTX(Ports port) {
        cmd_tx = new QueuedCommandSender(port);
        cmd_tx.start();
        
    }

    /**
     * Closes servers and clients for cmd reception
     */

    public void closeOnewayRX() {
        cmd_rx.stopReceiver();
        
    }

    /**
     * Closes servers and clients for cmd transmission
     */

    public void closeOnewayTX() {
        cmd_tx.stopSender();

    }

}
