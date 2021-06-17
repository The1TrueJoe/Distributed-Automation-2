package com.jtelaa.da2.director.util.cli;

import java.util.Scanner;

public class BaseCLI {
    private static Scanner keyboard;

    public static void startCLI() {
        startKeyboard();
        String in = "";

        if (in.equalsIgnoreCase("help") || in.equalsIgnoreCase("-h") || in.equalsIgnoreCase("-?")) {
            //printHelp();
        }
    }

    private static void startKeyboard() {
        keyboard = new Scanner(System.in);
    }

    private static void closeKeyboard() {
        keyboard.close();
    }
}
