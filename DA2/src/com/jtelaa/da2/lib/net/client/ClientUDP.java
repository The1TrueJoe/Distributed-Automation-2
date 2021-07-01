package com.jtelaa.da2.lib.net.client;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.NetTools;

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

    private String server_ip;
    private int port;

    byte buffer[];

    private DatagramSocket socket;

    public ClientUDP(String server_ip, int port) { 
        if (!NetTools.isValid(server_ip)) {
            try {
                server_ip = InetAddress.getLocalHost().toString();
            } catch (UnknownHostException e) {
                Log.sendMessage(e.getMessage());
                server_ip = "127.0.0.1";

            }
        }
        
        this.server_ip = server_ip; 
        this.port = port; 
    }

    /**
     * Starts a UDP server client <p>
     * Close when finished
     * 
     * @param server_ip IP Address of the server
     * @param port Port number to connect to @see com.jtelaa.da2.lib.net.Ports
     */

    public boolean startClient() {
        try {
            buffer = null;
            socket = new DatagramSocket();
            Log.sendMessage("Client open. Will send messages to " + server_ip + ":" + port);

            return true;

        } catch (IOException e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;
        }
    }

    /**
     * Sends a message to the pre-configured server
     * 
     * @param message The message to send
     */

    public boolean sendMessage(String message) {
        try {
            Log.sendMessage("Sending UDP: " + message + " to: " + server_ip + ":" + port + "\n");
            buffer = message.getBytes();
            socket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(server_ip), port));
            Log.sendMessage("Done");

            return true;
        
        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;
        }
    }

    /**
     * Sends a object
     * 
     * @param object Object to send
     */

    public boolean sendObject(Serializable object) {
        try {
            Log.sendMessage("Sending UDP Object: " + object + " to: " + server_ip + ":" + port + "\n");
            buffer = SerializationUtils.serialize(object);
            socket.send(new DatagramPacket(buffer, buffer.length, InetAddress.getByName(server_ip), port));
            Log.sendMessage("Done");

            return true;
        
        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;
        }

    }

    /**
     * Closes the client and output streams
     */

    public boolean closeClient() {
        try {    
            socket.close();
            Log.sendMessage("Closed");

            return true;

        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;

        }
    }

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

    public int getPort() { return port; }
    public String getServerIP() { return server_ip; }

    public DatagramSocket getSocket() { return socket; }
    
}
