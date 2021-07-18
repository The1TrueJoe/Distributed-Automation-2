package com.jtelaa.da2.bot.main;

import com.jtelaa.da2.bot.util.CLI;
import com.jtelaa.da2.bot.util.Heartbeat;
import com.jtelaa.da2.director.botmgmt.Bot;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

// TODO comment
// TODO update to current

public class Main {

    // The bots local object
    public static Bot me;

    // The cli local object
    public static CLI cli;

    // Heartbeat Processes
    private static Heartbeat beat;

    public static void main(String[] args) {
        // Default configuration file location
        String config_file_location = "config.properties";

        // Check for first time setup
        boolean first_time = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/da2/bot/main/config.properties";
                me = new Bot(new ConfigHandler(config_file_location, true));
                first_time = true;
                break;

            }
        }

        // Load normally if not first time
        if (!first_time) { me = new Bot(new ConfigHandler(config_file_location, false)); }

        // Load Log config and start client
        Log.loadConfig(me.getConfig());
        Log.openClient(me.getConfig().getLogIP());

        // Send welcome message
        Log.sendSysMessage(ConsoleBanners.botBanner());
        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + me.getID());
        
        // Start heartbeat
        if (me.hasHeartBeat()) { beat = new Heartbeat(); } 
        
        // Start CLI
        cli = new CLI();
        cli.start();

        // Wait
        MiscUtil.waitasec();

        // While CLI is available
        while (cli.cli_enabled) {

        }

        // Stop
        beat.stopHeart();
        Log.closeLog();

    }
}