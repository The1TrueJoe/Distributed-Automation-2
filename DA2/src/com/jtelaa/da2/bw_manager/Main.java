package com.jtelaa.da2.bw_manager;

import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.log.Log;

/**
 * 
 */

 // TODO Comment
 // TODO Setup

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

        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/da2/redemption_manager/misc/Redemption.txt"));
    }
}
