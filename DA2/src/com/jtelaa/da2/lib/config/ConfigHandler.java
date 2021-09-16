package com.jtelaa.da2.lib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Properties;

import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.NetTools;

/**
 * Stores configuration data for bots and applications
 * 
 * @since 2
 * @author Joseph
 * 
 * @deprecated No longer needed (Makes things uncessessarily complicated)
 * @see com.jtelaa.da2.lib.config.ConfigHandler
 */

 @Deprecated
public class ConfigHandler implements Serializable {

    public Properties config;

    /**
     * Constructor
     * <p> Creates a new config file
     */

    public ConfigHandler() {
        config = new Properties();

    }

    /**
     * Constructor
     * 
     * @param properties Properties file
     */

    public ConfigHandler(Properties properties) {
        config = properties;

    }

    /**
     * Constructor
     * 
     * @param path Path of properties file
     */

    public ConfigHandler(String path) {
        config = new Properties();
        importConfig(path);

    }

    /**
     * Constructor
     * 
     * @param path Path of properties file
     * @param internal Wether or not the file is internal
     */

    public ConfigHandler(String path, boolean internal) {
        config = new Properties();

        if (internal) {
            importInternalConfig(path);

        } else {
            importConfig(path);

        }
    }

    /**
     * Constructor
     * 
     * @param config_file File object of properties file
     */

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
     * Imports the internal properties file & creates a temp file
     * 
     * @param path File path
     */

    public void importInternalConfig(String path) {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            File file = FileUtil.duplicateInternalFile(classLoader, path, "config.properties");
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

    /**
     * Returns contents of properties file
     */

    public String toString() {
        return config.toString();
        
    }

    /** */
    public Properties get() { return config; }

    // Set
    /** */
    public void setProperty(String key, String value) { config.setProperty(key, value); }

    // Get
    /** */
    public String getProperty(String key) { return config.getProperty(key); }
    /** */
    public String getProperty(String key, String def) { return config.getProperty(key, def); }

    /** */
    public boolean isTrue(String key) { return config.getProperty(key).equalsIgnoreCase("true"); }
    /** */
    public boolean isTrue(String key, boolean def) { return config.getProperty(key, def+"").equalsIgnoreCase("true"); }
    /** */
    public boolean isTrue(String key, String def) { return config.getProperty(key, def).equalsIgnoreCase("true"); }

    /** Run a local cli */
    public boolean runLocalCLI() { return isTrue("local_cli", true); }
    /** Run a local cli */
    public boolean runRemoteCLI() { return isTrue("remote_cli", false); }

    // Logging Verbosity
    /** */
    public boolean runAppVerbose() { return getProperty("app_verbose", "true").equalsIgnoreCase("true"); }
    /** */
    public boolean runLogVerbose() { return getProperty("log_verbose", "false").equalsIgnoreCase("true"); }
    
    /** */
    public void runAppVerbose(boolean verbose) { setProperty("app_verbose", verbose+""); }
    /** */
    public void runLogVerbose(boolean verbose) { setProperty("log_verbose", verbose+""); }

    // Information
    public int getID() { try { return Integer.parseInt(getProperty("id", "0")); } catch (Exception e) { return 0; } }
    public void setID(int id) { setProperty("id", id+""); }

    // TODO Add Default IP
    // IPs
    /** */
    public String getIP() { return getProperty("ip", NetTools.getLocalIP()); }
    /** */
    public String getExternalIP() { return getProperty("ip_ext", NetTools.getExternalIP()); }
    /** */
    public String getDirectorIP() { return getProperty("director_ip", ""); }
    /** */
    public String getLogIP() { return getProperty("log_ip", ""); }

    /** */
    public void setIP() { setProperty("ip", NetTools.getLocalIP()); }
    /** */
    public void setIP(String ip) { setProperty("ip", ip); }

}
