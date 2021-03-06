package com.jtelaa.bwbot.bw_manager;

import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;

/**
 * 
 */

 // TODO Comment
 // TODO Setup

public class Main {
    public static volatile ConfigHandler my_config;

    /**
     * 
     * @param args
     */

    public static void main(String[] args) {
        // Default configuration file location
        String config_file_location = "config.properties";

        // Check for first time setup
        boolean first_time = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/bwbot/plugin/bw/config.properties";
                my_config = new ConfigHandler(config_file_location, true);
                first_time = true;
                break;

            }
        }

        // Load normally if not first time
        if (!first_time) { my_config = new ConfigHandler(config_file_location, false); }

        Log.loadConfig(my_config);
        Log.openClient(my_config.getLogIP());

        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/da2/redemption_manager/misc/Rewards.txt", ConsoleColors.CYAN_BOLD_BRIGHT));
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/da2/redemption_manager/misc/Redemption.txt", ConsoleColors.PURPLE));
    }
}
