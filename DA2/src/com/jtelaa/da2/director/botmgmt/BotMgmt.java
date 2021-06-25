package com.jtelaa.da2.director.botmgmt;

import com.jtelaa.da2.lib.net.Ports;

public class BotMgmt {

    private static QueuedCommandSender cmd_send;

    public static Bot getBot(int bot_id) {
        return new Bot();
    }



    public static void sendCommand(String ip, String command) {
        if (cmd_send.isAlive()) {
            cmd_send.add(ip, command);
        }
    }
    
}
