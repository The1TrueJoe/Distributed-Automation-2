package com.jtelaa.da2.lib.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.jtelaa.da2.lib.log.Log;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

// TODO comment

public class ServerUDP {

    private int port;

    private byte[] recieve_buffer;
    
    private InetAddress clientAddress;

    private DatagramSocket socket;

    public ServerUDP(int port) { this.port = port; }

    
    /**
     * Starts the server <p>
     * Close when finished
     */

    public boolean startServer() {
        try {
            Log.sendMessage("Starting Server....\n");
            socket = new DatagramSocket(port);

            return true;

        } catch (SocketException e) {
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
        recieve_buffer = new byte[65535];
        String message;
        
        try {
            DatagramPacket packet = new DatagramPacket(recieve_buffer, recieve_buffer.length);
            socket.receive(packet);
            message = convertMessage(recieve_buffer);
            clientAddress = socket.getInetAddress();

            Log.sendMessage("Received: " + message + " From: " + getClientAddress());

        } catch (IOException e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());
            message = "";

        }

         return message;
    }

    /**
     * Receives the object from a client
     * 
     * @return The object from the client
     */

    public Object getObject() {
        recieve_buffer = new byte[65535];
        Object object;
        
        try {
            DatagramPacket packet = new DatagramPacket(recieve_buffer, recieve_buffer.length);
            socket.receive(packet);
            object = SerializationUtils.deserialize(recieve_buffer);
            clientAddress = socket.getInetAddress();

            Log.sendMessage("Received UDP Object: " + object + " From: " + getClientAddress());

        } catch (IOException e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());
            object = new String("");

        }

         return object;
    }

    /**
     * Converts the message from a byte array into a string
     * 
     * @param bytes Byte array received from the datagram
     * @return Message as a string
     */

    private String convertMessage(byte[] bytes) {
        if (bytes == null) { return null; }

        StringBuilder string = new StringBuilder();
        int index = 0;

        while (bytes[index] != 0) {
            string.append((char) bytes[index]);
            index++;

        }

        return string.toString();

    }

    /**
     * Closes the client and output streams
     */

    public boolean closeServer() {
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
        ServerUDP server = new ServerUDP(9999);
        String message = "";

        server.startServer();

        while (true) {
            if (message != null) {
                System.out.println(server.getMessage());
            }
        }
    }

    public int getPort() { return port; }

    public DatagramSocket getSocket() { return socket; }

    public InetAddress getClient() { return clientAddress; }
    public String getClientAddress() { return clientAddress.getHostAddress(); }
    public String getClientName() { return clientAddress.getHostName(); }
    
}
