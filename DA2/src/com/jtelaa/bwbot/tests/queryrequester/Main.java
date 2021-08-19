package com.jtelaa.bwbot.tests.queryrequester;

import com.jtelaa.bwbot.bwlib.BWMessages;
import com.jtelaa.bwbot.bwlib.BWPorts;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.server.ServerUDP;

public class Main {

    public static void main(String[] args) {
        Log.app_verbose = true;
        Log.log_verbose = false;

        ServerUDP server = new ServerUDP(BWPorts.QUERY_RECEIVE);
        ClientUDP client = new ClientUDP("server_ip", BWPorts.QUERY_REQUEST);

        server.startServer();
        client.startClient();

        for (int i = 0; i < 100000; i++) {
            client.sendMessage(BWMessages.QUERY_REQUEST_MESSAGE);

            System.out.println(server.getMessage());

        }

    }
    
}
