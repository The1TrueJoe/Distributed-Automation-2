package com.jtelaa.da2.director.botmgmt;

import org.json.JSONObject;

/**
 * Bot object for storing data
 * 
 * @since 1
 * @author Joseph
 */

public class Bot {

    private static int id;

    private static String ip_address;

    public int getID() { return id; }
    
    public String getIP() { return ip_address; }

    public static JSONObject botParser() { 
        return null;
    }
    
}
