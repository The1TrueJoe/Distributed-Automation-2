package com.jtelaa.da2.director.util.cli;

import java.io.DataOutputStream;

import com.jtelaa.da2.director.util.cli.clis.DirecCLI;
import com.jtelaa.da2.director.util.cli.clis.Misc;

/**
 *  Basic CLI for the program
 *  @author Joe
 */

public class CLI {

    public static void startCLI(String command, DataOutputStream out) {
        command = command.split("/")[1];

        if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("-h") ||command.equalsIgnoreCase("-?")) {
            Misc.simpleHelp(command, out);

        } else if (command.equalsIgnoreCase("director") || command.equalsIgnoreCase("direc")) {
            DirecCLI.startCLI(command, out);
        } 

    }

    
}
