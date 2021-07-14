package com.jtelaa.da2.lib.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @deprecated
 */

 // TODO comment

public class OneTimeConnection {
    
    public static String log;

    /* One-Time Connections */

    /**
     * 
     * @param message
     * @param server_ip
     * @param port
     */

    public static void sendSingle(String message, String server_ip, int port) {
        log = "";
        
        try {
            Socket socket = new Socket(server_ip, port);
            log += "Connected to " + server_ip + ":" + port + "\n";
        
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outputStream);

            out.writeUTF(message);
            out.flush();
            log += "Sending: " + message + " to: " + server_ip + ":" + port + "\n";
        
            out.close();
            socket.close();
            log += "Done";

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();

        }
    }

    /**
     * 
     * @param port
     * @return
     */

    public static String receiveSingle(int port) {
        log = "";
        String message = "";

        try {
            ServerSocket server = new ServerSocket(port);
            log += "Awaiting Connection....\n";
            Socket socket = server.accept();
            log += socket + " Connected!\n";

            InputStream inputStream = socket.getInputStream();
            DataInputStream in = new DataInputStream(inputStream);

            message = in.readUTF();
            log += "Received: " + message;
        
            server.close();
            socket.close();
            in.close();
            log += "Closing";

        } catch (Exception e) {
            log += "Failed: \n" + e.getStackTrace();
            message = "";

        }

        return message;
    }
}
