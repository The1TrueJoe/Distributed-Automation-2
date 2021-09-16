package com.jtelaa.da2.lib.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.ports.Ports;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;


/**
 * Network server for receiving UDP messages
 * 
 * @since 2
 * @author Joseph
 */

public class ServerUDP {

    /** Console Color */
    private ConsoleColors colors;

    /** Prefix to add to log messages (optional) */
    public String log_prefix;

    /** Port opened by the server */
    private int port;

    /** Receive buffer */
    private byte[] recieve_buffer;
    
    /** Address of client */
    private InetAddress clientAddress;

    /** Socket used */
    private DatagramSocket socket;

    /** 
     * Constructor
     * 
     * @param port Port to open
     * 
     * @deprecated juse use the Ports object instead (ManualPort for a specefied port)
     * @see com.jtelaa.da2.lib.net.ports.Ports
     * @see com.jtelaa.da2.lib.net.ports.ManualPort
     */

    @Deprecated
    public ServerUDP(int port) { 
        log_prefix = "";
        this.port = port; 
        colors = ConsoleColors.WHITE;

    }

    /** 
     * Constructor
     * 
     * @param port Port to open
     */
    
    public ServerUDP(Ports port) { 
        log_prefix = "";
        this.port = port.getPort(); 
        colors = ConsoleColors.WHITE;
    
    }

    /** 
     * Constructor
     * 
     * @param port Port to open
     * 
     * @deprecated juse use the Ports object instead (ManualPort for a specefied port)
     * @see com.jtelaa.da2.lib.net.ports.Ports
     * @see com.jtelaa.da2.lib.net.ports.ManualPort
     */

    @Deprecated
    public ServerUDP(int port, String log_prefix) { 
        this.port = port; 
        this.log_prefix = log_prefix;
        colors = ConsoleColors.WHITE;
    
    }

    /** 
     * Constructor
     * 
     * @param port Port to open
     */
    
    public ServerUDP(Ports port, String log_prefix) { 
        this.port = port.getPort(); 
        this.log_prefix = log_prefix;
        colors = ConsoleColors.WHITE;
    
    }

    /** 
     * Constructor
     * 
     * @param port Port to open
     * 
     * @deprecated juse use the Ports object instead (ManualPort for a specefied port)
     * @see com.jtelaa.da2.lib.net.ports.Ports
     * @see com.jtelaa.da2.lib.net.ports.ManualPort
     */

    @Deprecated
    public ServerUDP(int port, String log_prefix, ConsoleColors colors) { 
        this.port = port; 
        this.log_prefix = log_prefix;
        this.colors = colors;
    
    }

    /** 
     * Constructor
     * 
     * @param port Port to open
     */
    
    public ServerUDP(Ports port, String log_prefix, ConsoleColors colors) { 
        this.port = port.getPort(); 
        this.log_prefix = log_prefix;
        this.colors = colors;
    
    }
    
    /**
     * Starts the server <p>
     * Close when finished
     * 
     * @return success
     */

    public boolean startServer() {
        try {
            // Start
            Log.sendMessage(log_prefix + "Starting server at port " + port, colors);

            // Reset buffer and create new socker
            recieve_buffer = null;
            socket = new DatagramSocket(port);

            // Send success message
            Log.sendMessage(log_prefix + "Server Opened", colors);
            return true;

        } catch (IOException e) {
            // Send error message
            Log.sendMessage(log_prefix, e, ConsoleColors.RED);
            return false;

        }
    }

    /**
     * Receives the message from a client
     * 
     * @return The message from the client
     */

    public String getMessage() {
        // New receive buffer and message
        recieve_buffer = new byte[65535];
        String message;
        
        try {
            // Receive packet
            DatagramPacket packet = new DatagramPacket(recieve_buffer, recieve_buffer.length);
            socket.receive(packet);
            message = convertMessage(recieve_buffer);

            if (message == null) {
                Log.sendMessage(log_prefix + "Received null message");
                return null;

            }

            // Store client
            clientAddress = socket.getInetAddress();

            try {
                // Log reception
                Log.sendSysMessage(log_prefix +"Received: " + message + " From: " + getClientAddress(), colors);

            } catch (NullPointerException e) {
                // Log reception
                Log.sendSysMessage(log_prefix +"Received: " + message, colors);

            }

        } catch (NullPointerException e) {
            // Send error
            //Log.sendSysMessage(e.getMessage());
            message = "null message";
        
        } catch (IOException e) {
            // Send error
            // TODO Add support for sysmessages exception pass through
            Log.sendSysMessage(log_prefix + "\n" + e.getMessage(), ConsoleColors.RED);
            message = "";

        }

        // Return
        return message;
    }

    /**
     * Receives the object from a client
     * 
     * @return The object from the client
     */

    public Object getObject() {
        // New receive buffer and message
        recieve_buffer = new byte[65535];
        Object object;
        
        try {
            // Receive packet
            DatagramPacket packet = new DatagramPacket(recieve_buffer, recieve_buffer.length);
            socket.receive(packet);
            object = SerializationUtils.deserialize(recieve_buffer);

            try {
                // Store client
                clientAddress = socket.getInetAddress();

            } catch (NullPointerException e) {
                Log.sendMessage("Cannot resolve the client's address", ConsoleColors.RED);

            }
            
            // Log reception
            Log.sendMessage(log_prefix + "Received UDP Object: " + object + " From: " + getClientAddress(), colors);

        } catch (IOException e) {
            // Send error
            Log.sendMessage(log_prefix, e, ConsoleColors.RED);
            object = new String("");

        }

        // Return
        return object;
    }

    /**
     * Converts the message from a byte array into a string
     * 
     * @param bytes Byte array received from the datagram
     * 
     * @return Message as a string
     */

    private String convertMessage(byte[] bytes) {
        // Check if valid array
        if (bytes == null || bytes.length <= 0) { return "null"; }

        // Prepare string building
        StringBuilder string = new StringBuilder();
        int index = 0;

        // Build string
        while (bytes[index] != 0) {
            string.append((char) bytes[index]);
            index++;

        }

        // Return string
        return string.toString();

    }

    /**
     * Closes the client and output streams
     * 
     * @return success
     */

    public boolean closeServer() {
        try {    
            // Close
            socket.close();

            // Send success
            Log.sendMessage(log_prefix + "Closed", colors);
            return true;

        } catch (Exception e) {
            // Send error
            Log.sendMessage(log_prefix, e, ConsoleColors.RED);
            return false;

        }
    }

    /**
     * Test by receiving packets and printing them
     */

    @Test
    public static void test() {
        ServerUDP server = new ServerUDP(9999);
        String message = "";

        server.startServer();

        while (true) {
            if (message != null) {
                System.out.println(server.getMessage());
            }
        }
    }

    /** @return port opened by server */
    public int getPort() { return port; }

    /** @return socket to be used outside of class */
    public DatagramSocket getSocket() { return socket; }

    /** @return client InetAddress object */
    public InetAddress getClient() { return clientAddress; }
    /** @return client IP address */
    public String getClientAddress() { return clientAddress.getHostAddress(); }
    /** @return client hostname */
    public String getClientName() { return clientAddress.getHostName(); }
    
}
