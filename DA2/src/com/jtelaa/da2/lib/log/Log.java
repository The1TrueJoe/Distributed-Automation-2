package com.jtelaa.da2.lib.log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.net.ports.Ports;
import com.jtelaa.da2.lib.net.ports.SysPorts;

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

    /** Log Queue */
    public volatile static Queue<String> logging_queue;

    /** Log history */
    public static volatile ArrayList<String> history;

    /** Log Sender Process */
    private volatile static LogSender sender;

    /** Local log connection */
    private volatile static LocalLogConnector connector;

    /**
     * Load the config from the configuration file <p>
     * <b> Make sure to load config file first!! </b>
     * 
     * @param config Config handler
     */

    public synchronized static void loadConfig(Properties config) {
        app_verbose = config.getProperty("app_verbose", "true").equalsIgnoreCase("true");
        log_verbose = config.getProperty("log_verbose", "true").equalsIgnoreCase("true");

    }

    /**
     * Load the config from the configuration file <p>
     * <b> Make sure to load config file first!! </b> </p>
     * 
     * <p> 
     * "no local": No local output,
     * "no remote": No remote output,
     * "headless": No output
     * </p>
     * 
     * @param config Handler
     * @param args System args
     */

    public synchronized static void loadConfig(Properties config, String[] args) {
        // Load config
        loadConfig(config);

        // Args trump config
        for (String arg : args) {
            if (arg.contains("no local")) {
                app_verbose = false;

            } else if (arg.contains("no remote")) {
                log_verbose = false;

            } else if (arg.contains("headless")) {
                app_verbose = false;
                log_verbose = false;

            }
        }

    }

    /**
     * Clear history
     */

    public synchronized static void clearHistory() {
        // Clear history
        history = new ArrayList<String>();

    }

    /**
     * Opens the logging connector so admine can interface with the application if it running in the background
     */

    public synchronized static void openConnector() {
        // Start the connector
        connector = new LocalLogConnector();
        connector.start();

    }

    /**
     * Opens the logging client to send messages to servers
     * 
     * @param logging_port IP of server
     */

    public synchronized static void openClient(String logging_server_ip) {
        openClient(logging_server_ip, SysPorts.LOG);

    }

    /**
     * Opens the logging client to send messages to servers
     * 
     * @param logging_server_ip IP of server
     * @param logging_port Port to use
     */

    public synchronized static void openClient(String logging_server_ip, Ports logging_port) {
        // Stop if log is not verbose
        if (!log_verbose) { 
            sendSysMessage("Local Logging Only");
            return; 

        } 

        // Create the queue
        logging_queue = new LinkedList<String>();
        
        // Start the log sender
        sender = new LogSender(logging_server_ip, logging_port);
        sender.start();

    }

    /**
     * Adds a log message to the history (Max lines: 5000)
     * 
     * @param message Message to add
     */

    public synchronized static void addToHistory(String message) {
        if (history.size() > 5000) { history.remove(0); } 
        history.add(message);

    }

    /**
     * Sends message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param message Message to send
     */

    public synchronized static void sendMessage(String message) {
        if (app_verbose) { sendManSysMessage(message); }   // System message
        if (log_verbose) { sendManLogMessage(message); }   // Logging message

    }

    /**
     * Sends message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param message Message to send
     * @param color Color to use
     */

    public synchronized static void sendMessage(String message, ConsoleColors color) {
        if (app_verbose) { sendManSysMessage(message, color); }   // System message
        if (log_verbose) { sendManLogMessage(message, color); }   // Logging message

    }

    /**
     * Sends exception's message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param log_prefix String to add to begining of exception message
     * @param e Exception to send
     */

    public synchronized static void sendMessage(String log_prefix, Exception e) {
        sendMessage(log_prefix + ":\n" + e.getMessage());

    }

     /**
     * Sends exception's message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param log_prefix String to add to begining of exception message
     * @param e Exception to send
     * @param color Color to use
     */

    public synchronized static void sendMessage(String log_prefix, Exception e, ConsoleColors color) {
        sendMessage(log_prefix + ":\n" + e.getMessage(), color);

    }

    /**
     * Sends exception's message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param e Exception to send
     */

    public synchronized static void sendMessage(Exception e) {
        sendMessage(e.getMessage());

    }

    /**
     * Sends exception's message to the log and system console <p>
     * This checks the verbosity of both
     * 
     * @param e Exception to send
     * @param color Color to use
     */

    public synchronized static void sendMessage(Exception e, ConsoleColors color) {
        sendMessage(e.getMessage(), color);

    }

    /**
     * Sends system message no matter what the verbosity is
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendManSysMessage(String message) {
        addToHistory(message);
        System.out.println(message);

        return true;

    }

    /**
     * Sends system message no matter what the verbosity is
     * 
     * @param message Message to send
     * @param color Color to use
     */

    public synchronized static boolean sendManSysMessage(String message, ConsoleColors color) {
        try {
            message = color.getEscape() + message + ConsoleColors.RESET.getEscape();

        } catch (NullPointerException e) {
            message = message + " - (Color could not get displayed)";

        }
        
        
        addToHistory(message);
        System.out.println(message);

        return true;

    }

    /**
     * Sends system message depending on verbosity
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendSysMessage(String message) {
        if (app_verbose) { addToHistory(message); System.out.println(message); }
        return app_verbose;

    }

    /**
     * Sends system message depending on verbosity
     * 
     * @param message Message to send
     * @param color Color to use
     */

    public synchronized static boolean sendSysMessage(String message, ConsoleColors color) {
        if (app_verbose) { 
            message = color.getColor() + message + ConsoleColors.RESET.getColor();
        
            addToHistory(message);
            System.out.println(message);
        
        }

        return app_verbose;

    }

    /**
     * Sends system message depending on verbosity
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendSysMessageNoNewLine(String message) {
        if (app_verbose) { addToHistory(message); System.out.print(message); }
        return app_verbose;

    }

    /**
     * Sends system message depending on verbosity
     * 
     * @param message Message to send
     * @param color Color to use
     */

    public synchronized static boolean sendSysMessageNoNewLine(String message, ConsoleColors color) {
        if (app_verbose) { 
            message = (color.getColor() + message + ConsoleColors.RESET.getColor()); 

            addToHistory(message);
            System.out.print(message);
        
        }

        return app_verbose;

    }

    /**
     * Sends logging message no matter what the verbosity is <b>
     * Will not attempt to send unless the log is established <b>
     * This adds the message to the queue
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendManLogMessage(String message) {
        if (sender.log_established || sender.isAlive()) { 
            logging_queue.add(message); 
            return true; 
        
        }
        
        return false;

    }

    /**
     * Sends logging message no matter what the verbosity is <b>
     * Will not attempt to send unless the log is established <b>
     * This adds the message to the queue
     * 
     * @param message Message to send
     * @param color Color to use
     */

    public synchronized static boolean sendManLogMessage(String message, ConsoleColors color) {
        if (sender.log_established || sender.isAlive()) { 
            logging_queue.add(color.getColor() + message + ConsoleColors.RESET.getColor()); 
            return true; 
        
        }
        
        return false;

    }

    /**
     * Sends logging message depending on verbosity <b>
     * Will not attempt to send unless the log is established <b>
     * This adds the message to the queue
     * 
     * @param message Message to send
     */

    public synchronized static boolean sendLogMessage(String message) {
        if (log_verbose) { sendManLogMessage(message); }
        return log_verbose;

    }

    /**
     * Sends logging message depending on verbosity <b>
     * Will not attempt to send unless the log is established <b>
     * This adds the message to the queue
     * 
     * @param message Message to send
     * @param color Color to use
     */

    public synchronized static boolean sendLogMessage(String message, ConsoleColors color) {
        if (log_verbose) { sendManLogMessage(color.getColor() + message + ConsoleColors.RESET.getColor()); }
        return log_verbose;

    }

    /** Closes the logging client */

    public synchronized static void closeLog() {
        try {
            if (sender.log_established) { 
                sender.stopSender();

            }

            if (connector.log_established) {
                connector.stopServer();
                
            }

        } catch (NullPointerException e) {
            return;

        }
        
    }

} 