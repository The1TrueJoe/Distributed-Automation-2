package com.jtelaa.da2.lib.net.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import com.jtelaa.da2.lib.log.Log;

public class ServerUDP {

    private int port;

    private byte[] recieve_buffer;

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

            Log.sendMessage("Received: " + message);

        } catch (IOException e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());
            message = "";

        }

         return message;
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

    public int getPort() { return port; }

    public DatagramSocket getSocket() { return socket; }
    
}
