package com.jtelaa.da2.bot.main;

import com.jtelaa.da2.bot.plugin.bw.BingRewards;
import com.jtelaa.da2.bot.util.Heartbeat;
import com.jtelaa.da2.director.botmgmt.Bot;
import com.jtelaa.da2.director.util.cli.Cases;
import com.jtelaa.da2.director.util.cli.clis.BotCLI;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.ComputerControl;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.Client;
import com.jtelaa.da2.lib.net.server.Server;

public class Main {

    public static Bot me;

    private static Heartbeat beat;

    private static Server cmd_rx;
    private static Client cmd_tx;

    public static void main(String[] args) {

        me = new Bot(ConfigHandler.getConfig());
        Log.loadConfig();
        Log.openClient(me.getLogIP());

        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + me.getID());
        if (me.hasHeartBeat()) { beat = new Heartbeat(); } 
        
        cmd_rx = new Server(Ports.RESPONSE.getPort());
        cmd_tx = new Client(me.getDirectorIP(), Ports.RESPONSE.getPort());

        cmd_rx.startServer();
        cmd_tx.startClient();

        String response = "", command ="";
        boolean cli_enabled = false;

        while(!cli_enabled) {
            response = cmd_rx.getMessage();

            if (response.equals(BotCLI.BOT_ENABLE_MESSAGE)) {
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

                    cmd_rx.closeServer();
                    cmd_tx.closeClient();
                    beat.stopHeart();
                    Log.closeLog();

                    ComputerControl.shutdown();
                
                } else if (Cases.command(command)) {
                    String tmp = "";
                    String[] tmps = response.split(" ");

                    for (int i = 1; i < tmp.length(); i++) {
                        tmp += tmps[i] + " ";
                    }

                    cmd_tx.sendMessage(ComputerControl.sendCommand(tmp));
                
                } else if (Cases.rewards_plugin(command)) {
                    new BingRewards();
                    ComputerControl.shutdown();
                    
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.sendMessage(e.getMessage());
            }
        }
    }
}