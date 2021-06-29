package com.jtelaa.da2.lib.log;

import java.util.LinkedList;
import java.util.Queue;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.ClientUDP;

/**
 * Logging capability
 * 
 * @since 2
 * @author Joseph
 */

public class Log {

    /** The vebosity of the local application output */
    public volatile static boolean app_verbose;

    /** The logging verbostiy */
    public volatile static boolean log_verbose;

    public volatile static ClientUDP logging_client;
    public volatile static boolean log_established = false;

    public volatile static Queue<String> logging_queue;
    private volatile static LogSender sender;

    /**
     * Load the config from the configuration file <p>
     * <b> Make sure to load config file first!! </b>
     */

    public synchronized static void loadConfig() {
        app_verbose = ConfigHandler.runAppVerbose();
        log_verbose = ConfigHandler.runLogVerbose();

    }

    public synchronized static void openClient(String logging_server_ip) {
        if (!log_verbose) { return; } // Stop if log is not verbose

        // Start logging client
        logging_client = new ClientUDP(logging_server_ip, Ports.LOG.getPort());
        log_established = logging_client.startClient();

        // Create the queue
        logging_queue = new LinkedList<String>();
        
        // Start the log sender
        sender = new LogSender();
        sender.start();
    }

    /**
     * Sends message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param message Message to send
     */

    public synchronized static void sendMessage(String message) {
        if (app_verbose) { sendSysMessage(message); }   // System message
        if (log_verbose) { sendLogMessage(message); }   // Logging message

    }

    /**
     * Sends system message no matter what the verbosity is
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendSysMessage(String message) {
        System.out.println(message);
        return true;

    }

    /**
     * Sends logging message no matter what the verbosity is <b>
     * Will not attempt to send unless the log is established <b>
     * This adds the message to the queue
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendLogMessage(String message) {
        if (!log_established || !sender.isAlive()) { return false; }
        logging_queue.add(message);
        return true;

    }

    /** Closes the logging client */

    public synchronized static void closeLog() { 
        logging_client.closeClient(); 
        sender.stopSender();
    
    }

} 