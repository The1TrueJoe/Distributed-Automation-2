package com.jtelaa.da2.director.botmgmt;

import com.jtelaa.da2.lib.control.QueuedCommandSender;

public class BotMgmt {

    public static final String INVALID_ID_MESSAGE = "Invalid Bot ID";
    public static final String BOT_ENABLE_MESSAGE = "Wake Up Bot!";

    private static QueuedCommandSender cmd_send;
    private static HeartbeatServer hbeat_server;

    public static void start() {
        cmd_send = new QueuedCommandSender();
        cmd_send.start();

        hbeat_server = new HeartbeatServer();
        hbeat_server.start();
        
    }

    public static Bot getBot(int bot_id) {
        // TODO add ability to pull from database of bots
        return new Bot("127.0.0.1", bot_id);
    }

    public static void sendCommand(Bot bot, String command) {
        if (cmd_send.isAlive()) {
            cmd_send.add(bot.getIP(), command);
        }
    }
    
}
