package com.jtelaa.da2.hypervisor_interface;

import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.log.Log;

/**
 * 
 */

// TDOD comment
// TODO setup main

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
                config_file_location = "com/jtelaa/da2/bot/plugin/bw/config.properties";
                my_config = new ConfigHandler(config_file_location, true);
                first_time = true;
                break;

            }
        }

        // Load normally if not first time
        if (!first_time) { my_config = new ConfigHandler(config_file_location, false); }

        Log.loadConfig(my_config);
        Log.openClient(my_config.getLogIP());

        Log.sendSysMessage(ConsoleBanners.hypervisorBanner());
    }
    
}
