package com.jtelaa.da2.bot.plugin.bw;

import com.jtelaa.da2.bot.plugin.bw.util.SearchSystem;
import com.jtelaa.da2.lib.log.Log;

/**
 * The bing rewards automation system
 * 
 * @since 1
 * @author Joseph
 */

public class BingRewards extends Thread {

    public void run() {
        // Startup
        Log.sendMessage("Bing Rewards Plugin Enabled");

        // Announce Account

        // Start the searches
        SearchSystem.startSearch();

        // Announce Points

        // Shutdown
        Log.sendMessage("Shutting Down Plugin");

    }

    
}
