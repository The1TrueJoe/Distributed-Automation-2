package com.jtelaa.da2.lib.log;

import com.jtelaa.da2.lib.config.Config;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.Client;

/**
 * Logging capability
 * 
 * @since 2
 * @author Joseph
 */

public class Log {

    /** The vebosity of the local application output */
    public static boolean app_verbose;

    /** The logging verbostiy */
    public static boolean log_verbose;

    private static Client logging_client;
    private static boolean log_established;

    /**
     * Load the config from the configuration file <p>
     * <b> Make sure to load config file first!! </b>
     */

    public static void loadConfig() {
        app_verbose = Config.runAppVerbose();
        log_verbose = Config.runLogVerbose();

    }

    public static void openClient(String logging_server_ip) {
        logging_client = new Client(logging_server_ip, Ports.LOG.getPort());
        log_established = logging_client.startClient();
    }

    /**
     * Sends message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param message Message to send
     */

    public static void sendMessage(String message) {
        if (app_verbose) { sendSysMessage(message); }   // System message
        if (log_verbose) { sendLogMessage(message); }   // Logging message

    }

    /**
     * Sends system message no matter what the verbosity is
     * 
     * @param message Message to send
     */

    public static boolean sendSysMessage(String message) {
        System.out.println(message);
        return true;

    }

    /**
     * Sends logging message no matter what the verbosity is <b>
     * Will not attempt to send unless the log is established
     * 
     * @param message Message to send
     */

    public static boolean sendLogMessage(String message) {
        if (!log_established) { return false; }
        message = Config.getID() + ">" + message;
        return logging_client.sendMessage(message);

    }

    /** Closes the logging client */

    public static void closeLog() { logging_client.closeClient(); }

}
