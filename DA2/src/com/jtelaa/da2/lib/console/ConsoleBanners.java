package com.jtelaa.da2.lib.console;

import com.jtelaa.da2.lib.files.FileUtil;

/**
 * Prints out the banners into the console
 * 
 * @since 1
 * @author Joseph
 */

public class ConsoleBanners {

    private static final String BANNER_PATH = "com/jtelaa/da2/lib/console/banners/";

    /**
     * Test prints banners
     */
    public static void main(String[] args) {
        System.out.println(mainBanner(ConsoleColors.YELLOW_BOLD_BRIGHT));
        System.out.println(directorBanner(ConsoleColors.GREEN_BOLD_BRIGHT));
    }

    /**
     * Prints the main banner
     * 
     * @return The banner
     */

    public static String mainBanner() { return FileUtil.readInternalFile(BANNER_PATH + "MainBanner.txt"); }

    /**
     * Prints the main banner
     * 
     * @param color 
     * 
     * @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * 
     * @return The banner
     */

    public static String mainBanner(ConsoleColors color) { return color.getColor() + mainBanner() + ConsoleColors.RESET.getEscape(); }

    /**
     * Prints the director banner
     * 
     * @return The banner
     */

    public static String directorBanner() { return FileUtil.readInternalFile(BANNER_PATH + "Director.txt"); }

    /**
     * Prints the director banner
     * 
     * @param color 
     * 
     * @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * 
     * @return The banner
     */

    public static String directorBanner(ConsoleColors color) { return color.getColor() + directorBanner() + ConsoleColors.RESET.getEscape(); }

    /**
     * Prints the bot banner
     * 
     * @return The banner
     */

    public static String botBanner() { return FileUtil.readInternalFile(BANNER_PATH + "Bot.txt"); }

    /**
     * Prints the bot banner
     * 
     * @param color 
     * 
     * @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * 
     * @return The banner
     */

    public static String botBanner(ConsoleColors color) { return color.getColor() + mainBanner() + ConsoleColors.RESET.getEscape(); }

    /**
     * Prints the hypervisor banner
     * 
     * @return The banner
     */

    public static String hypervisorBanner() { return FileUtil.readInternalFile(BANNER_PATH + "Hypervisor.txt"); }

    /**
     * Prints the hypervisor banner
     * 
     * @param color 
     * 
     * @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * 
     * @return The banner
     */

    public static String hypervisorBanner(ConsoleColors color) { return color.getColor() + hypervisorBanner() + ConsoleColors.RESET.getEscape(); }

    /**
     * Prints the hypervisor banner
     * 
     * @return The banner
     */

    public static String otherBanner(String path) { return FileUtil.readInternalFile(path); }

    /**
     * Prints the hypervisor banner
     * 
     * @param color 
     * 
     * @see com.jtelaa.da2.lib.console.ConsoleColors.java
     * 
     * @return The banner
     */

    public static String otherBanner(String path, ConsoleColors color) { return color.getColor() + otherBanner(path) + ConsoleColors.RESET.getEscape(); }


}