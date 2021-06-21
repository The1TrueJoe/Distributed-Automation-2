package com.jtelaa.da2.lib.net.client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Network client for sending messages
 * 
 * @author Joseph
 * @since 1.0
 */

public class Client {

    private String log;
    public String getLog() { return log; }
    
    /* Client */

    Socket clientSocket;
    
    OutputStream outputStream;
    DataOutputStream out;

    /**
     * Starts a server client <p>
     * Close when finished
     * 
     * @param server_ip IP Address of the server
     * @param port Port number to connect to @see com.jtelaa.da2.lib.net.Ports
     */

    public void startClient(String server_ip, int port) {
        log = "";
        
        try {
            clientSocket = new Socket(server_ip, port);
            log += "Connected to " + server_ip + ":" + port + "\n";
        
            outputStream = clientSocket.getOutputStream();
            out = new DataOutputStream(outputStream);
            log += "Ready";

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();

        }

    
    }

    /**
     * Sends a message to the pre-configured server
     * 
     * @param message The message to send
     */

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
            log += "Sending: " + message + " to: " + clientSocket + "\n";

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();

        }
    }
    
    /**
     * Closes the client and output streams
     */

    public void closeClient() {
        try {    
            out.close();
            clientSocket.close();
            log += "Closed";

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();

        }
    }

    public Socket getSocket() { return clientSocket; }
    public OutputStream getOutputStream() { return outputStream; }
    public DataOutputStream getDataOutputStream() { return out; }

}
