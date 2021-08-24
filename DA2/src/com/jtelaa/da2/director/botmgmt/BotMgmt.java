package com.jtelaa.da2.director.botmgmt;

import java.io.File;
import java.util.ArrayList;

import com.jtelaa.da2.director.botmgmt.heartbeat.HeartbeatServer;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandSender;
import com.jtelaa.da2.lib.files.FileUtil;


// TODO comment

public class BotMgmt {

    public volatile HeartbeatServer h_beat_server;

    private volatile ArrayList<Bot> all_bots;

    public volatile QueuedCommandSender cmd_send;

    /**
     * Starts the processes
     */

    public void start() {
        cmd_send = new QueuedCommandSender();
        cmd_send.start();

        h_beat_server = new HeartbeatServer();
        h_beat_server.start();
        
    }

    public synchronized void add(Bot bot) { all_bots.add(bot); }

    /** @return Active bots */
    public synchronized ArrayList<Bot> getActiveBots() { return h_beat_server.getActiveBots(); }

    /** @return All bots */
    public synchronized ArrayList<Bot> getAllBots() { return all_bots; }

    /**
     * Import bot objects from files
     * 
     * @param bots Bots file
     */

    public synchronized void importBots(String bots) { importBots(new File(bots)); }

    /**
     * Import bot objects from files
     * 
     * @param bots Bots file
     */

    public synchronized void importBots(File bots) {
        // Read bot objects
        ArrayList<Object> not_casted_bots = FileUtil.readObjectFromFile(bots);
        all_bots = new ArrayList<Bot>();

        // Turn all bots into objects
        for (Object not_casted_bot : not_casted_bots) {
            all_bots.add((Bot) not_casted_bot);

        }

    }

    /**
     * Writes all objects to a file
     * 
     * @param bots File for bot exports
     */

    public synchronized void exportBots(File bots) {
        FileUtil.writeObjectToFile(all_bots.toArray(), bots);

    }


    /**
     * Returns a bot based on id
     * 
     * @param bot_id Bot id to look for
     * 
     * @return Bot
     */

    public synchronized Bot getBot(int bot_id) {
        for (Bot bot : all_bots) {
            if (bot_id == bot.getID()) {
                return bot;

            }
        }

        return null;
    }

    /**
     * Send command to all active bots
     * 
     * @param command Command to send (Does not factor in address)
     */

    public synchronized void sendDistributedCommand(Command command) { sendDistributedCommand(new Command[] { command }); }

    /**
     * Send multiple commands to all active bots
     * 
     * @param commands Commands to send (Does not factor in address)
     */
    
    public synchronized void sendDistributedCommand(Command[] commands) {
        // Iterate all bots
        for (Bot bot : h_beat_server.getActiveBots()) {
            // Iterate all commands
            for (Command command : commands) {
                // Send command to bots
                sendCommand(command.changeDestination(bot.getIP()));

            }
        }
    }

    /**
     * Add a command to the queue
     * 
     * @param bot Bot to send command to
     * @param command Command to send
     */

    public synchronized void sendCommand(Bot bot, String command) { sendCommand(new Command(command, bot.getIP()));}

    /**
     * Add a command to the queue
     * 
     * @param command Command to send
     */
    
    public synchronized void sendCommand(Command command) { sendCommand(new Command[] { command }); }

    /**
     * Adds commands to the queue
     * 
     * @param commands
     */
    
    public synchronized void sendCommand(Command[] commands) {
        for (Command command : commands) {
            cmd_send.add(command);
        }
    }
    
}
