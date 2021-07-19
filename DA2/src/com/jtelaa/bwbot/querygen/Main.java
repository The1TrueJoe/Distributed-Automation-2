package com.jtelaa.bwbot.querygen;

import com.jtelaa.bwbot.querygen.processes.QueryGenerator;
import com.jtelaa.bwbot.querygen.processes.QueryServer;
import com.jtelaa.bwbot.querygen.processes.RequestServer;
import com.jtelaa.bwbot.querygen.util.CLI;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.console.ConsoleBanners;
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
// TODO Comment

public class Main {

    /** The cli local object */ 
    public static CLI cli;
    
    /** Local configuration handler */
    public static ConfigHandler my_config;

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

        // Start Logging
        Log.loadConfig(my_config);
        Log.openClient("logging_server_ip");

        // Startup
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/querygen/misc/QueryGen.txt"));
        Log.sendMessage("Query Generator Enabled");

        // Request server setup
        RequestServer req_srv = new RequestServer();
        Log.sendMessage("Starting request server");
        req_srv.start();
        
        // Query server setup
        QueryServer qry_serv = new QueryServer();
        Log.sendMessage("Starting query server");
        qry_serv.start();

        // Query generator setup
        QueryGenerator qry_gen = new QueryGenerator();
        Log.sendMessage("Starting query generator");
        qry_gen.start();

        // Start CLI
        cli = new CLI();
        cli.start();

        // Wait
        MiscUtil.waitasec();

        // While CLI is available
        while (cli.cli_enabled) {

        }

        Log.sendLogMessage("Done! Shutting down");
        Log.closeLog();
        req_srv.stopServer();
        qry_serv.stopReceiver();
        qry_gen.stopGen();
    }
    
}
