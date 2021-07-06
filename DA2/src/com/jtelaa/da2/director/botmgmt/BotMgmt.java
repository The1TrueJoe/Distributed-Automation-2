package com.jtelaa.da2.director.botmgmt;

import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandSender;


// TODO comment

public class BotMgmt {

    private static QueuedCommandSender cmd_send;
    private static HeartbeatServer hbeat_server;

    public static void start() {
        cmd_send = new QueuedCommandSender();
        cmd_send.start();

        hbeat_server = new HeartbeatServer();
        hbeat_server.start();
        
    }

    /**
     * 
     * @param bot_id
     * @return
     */

    public static Bot getBot(int bot_id) {
        for (Bot bot : hbeat_server.getActiveBots()) {
            if (bot_id == bot.getID()) {
                return bot;

            }
        }

        return null;
    }

    /**
     * 
     * @param command
     */

    public static void sendDistributedCommand(Command command) { sendDistributedCommand(new Command[] { command }); }

    /**
     * 
     * @param commands
     */
    
    public static void sendDistributedCommand(Command[] commands) {
        for (Bot bot : hbeat_server.getActiveBots()) {
            for (Command command : commands) {
                sendCommand(command.changeDestination(bot.getIP()));

            }
        }
    }

    /**
     * 
     * @param bot
     * @param command
     */

    public static void sendCommand(Bot bot, String command) { sendCommand(new Command(command, bot.getIP()));}

    /**
     * 
     * @param command
     */
    
    public static void sendCommand(Command command) { sendCommand(new Command[] { command }); }

    /**
     * 
     * @param commands
     */
    
    public static void sendCommand(Command[] commands) {
        for (Command command : commands) {
            cmd_send.add(command);
        }
    }
    
}
