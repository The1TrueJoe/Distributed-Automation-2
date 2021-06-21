package com.jtelaa.da2.director.util.cli;

import java.util.Scanner;

import com.jtelaa.da2.director.util.cli.cliutil.Misc;
import com.jtelaa.da2.director.util.cli.cliutil.DirecCLI;
import com.jtelaa.da2.director.util.cli.cliutil.HypervisCLI;
import com.jtelaa.da2.director.util.cli.cliutil.SchedCLI;



/**
 *  Basic CLI for the program
 *  @author Joe
 */
public class BaseCLI() {
    Scanner keyboard;

    public static void startCLI(Scanner keyboard, OutputStream out, String path) {
        this.keyboard = keyboard;
        
        startKeyboard();

        System.out.println(path + " ");
        String in = keyboard.nextLine().toLower();

        if (in.equalsIgnoreCase("help") || in.equalsIgnoreCase("-h") || in.equalsIgnoreCase("-?")) {
            Misc.simpleHelp();
        } else if (in.equalsIgnoreCase("director") || in.equalsIgnoreCase("direc")) {
            DirecCLI.startCLI(keyboard, out, path)
        } 

    }

    
}
