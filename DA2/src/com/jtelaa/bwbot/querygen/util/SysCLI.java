package com.jtelaa.bwbot.querygen.util;

import java.util.Scanner;

import com.jtelaa.bwbot.querygen.Main;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 * Local System CLI (Runs remote cli instance)
 * 
 * @author Joseph
 * @since 2
 * 
 */
public class SysCLI extends LocalCLI {

    private RemoteCLI cli;
    private Scanner keyboard;

    public SysCLI() {
        if (Main.my_config.runLocalCLI()) {
            // Use remote cli instance
            cli = new RemoteCLI(false);

            // Scanner
            keyboard = new Scanner(System.in);

        }

    }

    @Override
    public void stopCLI() {
        keyboard.close();
        super.stopCLI();

    }

    @Override
    public void run() {
        if (Main.my_config.runLocalCLI()) {
            // Setup
            Log.sendMessage("Preparing Local CLI");
            String type;

            while (!run) {
                MiscUtil.waitasec();

            }

            Log.sendMessage("Starting Local CLI");
        
            // While run
            while (run) {
                Log.sendSysMessageNoNewLine(">");
                type = keyboard.nextLine();

                Log.sendMessage(terminal(new Command(type)));

            }
        }
    }

    @Override
    public synchronized String terminal(Command command) {
        return cli.terminal(command);

    }
    
}
