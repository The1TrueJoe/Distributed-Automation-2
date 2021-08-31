package com.jtelaa.da2.bot.main;

import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
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
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.server.ServerUDP;

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

        // Config handler (use temp)
        ConfigHandler config;

        // Check for first time setup
        boolean first_time = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/da2/bot/main/config.properties";
                config = new ConfigHandler(config_file_location, true);
                first_time = true;
                break;

            }
        }

        // Load normally if not first time
        if (!first_time) { 
            me = new Bot(new ConfigHandler(config_file_location, false)); 
            config = me.getConfig();

            // Load Log config and start client
            Log.loadConfig(config);
            Log.openClient(config.getLogIP());

        } else {
            Log.log_verbose = true;

        }

        // Start Log Repeater
        LogRepeater repeater = new LogRepeater();
        repeater.start();

        // Send welcome message
        Log.sendSysMessage(ConsoleBanners.botBanner());
        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + me.getID());

        // TODO Add Enrollment

        // Request Bot (first time only)
        if (first_time) {
            Log.sendMessage("Requesting bot");

            // Server to receive bot info
            ServerUDP msg_response = new ServerUDP(BWPorts.INFO_RECEIVE);
            msg_response.startServer();

            // Client to send request
            ClientUDP msg_request = new ClientUDP("127.0.0.1", BWPorts.INFO_REQUEST);
            msg_request.startClient();

            // Set default
            me = new Bot("127.0.0.1");

            // Prep message
            Object message;

            do {
                // Send message 
                msg_request.sendMessage(BWMessages.BOT_REQUEST_MESSAGE);

                // Wait
                MiscUtil.waitasec(.1);

                // Get message
                message = msg_response.getObject();

            // Check if can cast
            } while (!Bot.class.isInstance(message));

            // Cast and apply
            me = (Bot) message;

            Log.sendMessage("Bot request done");

        }

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