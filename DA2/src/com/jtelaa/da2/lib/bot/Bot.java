package com.jtelaa.da2.lib.bot;

import java.io.Serializable;

import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.net.NetTools;

/**
 * Bot object for storing data
 * 
 * @since 1
 * @author Joseph
 */

 // TODO comment

public class Bot implements Serializable {

    /** Time last seen */
    public long last_seen;

    /** Bot id */
    private int id;

    /** Bot ip */
    private String ip;

    /** Bot ip */
    private String owner_phone_number;

    /** Config handler */
    private ConfigHandler config;

    /**
     * Constructor
     * 
     * @param ip Bot ip
     */

    public Bot(String ip) {
        this.ip = ip;

    }

    /**
     * Constructor
     * 
     * @param config Config
     */

    public Bot(ConfigHandler config) {
        this.config = config;
        ip = config.getIP();
        id = config.getID();
        owner_phone_number = config.getProperty("phone-number", "704-351-7396");

    }

    /** @param id Id to set */
    public void setID(int id) { this.id = id; }

    /** @return bot ip */
    public String getIP() { return ip; }

    /** @return bot id */
    public int getID() { return id; }

    /** @return get owner number */
    public String getOwnerNumber() { return owner_phone_number; }

    /** @return bot config */
    public ConfigHandler getConfig() { return config; }

    /** @return if the bot has a heartbeat */
    public boolean hasHeartBeat() { return config.getProperty("has_heartbeat", "true").equalsIgnoreCase("true"); }

    /** @return ip address of the heartbeat */
    public String getHearbeatIP() { return config.getProperty("has_heartbeat", " "); }

    /** @return if the bot can be pinged */
    public boolean isReachable() { return NetTools.isAlive(ip); }

}
