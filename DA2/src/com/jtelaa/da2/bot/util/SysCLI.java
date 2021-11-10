package com.jtelaa.da2.bot.util;

import java.util.Scanner;

import com.jtelaa.bwbot.bw.Main;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.config.PropertiesUtils;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

public class SysCLI extends LocalCLI {

    private RemoteCLI cli;
    private Scanner keyboard;

    public SysCLI() {
        if (PropertiesUtils.isTrue(Main.config, "local_cli", true)) {
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
        if (PropertiesUtils.isTrue(Main.config, "local_cli", true)) {
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
