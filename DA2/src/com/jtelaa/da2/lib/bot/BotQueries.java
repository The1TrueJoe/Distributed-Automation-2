package com.jtelaa.da2.lib.bot;

import java.util.ArrayList;

import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.sql.DA2SQLQueries;
import com.jtelaa.da2.lib.sql.EmptySQLURLException;
import com.jtelaa.da2.lib.sql.SQL;

/**
 * Utilities class to query the central database for bot info
 */

public class BotQueries extends DA2SQLQueries {

    /** Database name */
    private static volatile String database = "DA2";
    /** Table name */
    private static volatile String table_name = "BOT";
    /** ID column name */
    private static volatile String id_type = "ID";
    /** Franchise Column */
    private static volatile String franchise_column = "FranchiseID";

    /** Log prefix */
    //private static volatile String sql_log_prefix = "BotSQL";

    /**
     * Get the id based on current ip
     * 
     * @return id
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static int getID(String ip, int franchise) throws EmptySQLURLException {
        return DA2SQLQueries.getID(database, table_name, id_type, "LastIP", ip, franchise_column, franchise);

    }

    /**
     * Basic query for a column given an id
     * @param ID ID to use
     * @param column column get data from
     * 
     * @return Data in the column
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String queryByID(int ID, String column) throws EmptySQLURLException {
        return DA2SQLQueries.queryByID(database, table_name, id_type, ID, column);
    }

     /**
     * Get the app verbosity
     * 
     * @param ID id
     * 
     * @return verbosity
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean appVerbose(int ID) throws EmptySQLURLException {
        return appVerbose(database, table_name, id_type, ID);

    }

     /**
     * Get the log verbosity
     * 
     * @param ID id
     * 
     * @return verbosity
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean logVerbose(int ID) throws EmptySQLURLException {
        return logVerbose(database, table_name, id_type, ID);

    }

    /**
     * Get the last known ip address
     * 
     * @param ID id of the bot
     * 
     * @return lsat known ip
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String getLastIP(int ID) throws EmptySQLURLException {
        return queryByID(ID, "LastIP");

    }

    /**
     * Get the assoicated hypervisor id
     * 
     * @param ID id of the bot
     * 
     * @return associated hypervisor
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static int getHypervisorID(int ID) throws EmptySQLURLException {
        return Integer.parseInt(queryByID(ID, "Hypervisor"));

    }

    /**
     * Get vm id on the hypervisor
     * 
     * @param ID id of the bot
     * 
     * @return associated hypervisor
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static int getHypVMID(int ID) throws EmptySQLURLException {
        return Integer.parseInt(queryByID(ID, "HypVMID"));

    }

    /**
     * Get the assoicated franchise id
     * 
     * @param ID id of the bot
     * 
     * @return associated franchise
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static int getFranchiseID(int ID) throws EmptySQLURLException {
        return Integer.parseInt(queryByID(ID, "FranchiseID"));

    }

    /**
     * Check if local cli is enabled
     * 
     * @param ID id
     * 
     * @return enabled?
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean localCLI(int ID) throws EmptySQLURLException {
        return localCLI(database, table_name, id_type, ID);

    }

     /**
     * Check if remote cli is enabled
     * 
     * @param ID id
     * 
     * @return enabled?
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean remoteCLI(int ID) throws EmptySQLURLException {
        return remoteCLI(database, table_name, id_type, ID);

    }

    /**
     * Check logger server ip
     * 
     * @param ID id 
     * 
     * @return logging server ip
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String loggerIP(int ID) throws EmptySQLURLException {
        return queryByID(database, table_name, id_type, ID, "LoggerIP");

    }

    /**
     * Check logger server ip
     * 
     * @param ID id 
     * 
     * @return logging server ip
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String directorIP(int ID) throws EmptySQLURLException {
        return queryByID(database, table_name, id_type, ID, "DirectorIP");

    }

    /**
     * Check database mirror url (Can also be primary database)
     * 
     * @param ID id 
     * 
     * @return logging server ip
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String mirrordatabaseURL(int ID) throws EmptySQLURLException {
        return queryByID(database, table_name, id_type, ID, "MirrorDatabase");

    }

    /**
     * Check the last known activity timestamp
     * 
     * @param ID id
     * 
     * @return timestamp
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static long lastknownactivity(int ID) throws EmptySQLURLException {
        try {
            return Long.parseLong(queryByID(ID, "LastRunTime"));

        } catch (NumberFormatException e) {
            Log.sendMessage("Attempt to parse last known activity time failed");
            return System.currentTimeMillis();

        }

    }

    public synchronized static String getPlugins(int ID) {
        ArrayList<String> plugins = SQL.query(
            connectionURL,
            "USE " + database + "; " +
            "SELECT PluginPath " +
            "FROM PLUGINS " +
            "WHERE BotID == '" + ID + "';"

        );

        String pluginlist = "";

        for (String plugin : plugins) {
            pluginlist += plugin + ",";
        }

        return pluginlist.substring(0, pluginlist.length()-2);
        
    }

    /**
     * Update last known alive time
     * 
     * @param ID Id of bot to update
     */

    public synchronized static void updateLastKnownTime(int ID) {
        SQL.query(connectionURL, 
            "USE " + database + "; " +
            "UPDATE " + table_name + " " +
            "SET LastRunTime = " + System.currentTimeMillis() + " " +
            "WHERE " + id_type + " = " + ID + ";"
        
        );

    }

    /**
     * Update franchise
     * 
     * @param ID Id of bot to update
     * @param franchise Franchise to update
     */

    public synchronized static void setFranchise(int ID, int franchise) {
        SQL.query(connectionURL, 
            "USE " + database + "; " +
            "UPDATE " + table_name + " " +
            "SET FranchiseID = " + franchise + " " +
            "WHERE " + id_type + " = " + ID + ";"
        
        );

    }

    /**
     * Updates the ip address of the bot
     * 
     * @param ID ID of the bot
     * @param franchise Franchise number of the hot
     * @param new_ip New ip address
     */

    public synchronized static void updateIP(int ID, int franchise, String new_ip) {
        SQL.query(connectionURL,
            "USE " + database + "; " +
            "UPDATE " + table_name + " " +
            "SET LastIP = " + new_ip + " " +
            "WHERE " + 
                id_type + " = " + ID + " " +
                "AND " +
                franchise_column + " = " + franchise + 
            ";"
        );
    }

    
}
