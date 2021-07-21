package com.jtelaa.da2.lib.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.Ports;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;


/**
 * Network server for receiving UDP messages
 * 
 * @since 2
 * @author Joseph
 */

public class ServerUDP {

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
     */

    public ServerUDP(int port) { this.port = port; }

    /** 
     * Constructor
     * 
     * @param port Port to open
     */
    
    public ServerUDP(Ports port) { this.port = port.getPort(); }

    
    /**
     * Starts the server <p>
     * Close when finished
     * 
     * @return success
     */

    public boolean startServer() {
        try {
            // Start
            Log.sendMessage("Starting server at port " + port);

            // Reset buffer and create new socker
            recieve_buffer = null;
            socket = new DatagramSocket();

            // Send success message
            Log.sendMessage("Server Opened");
            return true;

        } catch (IOException e) {
            // Send error message
            Log.sendMessage("Failed: \n" + e.getStackTrace());
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

            // Store client
            clientAddress = socket.getInetAddress();

            // Log reception
            Log.sendMessage("Received: " + message + " From: " + getClientAddress());

        } catch (IOException e) {
            // Send error
            Log.sendMessage("Failed: \n" + e.getStackTrace());
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

            // Store client
            clientAddress = socket.getInetAddress();

            // Log reception
            Log.sendMessage("Received UDP Object: " + object + " From: " + getClientAddress());

        } catch (IOException e) {
            // Send error
            Log.sendMessage("Failed: \n" + e.getStackTrace());
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
            Log.sendMessage("Closed");
            return true;

        } catch (Exception e) {
            // Send error
            Log.sendMessage("Failed: \n" + e.getStackTrace());
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
