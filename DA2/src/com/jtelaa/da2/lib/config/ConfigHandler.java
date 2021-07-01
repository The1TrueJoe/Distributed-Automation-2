package com.jtelaa.da2.lib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.NetTools;

/**
 * Stores configuration data for bots and applications
 * 
 * @since 2
 * @author Joseph
 */

public class ConfigHandler {

    private Properties config;

    public ConfigHandler() {
        config = new Properties();

    }

    public ConfigHandler(Properties properties) {
        config = properties;

    }

    public ConfigHandler(String path) {
        config = new Properties();
        importConfig(path);

    }

    public ConfigHandler(File config_file) {
        config = new Properties();
        importConfig(config_file);

    }

    /**
     * Imports the properties file
     * 
     * @param path File path
     */

    public void importConfig(String path) {
        importConfig(new File(path));

    }

    /**
     * Imports the properties file
     * 
     * @param file File object
     */

    public void importConfig(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
        
            config.load(fileInputStream);

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }
    }

    /**
     * Exports the properties file
     * 
     * @param path File path
     */

    public void exportConfig(String path) {
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            config.store(fileOutputStream, "No Comments");

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }
    }


    public Properties get() { return config; }

    // Set
    public void setProperty(String key, String value) { config.setProperty(key, value); }

    // Get
    public String getProperty(String key) { return config.getProperty(key); }
    public String getProperty(String key, String def) { return config.getProperty(key, def); }

    public boolean isTrue(String key) { return config.getProperty(key).equalsIgnoreCase("true"); }
    public boolean isTrue(String key, String def) { return config.getProperty(key, def).equalsIgnoreCase("true"); }


    // Logging Verbosity
    public boolean runAppVerbose() { return getProperty("app_verbose", "true").equalsIgnoreCase("true"); }
    public boolean runLogVerbose() { return getProperty("log_verbose", "false").equalsIgnoreCase("true"); }
    
    public void runAppVerbose(boolean verbose) { setProperty("app_verbose", verbose+""); }
    public void runLogVerbose(boolean verbose) { setProperty("log_verbose", verbose+""); }

    // Information
    public int getID() { try { return Integer.parseInt(getProperty("id", "0")); } catch (Exception e) { return 0; } }
    public void setID(int id) { setProperty("id", id+""); }

    // TODO Add Default IP
    // IPs
    public String getIP() { return getProperty("ip", NetTools.getLocalIP()); }
    public String getExternalIP() { return getProperty("ip_ext", NetTools.getExternalIP()); }
    public String getDirectorIP() { return getProperty("director_ip", ""); }
    public String getLogIP() { return getProperty("log_ip", ""); }

    public void setIP() { setProperty("ip", NetTools.getLocalIP()); }
    public void setIP(String ip) { setProperty("ip", ip); }

}
