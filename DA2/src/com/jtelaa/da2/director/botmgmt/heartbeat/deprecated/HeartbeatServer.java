package com.jtelaa.da2.director.botmgmt.heartbeat.deprecated;

import java.util.ArrayList;

import com.jtelaa.da2.director.Main;
import com.jtelaa.da2.director.botmgmt.heartbeat.MultipleBeats;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * Heartbeat Server
 * 
 * @since 2
 * @author Joseph
 * 
 * @deprecated NEW Heartbeat will be based off of ICMP
 */

 @Deprecated
public class HeartbeatServer extends Thread {

    /** If the server is operating in child mode */
    private volatile boolean child_mode = false;

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

        // Server
        ServerUDP server = new ServerUDP(SysPorts.HEARTBEAT);

        // Time since last update (Child Mode)
        long last_update_time = System.currentTimeMillis();

        // Client to master server
        ClientUDP client = new ClientUDP(master_ip, SysPorts.HEARTBEAT_MASTER); 
        if (child_mode) { client.startClient(); }

        while (!run) {
            MiscUtil.waitasec();
            
        }

        while(run) {
            // Get the message
            message = server.getObject();

            // If in child mode an the object is correct
            if (child_mode && MultipleBeats.class.isInstance(message)) {
                // Get ids
                MultipleBeats beats = (MultipleBeats) message;
                int[] ids = beats.ids;

                // Mark all ids as alive
                for (int id : ids) {
                    markAlive(id);

                }

            // If not in child mode (Object likely a string)
            } else  {
                // If it is a beat message
                if (MgmtMessages.BEAT.equals(message)) {
                    // Cast the message as a String
                    String message_str = (String) message;
                
                    // Get bot & mark alive
                    int id = Integer.parseInt(message_str.substring(MgmtMessages.BEAT.getMessage().length()));
                    markAlive(id);
                
                } else {
                    // Check if all bots are recently alive
                    update();
                
                }

            }

            // If child mode
            if (child_mode) {
                // If it is the correct time
                if (System.currentTimeMillis() - last_update_time >= HEARTBEAT_INTERVAL) {
                    // Create array of ids
                    int[] ids = new int[active_bots.size()];

                    // Add ids to activity list
                    for (int i = 0; i < ids.length; i++) {
                        ids[i] = active_bots.get(i).getID();

                    }

                    // Send list to master
                    client.sendObject(new MultipleBeats(ids));

                    // Update time
                    last_update_time = System.currentTimeMillis();
                }

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
        // Get bot
        Bot bot = Main.bt_mgmt.getBot(id);

        // Update bot and mark it alive
        bot.last_seen = System.currentTimeMillis();
        active_bots.add(bot);

    }

    /**
     * Iterates through all bots to check if they haven't responded in a while
     */

    public void update() {
        // Last time it could be seen if it missed two beats
        long earliest_alive = System.currentTimeMillis() - (2 * HEARTBEAT_INTERVAL);

        // Check all bots
        for (Bot bot : active_bots) {
            if (bot.last_seen < earliest_alive) {
                active_bots.remove(bot);

            }
        }
    }

    /**
     * Flush the list of active bots
     */

    public void flush() {
        active_bots = new ArrayList<Bot>();

    }


}
