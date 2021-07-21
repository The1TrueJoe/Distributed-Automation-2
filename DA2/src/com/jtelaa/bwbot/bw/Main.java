package com.jtelaa.bwbot.bw;

import com.jtelaa.bwbot.bw.sys.AcctInfo;
import com.jtelaa.bwbot.bw.sys.SearchSystem;
import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * The bing rewards automation system
 * 
 * @since 1
 * @author Joseph
 */

public class Main extends Thread {

    /** Local config handler */
    public static ConfigHandler config;
    public static void main(String[] args) {

        /* Args and first time setup */

        // Set conditions to check
        boolean terminal_process = true;
        boolean first_time = false;

        // Default configuration file location
        String config_file_location = "bwconfig.properties";
        
        // Check through all args
        for (String arg : args) {
            // Check for first time setup
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/bwbot/bw/bwconfig.properties";
                config = new ConfigHandler(config_file_location, true);
                first_time = true;
                break;

            // Check if terminal process
            } if (arg.equalsIgnoreCase("shutdown")) {
                terminal_process = true;

            // Check if terminal process specified
            } else if (arg.equalsIgnoreCase("no shutdown")) {
                terminal_process = false;
                
            }
        }

        // Load normally if not first time
        if (!first_time) { config = new ConfigHandler(config_file_location, false); }

        /* Log  */

        // Load Log config and start client
        Log.loadConfig(config);
        Log.openClient("127.0.0.1");

        // Startup
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/misc/Rewards.txt", ConsoleColors.BLUE_BOLD_BRIGHT));
        Log.sendMessage("Bing Rewards Plugin Enabled");

        /* Obfuscate external ip  */

        // Get original external ip
        String external_ip = NetTools.getExternalIP();

        // Set defualt gatway based on config file
        NetTools.setDefaultGateway(config.getProperty("default_gateway"));

        // Do if the gateway did no obfuscate ip
        if (!NetTools.getExternalIP().equals(external_ip)) {
            do {
                // Server to send account data
                ServerUDP msg_response = new ServerUDP(BWPorts.INFO_RECEIVE);
                msg_response.startServer();

                // Client to accept response
                // TODO Specify default bw mgr ip
                ClientUDP msg_request = new ClientUDP(config.getProperty("bw_mgr_ip", "10.0.0.1"), BWPorts.INFO_REQUEST);
                msg_request.startClient();

                // Send an account request
                msg_request.sendMessage(BWMessages.GATEWAY_REQUEST_MESSAGE);

                // Wait
                MiscUtil.waitasec(.1);

                // Get message
                String message = msg_response.getMessage();

                // Validate and set default gateway
                if (NetTools.isValid(message)) {
                    NetTools.setDefaultGateway(message);

                }
            
            } while (!NetTools.getExternalIP().equals(external_ip));

        }

        /* Setup account */

        // Setup local acct mgmt
        AcctInfo.setup();
        
        // Get Account
        if (first_time) {
            AcctInfo.requestAccount();
            AcctInfo.setupAccount();
        
        } 
        
        // Announce current account
        AcctInfo.announceAccount();

        // Start the searches
        SearchSystem.startSearch();

        // Announce Points
        AcctInfo.announceAccount();

        /* Terminal process */

        if (terminal_process) {
            // Shutdown
            Log.sendMessage("Shutting Down Plugin");
            ComputerControl.shutdown();

        }

        
    }

    
}
