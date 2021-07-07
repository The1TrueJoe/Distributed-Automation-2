package com.jtelaa.da2.bw_manager.processes;

import com.jtelaa.da2.bot.plugin.bw.util.BWPorts;
import com.jtelaa.da2.director.botmgmt.Bot;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.server.ServerUDP;

/**
 * This process accepts the requests for info.
 * It enques them to the info response server if the request is valid.
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.da2.bot.plugin.bw.util.BWMessages
 * @see com.jtelaa.da2.bw_manager.processes.InfoResponseClient
 */

 // TODO comment

public class InfoRequestServer extends Thread {

    public void run() {
        ServerUDP server = new ServerUDP(BWPorts.INFO_REQUEST.getPort());
        
        if (server.startServer()) {
            while (run) {
                String response = server.getMessage();

                if (MiscUtil.notBlank(response)) {
                    InfoResponseClient.addRequest(response, new Bot(server.getClientAddress()));
                }
            }
        }
    }

    private boolean run = true;
    public synchronized void stopServer() { run = false; }
    
}