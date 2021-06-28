package com.jtelaa.da2.bot.plugin.bw.util;

import java.awt.event.KeyEvent;
import java.util.Random;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.querygen.util.Query;

/**
 * System for searching using Bing
 * 
 * @since 1
 * @author Joseph
 */

public class SearchSystem {

    private static int executed_pc_searches;
    private static int executed_mobile_searches;

    public static int max_pc_searches;
    public static int max_mobile_searches;

    private static String query_ip;

    public static final String BING_URL = "bing.com/search?q=";
    
    /**
     * Starts the searches
     */

    public static void startSearch() {
        // Load IP
        query_ip = Main.me.getQueryGenIP();

        // Calc Max Searches
        populateSearchMax();

        // PC Searches
        Log.sendMessage("Starting PC Search");
        executed_pc_searches = runSearches(executed_pc_searches, max_pc_searches);

        // Mobile Searches
        Log.sendMessage("Starting Mobile Search");
        enterMobile();
        executed_mobile_searches = runSearches(executed_mobile_searches, max_mobile_searches);
        exitMobile();

        Log.sendMessage("Done..");

    }

    /**
     * Runs the individual searches given parameters
     * 
     * @param current_count The current number of searches 
     * @param max Number of searches to reach
     * @return The new current number of searches
     */

    public static int runSearches(int current_count, int max) {
        Random rand = new Random();
        Query query;

        for (; current_count < max; current_count++) {
            query = Query.requestQuery(query_ip, Ports.QUERY_REQUEST.getPort(), Ports.QUERY_RECEIVE.getPort());
            
            openBing(query);

            try {
                Thread.sleep(rand.nextInt(900000));

            } catch (Exception e) {
                Log.sendMessage(e.getMessage());

            }
        }

        return current_count;
    }

    /**
     * Generates the maximum number searches for the day
     */

    private static void populateSearchMax() {
        Random rand = new Random();

        max_pc_searches = rand.nextInt(25);
        max_mobile_searches = rand.nextInt(25);

    }

    /** Opens Bing on chrome to the specified search query */
    public static void openBing(Query query) { openChrome(BING_URL + query.getQuery());}

    /** Opens chrome to the specified URL */
    public static void openChrome(String args) { ComputerControl.sendCommand(new Command("chrome.exe " + args)); }

    /** Sets chrome to mobile mode */
    public static void enterMobile() { chromeInspect(); chromeMobile(); }

    /** Returns chrome from mobile mode */
    public static void exitMobile()  { chromeMobile(); chromeInspect(); }

    /** Opens chrome in inspect */
    private static void chromeInspect() { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_I}); }
    
    /** Puts chrome into mobile mode from inspect */
    private static void chromeMobile()  { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_M}); }

}
