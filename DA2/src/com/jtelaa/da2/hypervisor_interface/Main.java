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

    public static volatile ConfigHandler me;

    /**
     * 
     * @param args
     */

    public static void main(String[] args) {
        String config_file_location = "";

        me = new ConfigHandler(config_file_location);
        Log.loadConfig(me);
        Log.openClient(me.getLogIP());

        Log.sendSysMessage(ConsoleBanners.hypervisorBanner());
    }
    
}
