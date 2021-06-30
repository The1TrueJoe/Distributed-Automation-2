package com.jtelaa.da2.director.cli;

import java.util.Queue;

import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandReceiver;
import com.jtelaa.da2.lib.control.QueuedCommandSender;
import com.jtelaa.da2.lib.control.QueuedResponseReceiver;
import com.jtelaa.da2.lib.control.QueuedResponseSender;
import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 *  Basic CLI for the program
 *  @author Joseph
 */

public class CLI extends Thread {

    public volatile Queue<Command> command_queue;
    
    public void run() {
        runCLI();
    }

    private void runCLI() {
        
    }

    public void terminal(Command command) {
        command = command.getModifier(Command.SYSTEM);

        if (Cases.help(command)) {
            

        } else if (Cases.director(command)) {
            

        } else if (Cases.hypervisor(command)) {
            
            
        } else if (Cases.scheduler(command)) {
            
            
        } else if (Cases.bot(command)) {
            
            
        }


        return "";

    }

    /** Accessible CMD Sender/Receiver */

    public static QueuedCommandReceiver cmd_rx;
    public static QueuedCommandSender cmd_tx;

    public static QueuedResponseReceiver resp_rx;
    public static QueuedResponseSender resp_tx;

    public static void open() { 
        cmd_rx = new QueuedCommandReceiver();
        resp_tx = new QueuedResponseSender();
        resp_rx = new QueuedResponseReceiver();
        cmd_tx = new QueuedCommandSender();

    }

    public static void close() {
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();

    }
    
}
