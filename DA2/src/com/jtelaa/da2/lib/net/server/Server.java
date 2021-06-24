package com.jtelaa.da2.lib.net.server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.jtelaa.da2.lib.log.Log;

/**
 * Server client for recieving messages
 * 
 * @since 1.0
 * @author Joseph
 */

public class Server {

    /* Server */

    private int port;

    private InetAddress clientAddress;

    private Socket serverSocket;
    private ServerSocket server;

    private InputStream inputStream;
    private DataInputStream in;

    public Server(int port) { this.port = port; }

    /**
     * Starts the server <p>
     * Close when finished
     */

    public boolean startServer() {
        try {
            server = new ServerSocket(port);
            Log.sendMessage("Awaiting Connection....\n");
            
            serverSocket = server.accept();
            clientAddress = serverSocket.getInetAddress();
            Log.sendMessage(serverSocket + " Connected! to " + clientAddress.getHostAddress() + "\n");

            inputStream = serverSocket.getInputStream();
            in = new DataInputStream(inputStream);

            return true;

        } catch (Exception e) {
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
        String message;
        
        try {
            message = in.readUTF();
            Log.sendMessage("Received: " + message);
            
        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());
            message = "";

        }

        return message;
    }

    /**
     * Closes the client and output streams
     */

    public boolean closeServer() {
        try {    
            serverSocket.close();
            server.close();
            in.close();
            Log.sendMessage("Closed");

            return true;

        } catch (Exception e) {
            Log.sendMessage("Failed: \n" + e.getStackTrace());

            return false;

        }
    }

    public int getPort() { return port; }

    public String getClientAddress() { return clientAddress.getHostAddress(); }
    public String getClientName() { return clientAddress.getHostName(); }

    public Socket getSocket() { return serverSocket; }
    public ServerSocket getServerSocket() { return server; }
    public InputStream getInputStream() { return inputStream; }
    public DataInputStream getDataInputStream() { return in; }
    
}
