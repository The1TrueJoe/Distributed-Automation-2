package com.jtelaa.da2.lib.cli;

import com.jtelaa.da2.director.botmgmt.MgmtMessages;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandReceiver;
import com.jtelaa.da2.lib.control.QueuedCommandSender;
import com.jtelaa.da2.lib.control.QueuedResponseReceiver;
import com.jtelaa.da2.lib.control.QueuedResponseSender;

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

    /** Abstract method for containing the command structure */
    public abstract String terminal(Command command);

    /** Boolean that controls wether or not the thread runs */
    public volatile boolean run;

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

    public void runRX(MgmtMessages enable_message) {
        // Open rx
        openOnewayRX();

        Command command;

        // While run
        while (run) {
            if (cmd_rx.getLatest().equals(enable_message) || enable_message.equals(MgmtMessages.NONE)) {
                command = cmd_rx.getLatest();
                
                if (command.isValid()) { 
                    if (command.isHeadless()) {
                        resp_tx.add(command.origin(), terminal(command));
                    
                    } else {
                        terminal(command);

                    }

                } else {
                    MiscUtil.waitasec(.25);

                }

            } else {
                MiscUtil.waitasec();

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
     * Enables the servers and clients
     * <p> Automatically run in the open methods
     */

    private void enable() { 
        run = true;

        cmd_rx = new QueuedCommandReceiver();
        resp_tx = new QueuedResponseSender();
        resp_rx = new QueuedResponseReceiver();
        cmd_tx = new QueuedCommandSender();

    }

    /**
     * Opens all of the servers and clients
     */

    public void open() {
        enable();

        cmd_rx.start();
        resp_rx.start();
        resp_tx.start();
        cmd_tx.start();
    }

    /**
     * Opens servers and clients for cmd reception
     */

    public void openOnewayRX() {
        enable();

        cmd_rx.start();
        resp_tx.start();
    }

    /**
     * Opens servers and clients for cmd transmission
     */

    public void openOnewayTX() {
        enable();

        resp_rx.start();
        cmd_tx.start();
    }
    

    /**
     * Closes all of the clients and servers
     */

    public void close() {
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();

    }

    /**
     * Closes servers and clients for cmd reception
     */

    public void closeOnewayRX() {
        cmd_rx.stopReceiver();
        resp_tx.stopSender();
    }

    /**
     * Closes servers and clients for cmd transmission
     */

    public void closeOnewayTX() {
        resp_rx.stopReceiver();
        cmd_tx.stopSender();
    }

}
