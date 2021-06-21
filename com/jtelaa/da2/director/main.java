package com.jtelaa.da2.director;

public class main {
    public static void main(String[] args) {
        System.out.println("Welcome to Distributed Automation 2");

        if (args[0].toUpper().contains("CLI")) {
            BaseCLI.startCLI(BaseCLI.startKeyboard);
        }
    }

    private static Scanner startKeyboard() {
        return new Scanner(System.in);
    }

    private static void closeKeyboard() {
        keyboard.close();
    }
}