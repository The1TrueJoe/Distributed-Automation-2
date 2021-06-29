package com.jtelaa.da2.test;

import com.jtelaa.da2.lib.net.server.ServerUDP;

public class UDPServerTest {

    public static void main(String[] args) {
        ServerUDP server = new ServerUDP(9999);
        String message = "";

        server.startServer();

        while (true) {
            if (message != null) {
                System.out.println(server.getMessage());
            }
        }
    }
    
}
