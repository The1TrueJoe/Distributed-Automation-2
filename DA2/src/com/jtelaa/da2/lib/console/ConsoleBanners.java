package com.jtelaa.da2.lib.console;

import com.jtelaa.da2.lib.files.Files;

/**
 * Prints out the banners into the console
 * 
 * @since 1
 * @author Joseph
 */

public class ConsoleBanners {

    private static final String BANNER_PATH = "com/jtelaa/da2/lib/console/banners/";

    /**
     * Prints the main banner
     * 
     * @return The banner
     */

    public static String mainBanner() { return Files.readInternalFile(BANNER_PATH + "MainBanner.txt"); }

    /**
     * Prints the main banner
     * 
     * @param color @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * @return The banner
     */

    public static String mainBanner(String color) { return color + mainBanner() + ConsoleColors.RESET; }

    /**
     * Prints the director banner
     * 
     * @return The banner
     */

    public static String directorBanner() { return Files.readInternalFile(BANNER_PATH + "Director.txt"); }

    /**
     * Prints the director banner
     * 
     * @param color @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * @return The banner
     */

    public static String directorBanner(String color) { return color + directorBanner() + ConsoleColors.RESET; }

    /**
     * Prints the bot banner
     * 
     * @return The banner
     */

    public static String botBanner() { return Files.readInternalFile(BANNER_PATH + "Bot.txt"); }

    /**
     * Prints the bot banner
     * 
     * @param color @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * @return The banner
     */

    public static String botBanner(String color) { return color + mainBanner() + ConsoleColors.RESET; }

    /**
     * Prints the hypervisor banner
     * 
     * @return The banner
     */

    public static String hypervisorBanner() { return Files.readInternalFile(BANNER_PATH + "Hypervisor.txt"); }

    /**
     * Prints the hypervisor banner
     * 
     * @param color @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * @return The banner
     */

    public static String hypervisorBanner(String color) { return color + hypervisorBanner() + ConsoleColors.RESET; }


}