package com.jtelaa.da2.director.util.cli;

import java.util.Scanner;

import com.jtelaa.da2.director.util.cli.cliutil.Misc;
import com.jtelaa.da2.director.util.guI.GUI;

public class BaseCLI {
    private static Scanner keyboard;

    public static void startCLI(String path) {
        startKeyboard();

        System.out.println(path + " ");
        String in = keyboard.nextLine();

        if (in.equalsIgnoreCase("help") || in.equalsIgnoreCase("-h") || in.equalsIgnoreCase("-?")) {
            Misc.simpleHelp();
        } else if (in.equalsIgnoreCase("gui")) {
            GUI.beginGUI();
        }


        closeKeyboard();
    }

    private static void startKeyboard() {
        keyboard = new Scanner(System.in);
    }

    private static void closeKeyboard() {
        keyboard.close();
    }
}
