package com.jtelaa.da2.director.util.cli;

import java.io.DataOutputStream;

import com.jtelaa.da2.director.util.cli.clis.DirecCLI;
import com.jtelaa.da2.director.util.cli.clis.Misc;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.Client;
import com.jtelaa.da2.lib.net.server.Server;

/**
 *  Basic CLI for the program
 *  @author Joseph
 */

public class CLI extends Thread {

    private Server tx;
    private Client rx;

    public void run() {
        tx = new Server(Ports.CMD.getPort());
        rx = new Client(tx.getClientAddress(), Ports.RESPONSE.getPort());
    }

    public static void startCLI(String command, DataOutputStream out) {
        command = command.split("/")[1];

        if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("-h") ||command.equalsIgnoreCase("-?")) {
            Misc.simpleHelp(command, out);

        } else if (command.equalsIgnoreCase("director") || command.equalsIgnoreCase("direc")) {
            DirecCLI.startCLI(command, out);
        } 

    }

    
}
