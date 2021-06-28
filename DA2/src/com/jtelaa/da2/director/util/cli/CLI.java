package com.jtelaa.da2.director.util.cli;

import java.util.Queue;

import com.jtelaa.da2.director.util.cli.clis.BotCLI;
import com.jtelaa.da2.director.util.cli.clis.DirecCLI;
import com.jtelaa.da2.director.util.cli.clis.HypervisCLI;
import com.jtelaa.da2.director.util.cli.clis.MiscCLI;
import com.jtelaa.da2.director.util.cli.clis.SchedCLI;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandReceiver;
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
        Command command = command_queue.poll();

        while (MiscUtil.notBlank(command.command()) && !Cases.exit(command)) {
            if (MiscUtil.notBlank(command.command())) { cmd_tx.add(command.origin(), terminal(command)); }
            
            MiscUtil.waitasec();
            command = command_queue.poll();
            
        }
    }

    public String terminal(Command command) {
        command = command.split(" ")[1];

        if (Cases.help(command)) {
            return new MiscCLI().startCLI(command);

        } else if (Cases.director(command)) {
            return new DirecCLI().startCLI(command);

        } else if (Cases.hypervisor(command)) {
            return new HypervisCLI().startCLI(command);
            
        } else if (Cases.scheduler(command)) {
            return new SchedCLI().startCLI(command);
            
        } else if (Cases.bot(command)) {
            return new BotCLI().startCLI(command);
            
        }


        return "";

    }

    /** Accessible CMD Receiver */

    public static QueuedCommandReceiver cmd_rx;
    public static QueuedResponseSender cmd_tx;

    public static void open() { 
        cmd_rx = new QueuedCommandReceiver();
        cmd_tx = new QueuedResponseSender();

    }

    public static void close() {
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();

    }
}
