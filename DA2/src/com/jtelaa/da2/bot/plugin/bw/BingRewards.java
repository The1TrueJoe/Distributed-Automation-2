package com.jtelaa.da2.bot.plugin.bw;

import com.jtelaa.da2.bot.plugin.bw.util.AcctInfo;
import com.jtelaa.da2.bot.plugin.bw.util.SearchSystem;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.log.Log;

/**
 * The bing rewards automation system
 * 
 * @since 1
 * @author Joseph
 */

// TODO comment

public class BingRewards extends Thread {

    public static ConfigHandler config;

    public void run() {
        config = new ConfigHandler(); // TODO add path

        // Startup
        Log.sendMessage("Bing Rewards Plugin Enabled");

        // Announce Account
        if (config.isTrue("first_setup", "true")) {
            AcctInfo.requestAccount();
            AcctInfo.setupAccount();
        
        } 
        
        AcctInfo.setup();
        AcctInfo.announceAccount();

        // Start the searches
        SearchSystem.startSearch();

        // Announce Points
        AcctInfo.announceAccount();

        // Shutdown
        Log.sendMessage("Shutting Down Plugin");

    }

    
}
