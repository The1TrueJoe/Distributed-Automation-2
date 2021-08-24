package com.jtelaa.da2.director;

import com.jtelaa.da2.director.botmgmt.BotMgmt;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.log.Log;

/**
 * 
 */

// TODO comment
// TODO setup

public class Main {

    public static volatile BotMgmt bt_mgmt;

    public static volatile ConfigHandler my_config;
    
    public static void main(String[] args) {
        // Default configuration file location
        String config_file_location = "config.properties";

        // Check for first time setup
        boolean first_time = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/da2/bot/plugin/bw/config.properties";
                my_config = new ConfigHandler(config_file_location, true);
                first_time = true;
                break;

            }
        }

        // Load normally if not first time
        if (!first_time) { my_config = new ConfigHandler(config_file_location, false); }

        // Open config
        Log.loadConfig(my_config);
        Log.openClient(my_config.getLogIP());

        // Display banner
        Log.sendSysMessage(ConsoleBanners.directorBanner());
        Log.sendSysMessage("Welcome to the Distributed Automation 2 Director Server");

        // Start MGMT & import
        Log.sendSysMessage("Starting bot managment");
        bt_mgmt = new BotMgmt();
        Log.sendSysMessage("Importing bots");
        bt_mgmt.importBots(my_config.getProperty("bot-file", "botfile.txt"));
        Log.sendSysMessage("Loaded " + bt_mgmt.getAllBots().size() + " bots");

        // Check if child mode
        boolean child = my_config.getProperty("child", "false").equalsIgnoreCase("false");
        if (child) {  bt_mgmt.h_beat_server.operateAsChild(my_config.getProperty("master_ip")); }

        // Start
        bt_mgmt.start();
        
    }

}