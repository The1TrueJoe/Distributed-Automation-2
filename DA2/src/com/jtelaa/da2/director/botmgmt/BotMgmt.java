package com.jtelaa.da2.director.botmgmt;

import com.jtelaa.da2.lib.control.QueuedCommandSender;

public class BotMgmt {

    public static final String INVALID_ID_MESSAGE = "Invalid Bot ID";
    public static final String BOT_ENABLE_MESSAGE = "Wake Up Bot!";

    private static QueuedCommandSender cmd_send;

    public static Bot getBot(int bot_id) {
        return new Bot();
    }

    public static void sendCommand(Bot bot, String command) {
        if (cmd_send.isAlive()) {
            cmd_send.add(bot.getIP(), command);
        }
    }
    
}
