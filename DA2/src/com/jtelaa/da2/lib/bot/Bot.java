package com.jtelaa.da2.lib.bot;

import java.io.Serializable;
import java.util.Properties;

import com.jtelaa.da2.lib.net.NetTools;
//import com.jtelaa.da2.lib.sql.SQL;

/**
 * simple Bot object for storing data
 * 
 * @since 1
 * @author Joseph
 */

 // TODO Implement other info
 // TODO add database connection

public class Bot implements Serializable {

    /** Time last seen */
    public long last_seen;

    /** Bot id */
    public int id;

    /** Bot ip */
    public String ip;

    /** Bot ip */
    public String owner_phone_number;

    /** Config handler */
    public Properties config;

    /**
     * Constructor
     */

    public Bot() { }

    /**
     * Constructor
     */

    public Bot(String ip) { this.ip = ip; }

    /**
     * Constructor
     * 
     * @param Bot bot to load
     */

    public Bot(Bot bot) { 
        this.config = bot.config;
        this.id = bot.id;
        this.ip = bot.ip;
        this.owner_phone_number = bot.owner_phone_number;

    }

    /**
     * Load bot from config file
     * 
     * @param config Config
     */

    public static synchronized Bot load(Properties config) {
        Bot bot = new Bot();

        bot.ip = config.getProperty("ip", NetTools.getLocalIP());
        bot.id = Integer.parseInt(config.getProperty("id", 0+""));
        bot.owner_phone_number = config.getProperty("phone-number", "704-351-7396");

        return bot;

    }

    public static synchronized Bot load(String database_ip) {
        //String url = SQL.getConnectionURL(url, database, user, pass)

        //String unformatted_query = 
        //Map<String, String> replace = 
        //String query = SQL.replace(queunformatted_queryry, replace);

        // TODO Setup query requests

        return new Bot();

    }

    /** See if the bot responds to pings */
    public boolean isReachable() { return NetTools.isAlive(ip); }

}
