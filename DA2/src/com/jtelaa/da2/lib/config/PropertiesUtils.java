package com.jtelaa.da2.lib.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.log.Log;

/**
 * Utilities class to import/export a properties file
 * 
 * @since 2
 * @version 2
 * @author Joseph
 */

public class PropertiesUtils {

    /**
     * Imports the properties file
     * 
     * @param path File path
     * 
     * @return properties
     */

    public synchronized static Properties importConfig(String path) {
        return importConfig(new File(path));

    }

    /**
     * Imports the properties file
     * 
     * @param file File object
     * 
     * @return properties
     */

    public synchronized static Properties importConfig(File file) {
        Properties config = new Properties();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
        
            config.load(fileInputStream);

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }

        return config;
    }

    /**
     * Checks if the key is true
     * 
     * @param config Properties file to check
     * @param key key to check
     * @param def defualt value
     * 
     * @return if the key returns true
     */

    public synchronized static boolean isTrue(Properties config, String key, boolean def) { return config.getProperty(key, def+"").equalsIgnoreCase("true"); }

     /**
     * Checks if the key is true (Default: true)
     * 
     * @param config Properties file to check
     * @param key key to check
     * 
     * @return if the key returns true
     */

    public synchronized static boolean isTrue(Properties config, String key) { return config.getProperty(key, true+"").equalsIgnoreCase("true"); }

    /**
     * Get the key as an integer
     * 
     * @param config properties file
     * @param key key
     * 
     * @return key result
     */

    public synchronized static int getKey(Properties config, String key) { return Integer.parseInt(config.getProperty(key)); }

    /**
     * Get the key as an integer
     * 
     * @param config properties file
     * @param key key
     * @param default default value
     * 
     * @return key result
     */

    public synchronized static int getKey(Properties config, String key, int defualt) { return Integer.parseInt(config.getProperty(key, defualt+"")); }

    /**
     * Imports the internal properties file & creates a temp file
     * 
     * @param path File path
     * 
     * @return properties
     */

    public synchronized static Properties importInternalConfig(String path) {
        Properties config = new Properties();

        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File file = FileUtil.duplicateInternalFile(classLoader, path, "config.properties");
            FileInputStream fileInputStream = new FileInputStream(file);

            config.load(fileInputStream);

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }

        return config;
    }

    /**
     * Exports the properties file
     * 
     * @param path File path
     * @param config Properties to export
     */

    public synchronized static void exportConfig(String path, Properties config) {
        try {
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            config.store(fileOutputStream, "No Comments");

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());

        }
    }

    /**
     * List the properties in the file
     * 
     * @param properties Properties file
     * 
     * @return List of properties
     */

    public synchronized static ArrayList<String> listProperties(Properties properties) {
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Properties");

        Enumeration<Object> keys = properties.keys();

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = (String) properties.get(key);

            lines.add(key + ": " + value);
        }

        return lines;
    }
}
