package com.jtelaa.bwbot.bw;

import com.jtelaa.bwbot.bw.sys.AcctInfo;
import com.jtelaa.bwbot.bw.sys.SearchSystem;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;

/**
 * The bing rewards automation system
 * 
 * @since 1
 * @author Joseph
 */

// TODO comment

public class Main extends Thread {

    /** Local config handler */
    public static ConfigHandler config;

    /** Arguments */
    private volatile String[] args;

    /**
     * Adds aguments to the process
     * 
     * @param args
     */
    
    public synchronized void args(String[] args) { this.args = args; }

    /**
     * Adds aguments to the process
     * 
     * @param command
     */

    public synchronized void args(Command command) { args = Command.toString(command.getSubCommands(Command.LOCAL_ARG1)); }

    public void run() {
        // Is this process terminal
        boolean terminal_process = true;

        // Default configuration file location
        String config_file_location = "bwconfig.properties";

        // Check for first time setup
        boolean first_time = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/da2/bot/plugin/bw/config.properties";
                config = new ConfigHandler(config_file_location, true);
                first_time = true;
                break;

            } if (arg.equalsIgnoreCase("shutdown")) {
                terminal_process = true;

            } else if (arg.equalsIgnoreCase(" no shutdown")) {
                terminal_process = false;
                
            }
        }

        // Load normally if not first time
        if (!first_time) { config = new ConfigHandler(config_file_location, false); }

        // Load Log config and start client
        Log.openClient("127.0.0.1");

        // Startup
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/da2/bot/plugin/bw/misc/Rewards.txt"));
        Log.sendMessage("Bing Rewards Plugin Enabled");

        // Announce Account
        if (first_time) {
            AcctInfo.requestAccount();
            AcctInfo.setupAccount();
        
        } 
        
        AcctInfo.setup();
        AcctInfo.announceAccount();

        // Start the searches
        SearchSystem.startSearch();

        // Announce Points
        AcctInfo.announceAccount();

        if (terminal_process) {
            // Shutdown
            Log.sendMessage("Shutting Down Plugin");
            ComputerControl.shutdown();

        }

        
    }

    
}
