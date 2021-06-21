package com.jtelaa.da2.directorapp;

import java.util.Scanner;

import org.apache.commons.validator.routines.InetAddressValidator;

import com.jtelaa.da2.lib.net.Ports;
import com.jtelaa.da2.lib.misc.Misc;
import com.jtelaa.da2.lib.net.client.Client;
import com.jtelaa.da2.lib.net.server.Server;
import com.jtelaa.da2.directorapp.gui.GUI;

public class Main {

    public static Client director_tx;
    public static Server director_rx;

    public static void main(String[] args) {
        System.out.println(
            "Welcome to the DA2 Bot-Net Director Client! Version 1.0\n" +
            "This tool allows you to interface with any device on the system.\n"+
            "After the login and connection process, type gui to access the GUI version of this client.\n"+
            "NOTE: As of current, please type your commands whole.\n\n"
        );      

        Scanner keyboard = new Scanner(System.in());
        String server_ip = "", command, keyboard_response;

        while (!InetAddressValidator.isValidInet4Address(server_ip)) {
            System.out.println("Enter IP address of the Director Server:");
            server_ip = keyboard.next();

        }

        System.out.println("Are you using the default CMD port of " + Ports.CMD.getPort() + "(y or n)");
        keyboard_response = keyboard.next();
        int CMD_Port;

        if (keyboard_response.equalsIgnoreCase("n") || keyboard_response.equalsIgnoreCase("no")) {
            do {
                System.out.println("Enter new port: ");
                keyboard_response = keyboard.next();

            } while (!Misc.isNumeric(keyboard_response));

        } else {
            CMD_Port = Ports.CMD.getPort();

        } 

        System.out.println("Are you using the default Response port of " + Ports.CMD.getPort() + "(y or n)");
        keyboard_response = keyboard.next();
        int RESPONSE_Port;

        if (keyboard_response.equalsIgnoreCase("n") || keyboard_response.equalsIgnoreCase("no")) {
            do {
                System.out.println("Enter new port: ");
                keyboard_response = keyboard.next();

            } while (!Misc.isNumeric(keyboard_response));

        } else {
            RESPONSE_Port = Ports.RESPONSE.getPort();

        } 

        director_tx = new Client(server_ip, CMD_Port);
        director_tx.startClient();

        director_rx = new Server(RESPONSE_Port);
        director_rx.startServer();


        while(keyboard_response.compareToIgnoreCase("exit")) {
            System.out.println(director_rx.getMessage());
            keyboard_response = keyboard.next();

            if (keyboard_response.compareToIgnoreCase("exit")) { 
                director_tx.closeClient();
                director_rx.closeServer();
                break;

            }

            if (keyboard_response.compareToIgnoreCase("gui")) { 
                director_tx.closeClient();
                director_rx.closeServer();
                
                GUI.beginGUI();
                
                break;

            }

            director_tx.sendMessage(keyboard_response);
        }

    }
    
}