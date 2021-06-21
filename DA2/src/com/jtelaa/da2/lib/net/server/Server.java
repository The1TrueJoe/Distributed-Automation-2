package com.jtelaa.da2.lib.net.server;

import java.io.DataInputStream;
import java.io.InputStream;
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

    Socket serverSocket;
    ServerSocket server;

    InputStream inputStream;
    DataInputStream in;

    /**
     * Starts the server <p>
     * Close when finished
     * 
     * @param port The port to open
     */

    public void startServer(int port) {
        log = "";

        try {
            server = new ServerSocket(port);
            log += "Awaiting Connection....\n";
            serverSocket = server.accept();
            log += serverSocket + " Connected!\n";

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

    public Socket getSocket() { return serverSocket; }
    public ServerSocket getServerSocket() { return server; }
    public InputStream getInputStream() { return inputStream; }
    public DataInputStream getDataInputStream() { return in; }
    
}
