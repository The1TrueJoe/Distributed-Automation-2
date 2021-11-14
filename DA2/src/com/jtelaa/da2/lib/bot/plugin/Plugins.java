package com.jtelaa.da2.lib.bot.plugin;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
     * Imports plugins
     * 
     * @param plugins List of plugins
     */

    public static void importPlugins(ArrayList<Plugin> plugins) {
        Plugins.plugins = plugins;

    }

    /**
     * Imports plugins
     * 
     * @param plugins List of plugins in CSV
     * 
     * @throws FileNotFoundException
     */

    public static ArrayList<Plugin> importPlugins(String plugin_list) throws FileNotFoundException {
        ArrayList<Plugin> new_plugins = new ArrayList<Plugin>();
        String[] plugins = plugin_list.split(".");

        for (String plugin : plugins) {
            new_plugins.add(Plugin.importPlugin(plugin));

        }

        return new_plugins;
         
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
    
    public String toString() {
        String out = "";

        for (Plugin plugin : plugins) {
            out += plugin + ",";

        }

        return out.substring(0, out.length()-2);
    }
    
}