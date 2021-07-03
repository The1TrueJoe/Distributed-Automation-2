package com.jtelaa.da2.lib.cli;

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
    
    public abstract void run();
    public abstract String terminal(Command command);

    public boolean run;
    public synchronized void stopReceiver() { run = false; }

    /** Accessible CMD Sender/Receiver */

    public QueuedCommandReceiver cmd_rx;
    public QueuedCommandSender cmd_tx;

    public QueuedResponseReceiver resp_rx;
    public QueuedResponseSender resp_tx;

    /**
     * Run cmd as tx
     * 
     * <p> Constantly receives commands and then runs them
     */

    public void runRX() {
        openOnewayRX();
        Command command;

        while (run) {
            command = cmd_rx.getMessage();

            if (command.isValid()) {
                resp_tx.add(command.origin(), terminal(command));

            } else {
                MiscUtil.waitasec();
                
            }
        }
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
     * Open the clients and servers
     */

    public void close() {
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();

    }

}
