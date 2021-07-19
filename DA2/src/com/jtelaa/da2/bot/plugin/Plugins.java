package com.jtelaa.da2.bot.plugin;

import java.io.File;
import java.util.ArrayList;

import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.files.FileUtil;

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
        ArrayList<String> lines = FileUtil.listLinesFile(path);
        for (String line : lines) { plugins.add(new Plugin(line)); }

    }

    /**
     * Imports plugins from a file
     * 
     * @param file File object
     */

    public static void importPlugins(File file) {
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

/**
 * Simple object for plugin managment
 */

class Plugin {

    /** Plugin path */
    private String path;

    /** Plugin path */
    private String name;

    /**
     * Constructor for plugin object
     */

    public Plugin() { path = ""; }

    /**
     * Constructor for plugin object
     * 
     * @param path Plugin path
     */

    public Plugin(String path) { this.path = path; }

    /**
     * Constructor for plugin object
     * 
     * @param path Plugin path
     * @param name Plugin name
     */

    public Plugin(String path, String name) { this.path = path; }

    /** 
     * Change the plugin name 
     * 
     * @param name Name to change 
     */

    public void changeName(String name) { this.name = name; }

    /** @return plugin path */
    public String getPath() { return path; }

    /** @return plugin path */
    public String getName() { return name; }

    /** @return full command */
    public String toString() { return "java -jar " + path; }

    /** @return full command */
    public Command getCommand() { return new Command(this.toString()); }

    /** Runs command to start the plugin */
    public String start() { return ComputerControl.sendCommand(new Command(this.toString())); }

    /** 
     * Checks if the plugins are equal
     * 
     * @param plugin Plugin to compare to
     */

    public boolean equals(Plugin plugin) { return this.getPath().equalsIgnoreCase(plugin.getPath()); }
    
}
