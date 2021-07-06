package com.jtelaa.da2.bot.main;

import com.jtelaa.da2.bot.plugin.bw.BingRewards;
import com.jtelaa.da2.bot.util.Heartbeat;
import com.jtelaa.da2.director.botmgmt.Bot;
import com.jtelaa.da2.director.util.MgmtMessages;
import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandReceiver;
import com.jtelaa.da2.lib.control.QueuedResponseSender;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

// TODO comment
// TODO update to current

public class Main {

    public static Bot me;

    private static Heartbeat beat;

    private static QueuedCommandReceiver cmd_rx;
    private static QueuedResponseSender cmd_tx;

    public static void main(String[] args) {
        String config_file_location = "";

        me = new Bot(new ConfigHandler(config_file_location));
        Log.loadConfig(me.getConfig());
        Log.openClient(me.getConfig().getLogIP());

        Log.sendSysMessage(ConsoleBanners.botBanner());
        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + me.getID());
        if (me.hasHeartBeat()) { beat = new Heartbeat(); } 
        
        cmd_tx.start();
        cmd_rx.start();

        Command response, command;
        boolean cli_enabled = false;

        while(!cli_enabled) {
            response = cmd_rx.getMessage();
            command = response;

            if (command.command().equals(MgmtMessages.BOT_ENABLE_MESSAGE.getMessage())) {
                Log.sendMessage("Enabling CLI");
                cli_enabled = true;

            }

            while (cli_enabled && !Cases.exit(command)) {
                response = cmd_rx.getMessage();
                command = response.split(" ")[0];
                
                if (Cases.exit(command)) {
                    Log.sendMessage("Exiting CLI");
                    cli_enabled = false;

                } else if (Cases.shutdown(command)) {
                    Log.sendLogMessage("Goodbye!");

                    cmd_rx.stopReceiver();
                    cmd_tx.stopSender();
                    beat.stopHeart();
                    Log.closeLog();

                    ComputerControl.shutdown();
                
                } else if (Cases.command(command)) {
                    String tmp = "";
                    Command[] tmps = response.split(" ");

                    for (int i = 1; i < tmp.length(); i++) {
                        tmp += tmps[i] + " ";
                    }

                    cmd_tx.add(command.origin(), ComputerControl.sendCommand(new Command(tmp)));
                
                } else if (Cases.rewards_plugin(command)) {
                    new BingRewards();
                    
                }
            }

            MiscUtil.waitasec();
            
        }
    }
}