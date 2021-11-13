package com.jtelaa.da2.lib.bot;

import com.jtelaa.da2.lib.sql.DA2SQLQueries;
import com.jtelaa.da2.lib.sql.EmptySQLURLException;

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

    /** Log prefix */
    //private static volatile String sql_log_prefix = "BotSQL";

    /**
     * Get the id based on current ip
     * 
     * @return id
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static int getID() throws EmptySQLURLException {
        return DA2SQLQueries.getID(database, table_name, id_type);

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
     * Check the last known activity timestamp
     * 
     * @param ID id
     * 
     * @return timestamp
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String lastknownactivity(int ID) throws EmptySQLURLException {
        return queryByID(ID, "LastRunTime");

    }

    
}
