package com.jtelaa.bwbot.bw_manager.processes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.bwbot.bw_manager.accounts.AccountGen;
import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.da2.lib.bot.Bot;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.files.ObjectUtils;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.client.ClientUDP;

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
    private boolean run = true;

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

        if (message.contains(BWMessages.ACCOUNT_REQUEST_MESSAGE.getMessage())) {
            try {
                command.forward();
                sendCommand(command.changeCommand(BWMessages.ACCOUNT_REPONSE_MESSAGE.getMessage()));
                sendCommand(command.changeCommand(ObjectUtils.objtoString(AccountGen.generateAccount())));
            
            } catch (IOException e) {
                Log.sendMessage(e.getMessage());

            }

        }
    }

    private void sendCommand(Command command) {
        cmd_tx = new ClientUDP(command.destination(), BWPorts.INFO_RECEIVE.getPort());

        if (cmd_tx.startClient()) {
            cmd_tx.sendMessage(command.command());
            cmd_tx.closeClient();

        }
    }
}
