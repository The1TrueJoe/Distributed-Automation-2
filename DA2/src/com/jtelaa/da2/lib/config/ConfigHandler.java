package com.jtelaa.da2.lib.config;

import org.json.JSONObject;

public class ConfigHandler {

    JSONObject config_file;

    public void newConfigFile(String path, String name) {

    }

    public static JSONObject getConfig() { return null; }

    public static boolean runAppVerbose() {
        return false;
    }

    public static boolean runLogVerbose() {
        return false;
    }

    /** Gets director IP from config */
    public static String getDirectorIP() { return null; }

    /** Gets director IP from config */
    public static String getQueryGenIP() { return null; }

}
