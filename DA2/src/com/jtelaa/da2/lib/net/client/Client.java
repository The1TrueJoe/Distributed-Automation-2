package com.jtelaa.da2.lib.net.client;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.NetTools;

/**
 * Network client for sending messages
 * 
 * @since 1.0
 * @author Joseph
 */

 // TODO comment

public class Client {
    
    /* Client */

    private String server_ip;
    private int port;

    private Socket clientSocket;
    
    private OutputStream outputStream;
    private DataOutputStream out;
    private ObjectOutputStream out_obj;

    public Client(String server_ip, int port) { 
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
     * Starts a server client <p>
     * Close when finished
     * 
     * @param server_ip IP Address of the server
     * @param port Port number to connect to @see com.jtelaa.da2.lib.net.Ports
     */

    public boolean startClient() {
        try {
            clientSocket = new Socket(server_ip, port);
            Log.sendMessage("Connected to " + server_ip + ":" + port + "\n");
        
            outputStream = clientSocket.getOutputStream();
            out = new DataOutputStream(outputStream);
            out_obj = new ObjectOutputStream(outputStream);
            Log.sendMessage("Ready");

            return true;

        } catch (Exception e) {
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
            Log.sendMessage("Sending: " + message + " to: " + server_ip + ":" + port + "\n");
            out.writeUTF(message);
            out.flush();
            Log.sendMessage("Done");

            return true;

        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;
        }
    }

    /**
     * Sends object
     * 
     * @param object Object to send
     */

    public boolean sendObject(Serializable object) {
        try {
            Log.sendMessage("Sending Object: " + object + " to: " + server_ip + ":" + port + "\n");
            out_obj.writeObject(object);
            out_obj.flush();
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
            out.close();
            out_obj.close();
            clientSocket.close();
            Log.sendMessage("Closed");

            return true;

        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;

        }
    }

    public int getPort() { return port; }
    public String getServerIP() { return server_ip; }

    public Socket getSocket() { return clientSocket; }
    public OutputStream getOutputStream() { return outputStream; }
    public DataOutputStream getDataOutputStream() { return out; }
    public ObjectOutputStream getObjectOutputStream() { return out_obj; }

}
