package com.jtelaa.da2.lib.log;

import java.util.ArrayList;

import com.jtelaa.da2.lib.control.SysMessages;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.ServerUDP;

public class LocalLogConnector extends Thread {

    /** Logging client */
    private ClientUDP logging_client;

    /** Is the loggin client established */
    public boolean log_established = false;

    public void run() {
        while (!run) {
            MiscUtil.waitasec();

        }

        while (run) {
            ServerUDP connection_listener = new ServerUDP(SysPorts.ESTABLISH_LOCAL_LOG);
            connection_listener.startServer();
            String message;

            do {
                MiscUtil.waitasec(.1);
                message = connection_listener.getMessage();

            } while (!SysMessages.ESTABLISH_LOCAL_LOG.equals(message));

            logging_client = new ClientUDP("127.0.0.1", SysPorts.LOCAL_LOG, "Local Log Connector: ");
            logging_client.startClient();
            boolean open = true;

            do {
                long time = System.currentTimeMillis();

                ArrayList<String> currentHistory = Log.history;
                Log.history = new ArrayList<String>();
    
                for (int i = 0; i < currentHistory.size(); i++) {
                    logging_client.sendMessage(currentHistory.get(i));
                    currentHistory.remove(i);
    
                }

                if (time - System.currentTimeMillis() >= 1000) {
                    message = connection_listener.getMessage();

                    if (SysMessages.CLOSE_LOCAL_LOG.equals(message)) {
                        open = false;

                    }
                }

            } while (run && open);


        }
    }

    /** Boolean to control the receiver */
    private volatile boolean run = true;

    /** Stops the command receiver */
    public synchronized void stopServer() { 
        logging_client.closeClient();
        run = false; 

    }

    /** Checks if the receier is ready */
    public synchronized boolean senderReady() { return run; }


    
}
