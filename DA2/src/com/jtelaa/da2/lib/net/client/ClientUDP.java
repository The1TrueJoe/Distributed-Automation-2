package com.jtelaa.da2.lib.net.client;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.jtelaa.da2.lib.control.Messages;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.net.ports.Ports;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

/**
 * Network client for sending UDP messages
 * 
 * @since 2
 * @author Joseph
 */

public class ClientUDP {

    /* Client */

    /** Prefix to add to log messages (optional) */
    private String log_prefix;

    /** Server ip */
    private String server_ip;
    /** Server port */
    private int port;

    /** Buffer */
    byte buffer[];

    /** Socket */
    private DatagramSocket socket;

    /**
     * Constructor
     * 
     * @param server_ip IP to contact
     * @param port Port to contact
     * 
     * @deprecated juse use the Ports object instead (ManualPort for a specefied port)
     * @see com.jtelaa.da2.lib.net.ports.Ports
     * @see com.jtelaa.da2.lib.net.ports.ManualPort
     */

    @Deprecated
    public ClientUDP(String server_ip, int port) { 
        this(server_ip, port, "");
        
    }

    /**
     * Constructor
     * 
     * @param server_ip IP to contact
     * @param port Port to contact
     */

    public ClientUDP(String server_ip, Ports port) { 
        this(server_ip, port.getPort(), "");

    }

    /**
     * Constructor
     * 
     * @param server_ip IP to contact
     * @param port Port to contact
     * @param logPrefix Prefix to add to log messages
     */

    public ClientUDP(String server_ip, Ports port, String logPrefix) {
        this(server_ip, port.getPort(), logPrefix);

    }

    /**
     * Constructor
     * 
     * @param server_ip IP to contact
     * @param port Port to contact
     * @param logPrefix Prefix to add to log messages
     * 
     * @deprecated juse use the Ports object instead (ManualPort for a specefied port)
     * @see com.jtelaa.da2.lib.net.ports.Ports
     * @see com.jtelaa.da2.lib.net.ports.ManualPort
     */

    @Deprecated
    public ClientUDP(String server_ip, int port, String logPrefix) { 
        // Log prefix
        this.log_prefix = logPrefix;

        // If the server ip is invalid
        if (NetTools.isValid(server_ip)) {     
            this.server_ip = server_ip; 
            this.port = port;                      
            
        } else {
            // Use local ip if the server is invalid
            Log.sendMessage(log_prefix + server_ip + " is invalid");
            server_ip = NetTools.getLocalIP();

        }
    }

    /**
     * Starts a UDP server client <p>
     * Close when finished
     * 
     * @param server_ip IP Address of the server
     * @param port Port number to connect to 
     * 
     * @see com.jtelaa.da2.lib.net.Port
     * 
     * @return Successs
     */

    public boolean startClient() {
        try {
            // Start
            Log.sendMessage(log_prefix + "Starting server at port " + port);
            
            // Reset buffer and create new socker
            buffer = null;
            socket = new DatagramSocket();

            // Send success message
            Log.sendMessage(log_prefix + "Client open. Will send messages to " + server_ip + ":" + port);
            return true;

        } catch (IOException e) {
            // Send error message
            Log.sendMessage(log_prefix, e);
            return false;

        }
    }

    /**
     * Sends a message to the pre-configured server
     * 
     * @param message The message to send
     * 
     * @return Success
     */

    public boolean sendMessage(String message) {
        try {
            // Log
            Log.sendMessage(log_prefix + "Sending UDP: " + message + " to: " + server_ip + ":" + port + "\n");

            // Set buffer and send
            buffer = message.getBytes();
            socket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(server_ip), port));

            // Send success
            Log.sendMessage(log_prefix + "Done");
            return true;
        
        } catch (Exception e) {
            // Send error
            Log.sendMessage(log_prefix, e);
            return false;

        }
    }

    /**
     * Send a system message to the server
     * 
     * @param message Message to send (Get from enumerated messages)
     * 
     * @return Success
     */

    public boolean sendMessage(Messages message) {
        return sendMessage(message.getMessage());

    }

    /**
     * Sends a object
     * 
     * @param object Object to send
     * 
     * @return Success
     */

    public boolean sendObject(Serializable object) {
        try {
            // Send log message
            Log.sendMessage(log_prefix + "Sending UDP Object: " + object + " to: " + server_ip + ":" + port + "\n");

            // Set buffer with serialized object and send
            buffer = SerializationUtils.serialize(object);
            socket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(server_ip), port));

            // Send success
            Log.sendMessage(log_prefix + "Done");
            return true;
        
        } catch (Exception e) {
            // end error
            Log.sendMessage(log_prefix + "Failed: \n" + e.getStackTrace());
            return false;

        }

    }

    /**
     * Closes the client and output streams
     * 
     * @return Success
     */

    public boolean closeClient() {
        try {    
            // Close
            socket.close();

            // Send success
            Log.sendMessage(log_prefix + "Closed");
            return true;

        } catch (Exception e) {
            // Send error
            Log.sendMessage(log_prefix, e);
            return false;

        }
    }

    
    /**
     * Test by sending packets
     */

    @Test
    public static void test() {
        ClientUDP client = new ClientUDP("127.0.0.1", 9999);
        int i = 0;

        client.startClient();
        
        while (true) {
            client.sendMessage("Test " + i);
            i++;
        }
        
    }

    /** @return port used by client */
    public int getPort() { return port; }
    /** @return server ip used by client*/
    public String getServerIP() { return server_ip; }

    /** @return socket for use outside of class */
    public DatagramSocket getSocket() { return socket; }
    
}
