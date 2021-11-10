package com.jtelaa.bwbot.bw.sys;

import java.util.Random;

import com.jtelaa.bwbot.bw.Main;
import com.jtelaa.bwbot.bw.util.BWControls;
import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.da2.lib.config.PropertiesUtils;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.ManualPort;
import com.jtelaa.da2.lib.net.ports.Ports;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * System for searching using Bing
 * 
 * @since 1
 * @author Joseph
 */

public class SearchSystem {

    /** Number of searches executed */
    private static int executed_pc_searches;

    /** Number of mobile searches executed */
    private static int executed_mobile_searches;

    /** Maxiumum number of pc searches */
    public static int max_pc_searches;

    /** Maximum number of mobile searches */
    public static int max_mobile_searches;

    /** Query generator ip */
    private static String query_ip;

    /**
     * Setup search system
     */

    public static void setup() {
        // Load IP
        query_ip = Main.config.getProperty("qry_gen_ip", Main.DEFAULT_QUERY_GENERATOR);

        // Calc Max Searches
        populateSearchMax();

        // If first time setup
        if (!Main.first_time) { 
            // Rand
            Random rng = new Random();

            // Do mobile searches
            if (rng.nextInt(10) < 3) {
                Main.config.setProperty("do_mobile", "false");

            }
        }
    }
    
    /**
     * Starts the searches
     */

    public static void start() {
        // PC Searches
        Log.sendMessage("Starting PC Search");
        executed_pc_searches = runSearches(executed_pc_searches, max_pc_searches);

        // Done
        Log.sendMessage("Done..");

        // Do mobile searches 
        if (PropertiesUtils.isTrue(Main.config, "do_mobile", false)) {
            // Enter mobile
            BWControls.openChrome();
            BWControls.enterMobile();

            // Search
            Log.sendMessage("Starting Mobile Search");
            executed_mobile_searches = runSearches(executed_mobile_searches, max_mobile_searches);

            // Exit mobile
            BWControls.exitMobile();

            // Done
            Log.sendMessage("Done..");

        }
    }

    /**
     * Runs the individual searches given parameters
     * 
     * @param current_count The current number of searches 
     * @param max Number of searches to reach
     * 
     * @return The new current number of searches
     */

    public static int runSearches(int current_count, int max) {
        // Local var call
        Random rand = new Random();
        Query query;

        // Run n searches based on current count and upper limit
        for (; current_count < max; current_count++) {
            // Request Search
            Log.sendMessage("Requesting search " + current_count);
            query = requestQuery(query_ip);
            
            // Run search, wait, and the close
            BWControls.openBing(query);
            MiscUtil.waitamoment(rand.nextInt(900000));
            BWControls.closeTab();

        }

        // Close window
        BWControls.closeWindow();

        // Return
        return current_count;
    }

    /**
     * Generates the maximum number searches for the day
     */

    private static void populateSearchMax() {
        // Rand
        Random rand = new Random();

        // Generate search max
        max_pc_searches = rand.nextInt(25);
        max_mobile_searches = rand.nextInt(25);

    }

    /**
     * Sends a request for a query
     * 
     * @return The search query
     */

    public static Query requestQuery() {
        return requestQuery(query_ip, BWPorts.QUERY_REQUEST.getPort(), BWPorts.QUERY_RECEIVE.getPort());

    }

    /**
     * Sends a request for a query
     * 
     * @return The search query
     * 
     * @param ip IP to contact
     */

    public static Query requestQuery(String ip) {
        return requestQuery(ip, BWPorts.QUERY_REQUEST.getPort(), BWPorts.QUERY_RECEIVE.getPort());

    }

    /**
     * Sends a request for a query
     * 
     * @return The search query
     * 
     * @param request Request port
     * @param receive Receive port
     * 
     * @deprecated Use port object
     */

    @Deprecated
    public static Query requestQuery(String ip, int request, int receive) {
        return requestQuery(ip, new ManualPort(request), new ManualPort(receive));

    }

    /**
     * Sends a request for a query
     * 
     * @return The search query
     * 
     * @param request Request port
     * @param receive Receive port
     */

    public static Query requestQuery(String ip, Ports request, Ports receive) {
        // Local var setup
        String response = "";

        // Setup request client
        ClientUDP send = new ClientUDP(ip, receive);
        send.startClient();

        // Send & close
        send.sendMessage(BWMessages.QUERY_REQUEST_MESSAGE);
        send.closeClient();

        // Start server
        ServerUDP get = new ServerUDP(request);
        get.startServer();
        
        // Wait for responses
        do {
            // Get response
            response = get.getMessage();

            // Wait
            MiscUtil.waitamoment(100);

        } while (!MiscUtil.notBlank(response));

        // Close the server
        get.closeServer();

        // Return the query
        return new Query(response, false);

    }


    public static void main(String[] args) {
        // Setup log
        Log.app_verbose = true;
        Log.log_verbose = false;

        // Send welcome
        Log.sendMessage("Starting query request test");
        Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/misc/Rewards.txt", ConsoleColors.CYAN_BOLD_BRIGHT));
        
        // IP
        query_ip = Main.DEFAULT_QUERY_GENERATOR;

        // Run 100 searches
        for (int i = 0; i < 100; i++) {
            Log.sendMessage(requestQuery().toString());
        }


    }

}
