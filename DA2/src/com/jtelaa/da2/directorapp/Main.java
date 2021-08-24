package com.jtelaa.da2.directorapp;

import java.util.Scanner;

import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.Client;
import com.jtelaa.da2.lib.net.ports.SysPorts;
import com.jtelaa.da2.lib.net.server.Server;
import com.jtelaa.da2.directorapp.gui.GUI;

/**
 * This program acts as an easy remote tool to access the director
 * 
 * @since 2
 * @author Joseph
 */

 // TDOD comment
 // TDOD update to current 

public class Main {

    public static Client director_tx;
    public static Server director_rx;

    public static void main(String[] args) {
        // Prints the console banner
        ConsoleBanners.mainBanner();

        // Prints a welcome messagee
        System.out.println(
            "Welcome to the DA2 Bot-Net Director Client! Version 1.0\n" +
            "This tool allows you to interface with any device on the system.\n"+
            "After the login and connection process, type gui to access the GUI version of this client.\n"+
            "NOTE: As of current, please type your commands whole.\n\n"
        );      

        Scanner keyboard = new Scanner(System.in);
        String server_ip = "", keyboard_response;

        while (!NetTools.isValid(server_ip)) {
            System.out.println("Enter IP address of the Director Server:");
            server_ip = keyboard.next();

        }

        System.out.println("Are you using the default CMD port of " + SysPorts.CMD.getPort() + "(y or n)");
        keyboard_response = keyboard.next();
        int CMD_Port = SysPorts.CMD.getPort();

        if (keyboard_response.equalsIgnoreCase("n") || keyboard_response.equalsIgnoreCase("no")) {
            do {
                System.out.println("Enter new port: ");
                keyboard_response = keyboard.next();

            } while (!MiscUtil.isNumeric(keyboard_response));
        }

        System.out.println("Are you using the default Response port of " + SysPorts.CMD.getPort() + "(y or n)");
        keyboard_response = keyboard.next();
        int RESPONSE_Port = SysPorts.RESPONSE.getPort();

        if (keyboard_response.equalsIgnoreCase("n") || keyboard_response.equalsIgnoreCase("no")) {
            do {
                System.out.println("Enter new port: ");
                keyboard_response = keyboard.next();

            } while (!MiscUtil.isNumeric(keyboard_response));
        }

        director_tx = new Client(server_ip, CMD_Port);
        director_tx.startClient();

        director_rx = new Server(RESPONSE_Port);
        director_rx.startServer();


        while(keyboard_response.compareToIgnoreCase("exit") != 0) {
            System.out.println(director_rx.getMessage());
            keyboard_response = keyboard.next();

            if (keyboard_response.compareToIgnoreCase("exit") != 0) { 
                director_tx.closeClient();
                director_rx.closeServer();
                
                break;
            }

            if (keyboard_response.compareToIgnoreCase("gui") != 0) { 
                GUI.beginGUI();
                
                break;
            }

            director_tx.sendMessage(keyboard_response);
        }

        keyboard.close();

    }
    
}