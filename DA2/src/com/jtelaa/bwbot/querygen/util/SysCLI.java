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
        if (Main.my_config.getProperty("local_cli", "true").equalsIgnoreCase("true")) {
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
        if (Main.my_config.getProperty("local_cli", "true").equalsIgnoreCase("true")) {
            // Setup
            Log.sendSysMessage("Preparing Local CLI");
            String type;

            while (!run) {
                MiscUtil.waitasec();

            }

            Log.sendSysMessage("Starting Local CLI");
        
            // While run
            while (run) {
                Log.sendSysMessageNoNewLine(">");
                type = keyboard.nextLine();

                Log.sendSysMessage(terminal(new Command(type)));

            }
        }
    }

    @Override
    public synchronized String terminal(Command command) {
        return cli.terminal(command);

    }
    
}
