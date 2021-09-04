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
