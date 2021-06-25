package com.jtelaa.da2.bot.plugin.bw;

import java.awt.KeyEvent;
import java.util.Random;

import javax.sound.sampled.Port;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.ComputerControl;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.querygen.util.Query;

/**
 * The bing rewards automation system
 * 
 * @since 1
 * @author Joseph
 */

public class BingRewards extends Thread {

    public final String BING_URL = "bing.com/search?1=";

    private int executed_pc_searches;
    private int executed_mobile_searches;

    public int max_pc_searches;
    public int max_mobile_searches;

    private String query_ip;

    public void run() {
        // Startup
        Log.sendMessage("Bing Rewards Plugin Enabled");

        // Load query ip
        query_ip = Main.me.getQueryGenIP();

        // Generate random search count
        populateSearchMax();

        // Start the searches
        startSearch();

        // Shutdown
        Log.sendMessage("Shutting Down");
        ComputerControl.shutdown();

    }

    /**
     * Starts the searches
     */

    public void startSearch() {
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

    public int runSearches(int current_count, int max) {
        Random rand = new Random();
        Query query;

        for (; current_count < max; current_count++) {
            query = Query.requestQuery(query_ip, Ports.QUERY_REQUEST, Ports.QUERY_RECEIVE);
            
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

    private void populateSearchMax() {
        Random rand = new Random();

        max_pc_searches = rand.nextInt(25);
        max_mobile_searches = rand.nextInt(25);

    }

    /** Opens Bing on chrome to the specified search query */
    public void openBing(Query query) { openChrome(BING_URL + query.getQuery());}

    /** Opens chrome to the specified URL */
    public void openChrome(String args) { ComputerControl.sendCommand("chrome.exe " + args); }

    /** Sets chrome to mobile mode */
    public void enterMobile() { chromeInspect(); chromeMobile(); }

    /** Returns chrome from mobile mode */
    public void exitMobile()  { chromeMobile(); chromeInspect(); }

    /** Opens chrome in inspect */
    private void chromeInspect() { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_I}); }
    
    /** Puts chrome into mobile mode from inspect */
    private void chromeMobile()  { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_M}); }

    
}
