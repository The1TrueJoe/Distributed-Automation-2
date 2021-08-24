package com.jtelaa.da2.bot.plugin;

import java.io.File;
import java.util.ArrayList;

import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.log.Log;

/**
 * Plugin managment
 * 
 * @author Joseph
 * @since 2
 * 
 */

public class Plugins {

    /** List of plugins */
    private static ArrayList<Plugin> plugins;

    /**
     * Imports plugins from a file
     * 
     * @param path File path
     */

    public static void importPlugins(String path) {
        importPlugins(new File(path));

    }

    /**
     * Imports plugins from a file
     * 
     * @param file File object
     */

    public static void importPlugins(File file) {
        if (!file.exists()) { 
            Log.sendMessage("Plugin File Does Not Exist");
            return;

        }

        ArrayList<String> lines = FileUtil.listLinesFile(file);
        for (String line : lines) { plugins.add(new Plugin(line)); }
        
    }

    /**
     * Adds the plugin into the list
     * 
     * @param plugin Plugin to add
     * 
     * @return the plugin added
     */

    public static Plugin add(Plugin plugin) { plugins.add(plugin); return plugin; }

    /**
     * Starts a plugin at an index
     * 
     * @param index
     */

    public static void start(int index) { 
        plugins.get(index).start();

    }

    /**
     * Starts the plugin with the name
     * 
     * @param name Name of plugin to start
     */

    public static void start(String name) {
        find(name).start();
    }

    /**
     * Starts all plugins
     */

    public static void startAll() {
        if (plugins.size() == 0) {
            Log.sendMessage("No Plugins Detected");
            return;

        }

        for (Plugin plugin : plugins) {
            plugin.start();

        }
    }

    /**
     * Find a plugin in the list
     * 
     * @param name Name of plugin
     * 
     * @return The plugin
     */

    public static Plugin find(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.getName().equalsIgnoreCase(name)) {
                return plugin;
            }
        }

        return new Plugin();

    }
    
    
}