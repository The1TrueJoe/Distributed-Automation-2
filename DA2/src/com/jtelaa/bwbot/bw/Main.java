package com.jtelaa.bwbot.bw;

import java.util.Properties;

import com.jtelaa.bwbot.bw.sys.AcctInfo;
import com.jtelaa.bwbot.bw.sys.Redeem;
import com.jtelaa.bwbot.bw.sys.SearchSystem;
import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.da2.bot.util.RemoteCLI;
import com.jtelaa.da2.bot.util.SysCLI;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.config.PropertiesUtils;
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

public class Main {

    // Defaults

    /** Default VPN Gateway */
    public static final String DEFAULT_GATEWAY = "172.16.4.1";

    /** Default point manager (Should remain constant) */
    public static final String DEFAULT_POINT_MANAGER = "172.16.3.1";

    /** Default Query Generator */
    public static final String DEFAULT_QUERY_GENERATOR = "172.16.3.101";

    /** Default Director IP */
    public static final String DEFAULT_DIRECTOR_IP = "172.16.2.2";

    // Fields

    /** The remote cli local object */ 
    public static RemoteCLI rem_cli;

    /** The system cli local object */ 
    public static SysCLI sys_cli;

    /** Local config handler */
    public static Properties config;

    /** Main bot objcect */
    public static Bot me;

    /** First time run */
    public static boolean first_time;
    
    public static void main(String[] args) {

        /* Args and first time setup */

        // Set conditions to check
        boolean terminal_process = true;
        first_time = false;

        // Default configuration file location
        String config_file_location = "bwconfig.properties";
        
        // Check through all args
        for (String arg : args) {
            // Check for first time setup
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/bwbot/bw/bwconfig.properties";
                config = PropertiesUtils.importConfig(config_file_location);
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
        if (!first_time) { config = PropertiesUtils.importConfig(config_file_location); }

        /* Log  */

        // Load Log config and start client
        Log.loadConfig(config);
        Log.openClient("127.0.0.1"); // TODO Read Properties and use director as default

        // Startup
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/misc/Rewards.txt", ConsoleColors.CYAN_BOLD_BRIGHT));
        Log.sendMessage("Bing Rewards Plugin Enabled");

        // Request Bot (first time only)
        if (first_time) {
            // Server to receive bot info
            ServerUDP msg_response = new ServerUDP(BWPorts.INFO_RECEIVE);
            msg_response.startServer();

            // Client to send request
            ClientUDP msg_request = new ClientUDP("127.0.0.1", BWPorts.INFO_REQUEST);
            msg_request.startClient();

            // Set default
            me = new Bot("127.0.0.1");

            // Prep message
            Object message;

            do {
                // Send message 
                msg_request.sendMessage(BWMessages.BOT_REQUEST_MESSAGE);

                // Wait
                MiscUtil.waitasec(.1);

                // Get message
                message = msg_response.getObject();

            // Check if can cast
            } while (!Bot.class.isInstance(message));

            // Cast and apply
            me = (Bot) message;

        }

        /* Obfuscate external ip  */

        // Get original external ip
        String external_ip = NetTools.getExternalIP();

        // Set defualt gatway based on config file
        NetTools.setDefaultGateway(config.getProperty("default_gateway", DEFAULT_GATEWAY));

        // Do if the gateway did no obfuscate ip
        if (!NetTools.getExternalIP().equals(external_ip) || config.getProperty("default_gateway", DEFAULT_GATEWAY).equals(DEFAULT_GATEWAY)) {
            // Server to send account data
            ServerUDP msg_response = new ServerUDP(BWPorts.INFO_RECEIVE);
            msg_response.startServer();

            // Client to accept response
            ClientUDP msg_request = new ClientUDP(config.getProperty("bw_mgr_ip", DEFAULT_POINT_MANAGER), BWPorts.INFO_REQUEST);
            msg_request.startClient();

            // Attempts
            int counter = 1;
            boolean has_been_obfuscated = false;

            do {

                // Default
                if (counter >= 10) {
                    Log.sendMessage("Using system default: " + DEFAULT_GATEWAY);
                    NetTools.setDefaultGateway(DEFAULT_GATEWAY);

                    // Basically just gives up if this failed
                    has_been_obfuscated = true;
                }

                // Log obfuscation attempt
                Log.sendMessage("Attempting to obfuscate: " + external_ip + "(" + counter + ")");

                // Send a gateway request
                msg_request.sendMessage(BWMessages.GATEWAY_REQUEST_MESSAGE);

                // Wait
                MiscUtil.waitasec(.1);

                // Get message
                String message = msg_response.getMessage();

                // Validate and set default gateway
                if (NetTools.isValid(message)) {
                    NetTools.setDefaultGateway(message);
                    config.setProperty("default_gateway", message);

                }

                // Validate obfuscation
                if (NetTools.getExternalIP().equals(external_ip)) {
                    has_been_obfuscated = true;

                } else {
                    // Log and increment counter
                    Log.sendMessage("Obfuscation has failed (" + counter + ")");
                    counter++;

                }
            
            } while (!has_been_obfuscated);

            // Set and log new external ip
            external_ip = NetTools.getExternalIP();
            Log.sendMessage("The new external ip is " + external_ip);

            msg_response.closeServer();
            msg_request.closeClient();
            
        }

        /* Setup account */

        // Setup local systems
        AcctInfo.setup(first_time);
        Redeem.setup();
        SearchSystem.setup();
        
        /* Search process */
        
        // Announce current account
        AcctInfo.announceAccount();

        // Done
        Log.sendMessage("Main: Done", ConsoleColors.GREEN);

        configBootup();

        // Start the searches
        SearchSystem.start();

        // Announce Points
        AcctInfo.announceAccount();

        /* Terminal process */

        if (terminal_process) {
            // Shutdown
            Log.sendMessage("Shutting Down Plugin");
            ComputerControl.shutdown();

        }

        
    }

    private static void configBootup() {
        // Remote CLI
        if (me.config.getProperty("remote_cli", "false").equalsIgnoreCase("true")) {
            Log.sendMessage("CLI: Remote Cli Enabled");
            rem_cli = new RemoteCLI();
            rem_cli.start();

        } else {
            Log.sendMessage("CLI: No Remote CLI");

        }

        // Local CLI
        if (me.config.getProperty("local_cli", "true").equalsIgnoreCase("true")) {
            Log.sendMessage("CLI: Local CLI Allowed");
            sys_cli = new SysCLI();
            sys_cli.start();

        } else {
            Log.sendMessage("CLI: No Local CLI");

        }

        Log.sendMessage("CLI: Waiting 45s for CLI bootup");
        MiscUtil.waitasec(45);
        Log.sendMessage("CLI: Done waiting");

        if (me.config.getProperty("remote_cli", "false").equalsIgnoreCase("true")) { rem_cli.runCLI(); }
        if (me.config.getProperty("local_cli", "true").equalsIgnoreCase("true")) { sys_cli.runCLI(); }

    }

    
}
