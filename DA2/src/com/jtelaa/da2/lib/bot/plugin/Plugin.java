package com.jtelaa.da2.lib.bot.plugin;

import java.io.File;
import java.io.FileNotFoundException;

import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;

/**
 * Simple object for plugin managment
 */

public class Plugin {

    /** Plugin path */
    private String path;

    /** Plugin path */
    private String name;

    public synchronized static Plugin importPlugin(String line) throws FileNotFoundException {
        String command = "java -jar ";
        line = line.substring(command.length());

        String name = line.substring(line.lastIndexOf("/"), line.length() - ".jar".length() - 1);
        String path = line.substring(0, line.lastIndexOf("/"));
    
        if (!new File(line).exists()) {
            throw new FileNotFoundException("Cannot find file " + line);
        }

        return new Plugin(path, name);

    }

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
    public String start() { 
        Log.sendMessage("Starting Plugin: " + name);
        return ComputerControl.sendCommand(new Command(this.toString())); 
        
    }

    /** 
     * Checks if the plugins are equal
     * 
     * @param plugin Plugin to compare to
     */

    public boolean equals(Plugin plugin) { return this.getPath().equalsIgnoreCase(plugin.getPath()); }
    
}
