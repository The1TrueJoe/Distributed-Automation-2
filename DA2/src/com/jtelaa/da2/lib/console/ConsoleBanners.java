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

    public static String mainBanner(String color) { return color + Files.readInternalFile(BANNER_PATH + "MainBanner.txt") + ConsoleColors.RESET; }
}