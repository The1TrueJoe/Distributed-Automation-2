package com.jtelaa.da2.bot.main;

import com.jtelaa.da2.bot.plugin.Plugins;
import com.jtelaa.da2.bot.util.LogRepeater;
import com.jtelaa.da2.bot.util.RemoteCLI;
import com.jtelaa.da2.bot.util.SysCLI;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

// TODO comment
// TODO update to current

public class Main {

    /** The remote cli local object */ 
    public static RemoteCLI rem_cli;

    /** The system cli local object */ 
    public static SysCLI sys_cli;

    // The bots local object
    public static Bot me;

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

        // Start Log Repeater
        LogRepeater repeater = new LogRepeater();
        repeater.start();

        // Send welcome message
        Log.sendSysMessage(ConsoleBanners.botBanner());
        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + me.getID());

        // TODO Add Enrollment

        // Add plugins
        Plugins.importPlugins(me.getConfig().getProperty("plugin_path", "plugins.txt"));
        Plugins.startAll();
        
        // Done
        Log.sendMessage("Main: Done", ConsoleColors.GREEN);

        // Remote CLI
        if (me.getConfig().runRemoteCLI()) {
            Log.sendMessage("Remote CLI Allowed");
            rem_cli = new RemoteCLI();
            rem_cli.start();

        } else {
            Log.sendMessage("No Remote CLI");

        }

        // Local CLI
        if (me.getConfig().runLocalCLI()) {
            Log.sendMessage("Local CLI Allowed");
            sys_cli = new SysCLI();
            sys_cli.start();

        } else {
            Log.sendMessage("No Local CLI");

        }

        Log.sendMessage("Waiting 45s for CLI bootup");
        MiscUtil.waitasec(45);
        Log.sendMessage("Done waiting");

        if (me.getConfig().runLocalCLI()) { sys_cli.runCLI(); }
        if (me.getConfig().runRemoteCLI()) { rem_cli.runCLI(); }

        // Stop
        Log.closeLog();
        repeater.stopRepeater();

    }
}