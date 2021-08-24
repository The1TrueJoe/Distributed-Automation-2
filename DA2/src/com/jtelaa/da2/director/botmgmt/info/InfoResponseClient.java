package com.jtelaa.da2.director.botmgmt.info;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.director.Main;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.SysPorts;

// TODO Comment

/**
 * 
 */

public class InfoResponseClient extends Thread {

    private volatile static Queue<Command> request_queue;

    private ClientUDP cmd_tx;

    /**
     * Adds a request to the queue
     * 
     * @param message
     * @param origin
     */

    public synchronized static void addRequest(String message, Bot origin) {
        if (MiscUtil.notBlank(message) && origin.isReachable()) {
            request_queue.add(new Command(message, NetTools.getLocalIP(), origin.getIP()));

        }
    }
    
    public void run() {
        request_queue = new LinkedList<>();

        while (!run) {
            MiscUtil.waitasec();
            
        }

        while (run) {
            fillRequest();
        }
    }

    /** Boolean to control the receiver */
    private volatile boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopReceiver() { run = false; }

    /** Checks if the receier is ready */
    public synchronized boolean receiverReady() { return run; }
    // TODO Implement

    /**
     * Fills the search query request by establishing a connection
     * with the bot
     */

    private void fillRequest() {
        if (request_queue.size() == 0) {
            MiscUtil.waitasec(.10);
            return;

        }
        
        Command command = request_queue.poll();
        String message = command.command();

        if (MgmtMessages.ID_REQUEST.equals(message)) {
            ArrayList<Bot> bots = Main.bt_mgmt.getAllBots();
            int max_id = 1;

            for (Bot bot : bots) {
                if (bot.getID() > max_id) {
                    max_id = bot.getID();

                }
            }

            sendMessage(command.destination(), (max_id + 1) + "");

        } 
    }

    private void sendMessage(String dest, String message) {
        cmd_tx = new ClientUDP(dest, SysPorts.INFO_RESPONSE);

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(message);
            cmd_tx.closeClient();

        }
    }
}

