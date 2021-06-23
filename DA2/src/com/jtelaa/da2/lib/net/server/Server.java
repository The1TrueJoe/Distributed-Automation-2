package com.jtelaa.da2.lib.net.server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server client for recieving messages
 * 
 * @author Joseph
 * @since 1.0
 */

public class Server {

    private String log;
    public String getLog() { return log; }

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

    public void startServer() {
        log = "";

        try {
            server = new ServerSocket(port);
            log += "Awaiting Connection....\n";
            
            serverSocket = server.accept();
            clientAddress = serverSocket.getInetAddress();
            log += serverSocket + " Connected! to " + clientAddress.getHostAddress() + "\n";

            inputStream = serverSocket.getInputStream();
            in = new DataInputStream(inputStream);

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();

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
            log += "Received: " + message;
            
        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();
            message = "";

        }

        return message;
    }

    /**
     * Closes the client and output streams
     */

    public void closeServer() {
        try {    
            serverSocket.close();
            server.close();
            in.close();
            log += "Closed";

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();

        }
    }

    public String getClientAddress() { return clientAddress.getHostAddress(); }
    public String getClientName() { return clientAddress.getHostName(); }

    public Socket getSocket() { return serverSocket; }
    public ServerSocket getServerSocket() { return server; }
    public InputStream getInputStream() { return inputStream; }
    public DataInputStream getDataInputStream() { return in; }
    
}
