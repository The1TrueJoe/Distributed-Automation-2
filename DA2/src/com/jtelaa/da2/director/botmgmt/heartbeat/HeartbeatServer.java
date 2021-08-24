package com.jtelaa.da2.director.botmgmt.heartbeat;

import java.util.ArrayList;

import com.jtelaa.da2.director.Main;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Heartbeat Server
 * 
 * @since 2
 * @version 2
 * 
 * @author Joseph
 */

public class HeartbeatServer extends Thread {

    /** Logging prefix */
    private String log_prefix = "Heartbeat: ";

    /** If the server is operating in child mode */
    private volatile boolean child_mode = false;

    /** If the server is operating in child mode */
    private volatile boolean parent_mode = false;

    /** Master Heartbeat Server (If the server is operating in child mode) */
    private volatile String master_ip;

    /** Typical heartbeat interval */
    private volatile int HEARTBEAT_INTERVAL = 60000;

    /** List of active bots */
    private volatile ArrayList<Bot> active_bots;


    /** @return A list of all bots with active heartbeats */
    public synchronized ArrayList<Bot> getActiveBots() { return active_bots; }

    /**
     * Operate the heartbeat server in child mode
     * 
     * @param master_ip
     */

    public synchronized void operateAsChild(String master_ip) {
        child_mode = true;
        this.master_ip = master_ip;

    }


    public void run() {
        active_bots = new ArrayList<Bot>();
        Object message;

        // Time since last update (Child Mode)
        long last_update_time = System.currentTimeMillis();

        double duplicate_search_multiplier = 100;
        long dup_last_update_time = System.currentTimeMillis();

        // Client to master server
        ClientUDP client = new ClientUDP(master_ip, SysPorts.HEARTBEAT_MASTER, log_prefix);
        if (child_mode) { client.startClient(); }

        // Client to master server
        ServerUDP server = new ServerUDP(SysPorts.HEARTBEAT_MASTER, log_prefix);
        if (parent_mode) { server.startServer(); }

        while (!run) {
            MiscUtil.waitasec();
            
        }

        while(run) {
            if (child_mode) {
                // If it is the correct time
                if (System.currentTimeMillis() - last_update_time >= HEARTBEAT_INTERVAL) {
                    // Check for life
                    for (Bot bot : Main.bt_mgmt.getAllBots()) {
                        if (bot.isReachable()) {
                            markAlive(bot.getID());

                        } else {
                            active_bots.remove(bot);

                        }
                    }

                    // Create array of ids
                    int[] ids = new int[active_bots.size()];

                    // Add ids to activity list
                    for (int i = 0; i < ids.length; i++) {
                        ids[i] = active_bots.get(i).getID();

                    }

                    // Send list to master
                    client.sendObject(new MultipleBeats(ids));

                } else {
                    MiscUtil.waitasec();

                }

            } else if (parent_mode) {
                // Get the message
                message = server.getObject();

                // If in child mode an the object is correct
                if (MultipleBeats.class.isInstance(message)) {
                    // Get ids
                    MultipleBeats beats = (MultipleBeats) message;
                    int[] ids = beats.ids;

                    // Mark all ids as alive
                    for (int id : ids) {
                       markAlive(id);

                    }
                }


            }

            // Update time
            last_update_time = System.currentTimeMillis();

            // Check for duplicates 
            if (dup_last_update_time > System.currentTimeMillis() - (duplicate_search_multiplier * HEARTBEAT_INTERVAL)) {
                Log.sendMessage("Searching for duplicates");
                int result = removeDuplicates();

                if (result > 0 && duplicate_search_multiplier < Integer.MAX_VALUE - 1000) { 
                    duplicate_search_multiplier += 10; 
                
                } else if (result > 5 && duplicate_search_multiplier > Integer.MIN_VALUE + 1000) {
                    duplicate_search_multiplier -= 10;

                }

                dup_last_update_time = System.currentTimeMillis();

            }

            
        }

        // Close
        server.closeServer();

    }

    /** Boolean to control the receiver */
    private boolean run = false;

    /** Stops the command receiver */
    public synchronized void stopServer() { run = false; }

    /** Starts the command receiver */
    public synchronized void startServer() { run = true; }

    /** Checks if the receier is ready */
    public synchronized boolean receiverReady() { return run; }
    // TODO Implement

    /**
     * Check if a specified bot is alive (Based on recent heartbeat)
     * 
     * @param bot Bot to check
     * 
     * @return If the bot is alive
     */

    public synchronized boolean isAlive(Bot bot) {
        // Iterate through all bots
        for (Bot bot_to_check : active_bots) {
            if (bot.getID() == bot_to_check.getID()) {
                return true;

            }
        }

        return false;

    }

    /**
     * Mark a bot as alive
     * 
     * @param id Bot's id
     */

    public void markAlive(int id) {
        Bot current_bot = Main.bt_mgmt.getBot(id);

        if (active_bots.contains(current_bot)) {
            // Get bot and update time
            int index = active_bots.indexOf(current_bot);
            active_bots.get(index).last_seen = System.currentTimeMillis();

        } else {
            active_bots.add(current_bot);

        }
    }

    /**
     * Iterates through all bots to check if they haven't responded in a while
     */

    public void update() {
        // Last time it could be seen if it missed five beats
        long earliest_alive = System.currentTimeMillis() - (5 * HEARTBEAT_INTERVAL);

        // Check all bots
        for (Bot bot : active_bots) {
            // See when the earlies time seen was
            if (bot.last_seen < earliest_alive) {
                active_bots.remove(bot);

            }
        }

        
    }

    /**
     * Search for and remove duplicates in the active_bots
     * 
     * @return Number of duplicates
     */

    public int removeDuplicates() {
        // all ids to remove duplicates
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (Bot bot : active_bots) { ids.add(bot.getID()); }

        int num_of_duplicates = 0;

        for (int current_index = 0; current_index < ids.size(); current_index++) {
            int current_id = ids.get(current_index);

            for (int i = 0; i < ids.size(); i++) {
                if (i != current_index && ids.get(i) == current_id) {
                    if (active_bots.get(current_index).last_seen > active_bots.get(i).last_seen) {
                        num_of_duplicates++;
                        active_bots.remove(i);

                    }
                }
            }
        }

        Log.sendMessage(log_prefix + num_of_duplicates + " Duplicates Found and Removed");
        return num_of_duplicates;

    }

    /**
     * Flush the list of active bots
     */

    public void flush() {
        active_bots = new ArrayList<Bot>();

    }


}
