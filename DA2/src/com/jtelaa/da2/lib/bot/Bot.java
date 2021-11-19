package com.jtelaa.da2.lib.bot;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

import com.jtelaa.da2.lib.bot.plugin.Plugin;
import com.jtelaa.da2.lib.bot.plugin.Plugins;
import com.jtelaa.da2.lib.config.PropertiesUtils;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.net.NetTools;
import com.jtelaa.da2.lib.sql.EmptySQLURLException;

/**
 * Bot obect based on data pulled from the database
 * 
 * @since 2
 * @author Joseph
 */

 // TODO Implement other info
 // TODO add database connection

public class Bot implements Serializable {

    /** Bot id */
    public int id;
    /** Bot ip */
    public String ip;
    /** Hypervisor ID */
    public int hypervisor_id;
    /** Hypervisor VM ID */
    public int hypervisor_vm_id;
    /** Franchise ID */
    public int franchise_id;
    /** App verbosity */
    public boolean app_verbose;
    /** Log verbosity */
    public boolean log_verbose;
    /** Remote CLI */
    public boolean remote_cli;
    /** Local CLI */
    public boolean local_cli;
    /** Last run time */
    public long last_time_seen;
    /** Logger IP */
    public String logger_IP;
    /** Director IP */
    public String director_IP;
    /** Plugins */
    public ArrayList<Plugin> plugins;
    /** Database URL */
    public String db_url;


    /** Config */
    public Properties config;

    /**
     * Blank Constructor used when manually filling data
     */

    private Bot() { }

    /**
     * Constructor building a simple bot based on id
     */

    public Bot(int id) { this.id = id; }

    /**
     * Constructor building a simple bot based on ip
     */

    public Bot(String ip) { this.ip = ip; }

    /**
     * Constructor to duplicate bot
     * 
     * @param Bot bot to load
     */

    public Bot(Bot bot) { 
        this.config = bot.config;
        
        this.id = bot.id;
        this.ip = bot.ip;
        this.hypervisor_id = bot.hypervisor_id;
        this.hypervisor_vm_id = bot.hypervisor_vm_id;
        this.log_verbose = bot.log_verbose;
        this.app_verbose = bot.app_verbose;
        this.remote_cli = bot.remote_cli;
        this.local_cli = bot.local_cli;
        this.last_time_seen = bot.last_time_seen;
        this.logger_IP = bot.logger_IP;
        this.director_IP = bot.director_IP;
        this.plugins = bot.plugins;
        this.db_url = bot.db_url;

    }

    /**
     * Load bot from config file
     * 
     * @param config Config
     * 
     * @deprecated Now handled through SQl
     */

    public static synchronized Bot loadfromFile(Properties config) {
        Bot bot = new Bot();

        bot.id = PropertiesUtils.getKey(config, "id", -1);
        bot.ip = config.getProperty("ip");
        bot.hypervisor_id = PropertiesUtils.getKey(config, "hypervisor_id", -1);
        bot.franchise_id = PropertiesUtils.getKey(config, "franchise");
        bot.log_verbose = PropertiesUtils.isTrue(config, "log_verbose", true);
        bot.app_verbose = PropertiesUtils.isTrue(config, "app_verbose", false);
        bot.local_cli = PropertiesUtils.isTrue(config, "local_cli", false);
        bot.remote_cli = PropertiesUtils.isTrue(config, "remote_cli", true);
        bot.last_time_seen = Long.parseLong(config.getProperty("last_time_seen"));
        bot.logger_IP = config.getProperty("logger_ip");
        bot.director_IP = config.getProperty("director_ip");
        bot.db_url = config.getProperty("db_url");
        bot.hypervisor_vm_id = PropertiesUtils.getKey(config, "hypervisor_vm_id");

        try {
            bot.plugins = Plugins.importPlugins(config.getProperty("plugins"));

        } catch (FileNotFoundException e) {
            Log.sendMessage(e);
            bot.plugins = new ArrayList<Plugin>();

        }

        return bot;

    }

    /**
     * Exports bot as a properties file
     * 
     * @return bot as properties file
     */

    public Properties exporttoProperties() {
        Properties exp_config = new Properties();

        exp_config.setProperty("id", id+"");
        exp_config.setProperty("ip", ip);
        exp_config.setProperty("hypervisor_id", hypervisor_id+"");
        exp_config.setProperty("franchise", franchise_id+"");
        exp_config.setProperty("app_verbose", app_verbose+"");
        exp_config.setProperty("log_verbose", log_verbose+"");
        exp_config.setProperty("local_cli", local_cli+"");
        exp_config.setProperty("remote_cli", remote_cli+"");
        exp_config.setProperty("last_time_seen", last_time_seen+"");
        exp_config.setProperty("logger_ip", logger_IP);
        exp_config.setProperty("director_ip", director_IP);
        exp_config.setProperty("plugins", plugins.toString());
        exp_config.setProperty("db_url", db_url);
        exp_config.setProperty("hypervisor_vm_id", hypervisor_vm_id+"");

        return exp_config;

    }

    /**
     * Load bot configuration from the database
     * 
     * @param connectionURL Url to connect to
     * @param ip IP of the bot (used to determine id)
     * @param franchise franchise number
     * 
     * @return Bot config
     * 
     * @throws EmptySQLURLException
     */

    public static synchronized Bot loadfromDatabase(String connectionURL, String ip, int franchise) throws EmptySQLURLException {
        BotQueries.connectionURL = connectionURL;
        
        Bot bot = new Bot();

        bot.id = BotQueries.getID(ip, franchise);
        bot.ip = BotQueries.getLastIP(bot.id);
        bot.hypervisor_id = BotQueries.getHypervisorID(bot.id);
        bot.hypervisor_vm_id = BotQueries.getHypVMID(bot.id);
        bot.franchise_id = BotQueries.getFranchiseID(bot.id);
        bot.app_verbose = BotQueries.appVerbose(bot.id);
        bot.local_cli = BotQueries.localCLI(bot.id);
        bot.remote_cli = BotQueries.remoteCLI(bot.id);
        bot.last_time_seen = BotQueries.lastknownactivity(bot.id);
        bot.logger_IP = BotQueries.loggerIP(bot.id);
        bot.director_IP = BotQueries.directorIP(bot.id);
        bot.db_url = BotQueries.mirrordatabaseURL(bot.id);
        
        try {
            bot.plugins = Plugins.importPlugins(BotQueries.getPlugins(bot.id));

        } catch (FileNotFoundException e) {
            Log.sendMessage(e);
            bot.plugins = new ArrayList<Plugin>();

        }

        return bot;

    }

    /**
     * Update the last known run time
     */

    public void updateLastKnownTime() {
        BotQueries.updateLastKnownTime(id);

    }

    /** See if the bot responds to pings */
    public boolean isReachable() { return NetTools.isAlive(ip); }

}
