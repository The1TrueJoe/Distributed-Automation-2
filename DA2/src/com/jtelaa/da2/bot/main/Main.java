package com.jtelaa.da2.bot.main;

import com.jtelaa.da2.bot.util.LogRepeater;
import com.jtelaa.da2.bot.util.RemoteCLI;
import com.jtelaa.da2.bot.util.SysCLI;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.bot.plugin.Plugins;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.sql.EmptySQLURLException;

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
        // Configuration
        String connectionURL = "";

        // Set config
        try {
            me = Bot.loadfromDatabase(connectionURL, NetTools.getLocalIP());

        } catch (EmptySQLURLException e) {
            // TODO Add default
            
        }

        me.config = me.exporttoProperties();

        // Load Log config and start client
        Log.loadConfig(me.config);
        Log.openClient(me.logger_IP);

        // Start Log Repeater
        LogRepeater repeater = new LogRepeater();
        repeater.start();

        // Send welcome message
        Log.sendSysMessage(ConsoleBanners.botBanner());
        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + me.id);


        // Add plugins
        Plugins.importPlugins(me.plugins);
        Plugins.startAll();
        
        // Done
        Log.sendMessage("Main: Done", ConsoleColors.GREEN);

        // CLI
        cliBootup();

    }

    private static void cliBootup() {
        // Remote CLI
        if (me.config.getProperty("remote_cli", "false").equalsIgnoreCase("true")) {
            Log.sendMessage("CLI: Remote Cli Enabled");
            rem_cli = new RemoteCLI();
            rem_cli.start();

        } else {
            Log.sendMessage("CLI: No Remote CLI");

        }

        // Local CLI
        if (me.config.getProperty("local_cli", "true").equalsIgnoreCase("true")) {
            Log.sendMessage("CLI: Local CLI Allowed");
            sys_cli = new SysCLI();
            sys_cli.start();

        } else {
            Log.sendMessage("CLI: No Local CLI");

        }

        Log.sendMessage("CLI: Waiting 45s for CLI bootup");
        MiscUtil.waitasec(45);
        Log.sendMessage("CLI: Done waiting");

        if (me.config.getProperty("remote_cli", "false").equalsIgnoreCase("true")) { rem_cli.runCLI(); }
        if (me.config.getProperty("local_cli", "true").equalsIgnoreCase("true")) { sys_cli.runCLI(); }


    }
}