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

    private String ip_address;

    private boolean app_verbose;
    private boolean log_verbose;

    private String director_ip;
    private String query_ip;
    private String heartbeat_ip;
    private String log_ip;

    /**  TODO Add Constructors */
    public Bot() {
        
    }
    
    public Bot(JSONObject bot_config) {
        
    }
    

    public JSONObject botParser() { return null; }

    /** Checks config if the app will output to the local terminal */
    public boolean runAppVerbose() { return app_verbose; }

    /** Checks config if the app will output to the log */
    public boolean runLogVerbose() { return log_verbose; }

    /** Gets director IP from config */
    public String getDirectorIP() { return director_ip; }

    /** Gets director IP from config */
    public String getQueryGenIP() { return query_ip; }

    public int getID() { return 0; }

    public String getIP() { return ip_address; }

    public String getHearbeatIP() { return heartbeat_ip; }

    public String getLogIP() { return log_ip; }
    
}
