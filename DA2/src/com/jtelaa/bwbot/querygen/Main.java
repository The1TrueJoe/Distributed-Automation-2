package com.jtelaa.bwbot.querygen;

import com.jtelaa.bwbot.querygen.processes.QueryGenerator;
import com.jtelaa.bwbot.querygen.processes.QueryServer;
import com.jtelaa.bwbot.querygen.processes.RequestServer;
import com.jtelaa.bwbot.querygen.util.RemoteCLI;
import com.jtelaa.bwbot.querygen.util.SysCLI;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 * Main for the query generation application
 * 
 * @author Joseph
 * @since 2
 * 
 * @see com.jtelaa.bwbot.bwlib.Query
 * @see com.jtelaa.bwbot.querygen.processes.QueryGenerator
 */

public class Main {

    /** The remote cli local object */ 
    public static RemoteCLI rem_cli;

    /** The system cli local object */ 
    public static SysCLI sys_cli;
    
    /** Local configuration handler */
    public static ConfigHandler my_config;

    public static void main(String[] args) {
        args = new String[] {"setup"};

        // Default configuration file location
        String config_file_location = "config.properties";

        // Check for first time setup
        boolean first_time = false;
        for (String arg : args) {
            if (arg.equalsIgnoreCase("setup")) {
                config_file_location = "com/jtelaa/bwbot/querygen/config.properties";
                System.out.println("Loaded internal config");
                my_config = new ConfigHandler(config_file_location, true);
                first_time = true;

                break;

            }
        }

        // Load normally if not first time
        if (!first_time) { my_config = new ConfigHandler(config_file_location, false); }

        // Start Logging
        Log.loadConfig(my_config);

        // Startup
        Log.sendSysMessage("Starting.....\n");
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/QueryGen.txt", ConsoleColors.YELLOW));
        Log.sendSysMessage("\n\n\n");

        // Start logging client
        Log.openClient(my_config.getLogIP());

        // Request server setup
        RequestServer req_srv = new RequestServer();
        Log.sendMessage("Starting request server", ConsoleColors.GREEN);
        req_srv.start();
        
        // Query server setup
        QueryServer qry_serv = new QueryServer();
        Log.sendMessage("Starting query server", ConsoleColors.GREEN);
        qry_serv.start();

        // Query generator setup
        QueryGenerator qry_gen = new QueryGenerator();
        Log.sendMessage("Starting query generator", ConsoleColors.GREEN);
        qry_gen.start();

        // Done
        Log.sendMessage("Main: Done", ConsoleColors.GREEN);

        // Remote CLI
        if (my_config.runRemoteCLI()) {
            Log.sendMessage("Remote CLI Allowed");
            rem_cli = new RemoteCLI();
            rem_cli.start();

        } else {
            Log.sendMessage("No Remote CLI");

        }

        // Local CLI
        if (my_config.runLocalCLI()) {
            Log.sendMessage("Local CLI Allowed");
            sys_cli = new SysCLI();
            sys_cli.start();

        } else {
            Log.sendMessage("No Local CLI");

        }

        Log.sendMessage("Waiting 45s for CLI bootup");
        MiscUtil.waitasec(45);
        Log.sendMessage("Done waiting");

        if (my_config.runLocalCLI()) { sys_cli.runCLI(); }
        if (my_config.runRemoteCLI()) { rem_cli.runCLI(); }

        /*

        // Wait
        MiscUtil.waitasec();

        Log.sendLogMessage("Done! Shutting down");
        Log.closeLog();
        req_srv.stopServer();
        qry_serv.stopReceiver();
        qry_gen.stopGen();

        */
    }
    
}
