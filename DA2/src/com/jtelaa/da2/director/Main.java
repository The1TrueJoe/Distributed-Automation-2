package com.jtelaa.da2.director;

import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.log.Log;

/**
 * 
 */

// TODO comment
// TODO setup

public class Main {

    public static volatile ConfigHandler me;
    
    public static void main(String[] args) {
        String config_file_location = "";

        me = new ConfigHandler(config_file_location);
        Log.loadConfig(me);
        Log.openClient(me.getLogIP());

        Log.sendSysMessage(ConsoleBanners.directorBanner());
        System.out.println("Welcome to the Distributed Automation 2 Director Server");

        
    }

}