package com.jtelaa.da2.test;

import com.jtelaa.da2.lib.net.client.ClientUDP;

public class UDPClientTest {

    public static void main(String[] args) {
        ClientUDP client = new ClientUDP("127.0.0.1", 9999);
        int i = 0;

        client.startClient();
        
        while (true) {
            client.sendMessage("Test " + i);
            i++;
        }
        
    }
    
}
