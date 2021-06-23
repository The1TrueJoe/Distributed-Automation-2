package com.jtelaa.da2.director.util.cli;

import com.jtelaa.da2.director.util.cli.clis.DirecCLI;
import com.jtelaa.da2.director.util.cli.clis.MiscCLI;
import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.net.client.Client;
import com.jtelaa.da2.lib.net.server.Server;

/**
 *  Basic CLI for the program
 *  @author Joseph
 */

public class CLI extends Thread {

    private static Server direc_rx;
    private static Client direc_tx;

    public void run() {
        direc_rx = new Server(Ports.CMD.getPort());
        direc_tx = new Client(direc_rx.getClientAddress(), Ports.RESPONSE.getPort());

        runCLI();
    }

    private void runCLI() {
        String message = "";

        while (message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("end")) {
            message = direc_rx.getMessage();
            if (message != null) { direc_tx.sendMessage(terminal(message)); }

        }
    }

    public String terminal(String command) {
        command = command.split(" ")[1];

        if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("-h") ||command.equalsIgnoreCase("-?")) {
            return new MiscCLI().startCLI(command);

        } else if (command.equalsIgnoreCase("director") || command.equalsIgnoreCase("direc")) {
            return new DirecCLI().startCLI(command);

        } else if (command.equalsIgnoreCase("hypervisor") || command.equalsIgnoreCase("hyp")) {
            return new DirecCLI().startCLI(command);
            
        } else if (command.equalsIgnoreCase("scheduler") || command.equalsIgnoreCase("sched")) {
            return new DirecCLI().startCLI(command);
            
        } else if (command.equalsIgnoreCase("bot") || command.equalsIgnoreCase("bt")) {
            return new DirecCLI().startCLI(command);
            
        }


        return "";

    }

    /** Accessible CMD Server */

    private static Server cmd_rx;
    private static Client cmd_tx;

    public static boolean openCMDServer() { 
        cmd_rx = new Server(Ports.RESPONSE.getPort()); 
        return cmd_rx.startServer(); 

    }

    public static boolean sendCommand(String command) { return cmd_tx.sendMessage(command); }

    public static String getResponse() { return cmd_rx.getMessage(); }

    public static boolean openCMDClient(String ip_address) { 
        cmd_tx = new Client(ip_address, Ports.CMD.getPort()); 
        return cmd_tx.startClient(); 

    }
    
    public static boolean closeCMDServer() { return cmd_rx.closeServer(); }
    public static boolean closeCMDClient() { return cmd_tx.closeClient(); }
        

       
}
