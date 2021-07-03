package com.jtelaa.da2.director.botmgmt;

import java.io.Serializable;

import com.jtelaa.da2.lib.config.ConfigHandler;

/**
 * Bot object for storing data
 * 
 * @since 1
 * @author Joseph
 */

 // TODO comment

public class Bot implements Serializable {

    private int id;
    private String ip;

    private ConfigHandler config;

    public Bot(String ip) {
        this.config = new ConfigHandler();
        ip = config.getIP();
        id = config.getID();

    }

    public Bot(String ip, int id) {
        this.config = new ConfigHandler();

        this.ip = ip;
        config.setIP(ip);

        this.id = id;
        config.setID(id);

    }

    public Bot(ConfigHandler config) {
        this.config = config;
        ip = config.getIP();
        id = config.getID();

    }

    public String getIP() { return ip; }
    public int getID() { return id; }

    public ConfigHandler getConfig() { return config; }

    public boolean hasHeartBeat() { return config.getProperty("has_heartbeat", "true").equalsIgnoreCase("true"); }
    public String getHearbeatIP() { return config.getProperty("has_heartbeat", " "); }
    
    public String getQueryGenIP() { return config.getProperty("qgen_ip", ""); }
    public String getPointMgrIP() { return config.getProperty("ptmgr_ip", ""); }

}
