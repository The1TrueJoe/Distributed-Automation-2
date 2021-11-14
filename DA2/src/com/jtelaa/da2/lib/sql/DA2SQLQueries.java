package com.jtelaa.da2.lib.sql;

import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 * Utilies class containing methods to query the database for server configs
 * 
 * @author Joseph
 * @since 2
 */

public class DA2SQLQueries {

    /** 
     * Connection url for the database
     * Note: get url from com.jtelaa.da2.lib.sql.SQL.getConnectionURL();
     */

    public static volatile String connectionURL;

    /**
     * Check the URL of the database
     * 
     * @throws EmptySQLURLException throws if sql url is empty
     */

    public synchronized static void checkURL() throws EmptySQLURLException {
        if (!MiscUtil.notBlank(connectionURL)) {
            throw new EmptySQLURLException();

        }


    }

    /**
     * 
     * Basic query that gets a column param given an id
     * 
     * @param database Database to connect to
     * @param table_name Name of the table to access
     * @param id_type The column name that contains the id
     * @param id ID number
     * @param column column to check
     * 
     * @return If the app should log to the console
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static String queryByID(String database, String table_name, String id_type, int id, String column) throws EmptySQLURLException {
        checkURL();

        // Get ID
        String query = (
            SQL.query(
                connectionURL,
                "USE " + database + "; " +
                "SELECT " + column + " " +
                "FROM " + table_name + " " +
                "WHERE " + id_type + " == '" + id + "';"

            )
            .get(0)
        );

        return query;

    }

    /**
     * 
     * Checks the app logging verbosity
     * 
     * @param database Database to connect to
     * @param table_name Name of the table to access
     * @param id_type The column name that contains the id
     * @param id ID number
     * 
     * @return If the app should log to the console
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean appVerbose(String database, String table_name, String id_type, int id) throws EmptySQLURLException {
        // Get ID
        int verbosity =  
            Integer.parseInt(
                queryByID(database, table_name, id_type, id, "AppVerbose")
            )
        ;

        return verbosity == 1 ? true : false;

    }

    /**
     * 
     * Checks the app logging verbosity
     * 
     * @param database Database to connect to
     * @param table_name Name of the table to access
     * @param id_type The column name that contains the id
     * @param id ID number
     * 
     * @return If the app should log to the logging derver
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean logVerbose(String database, String table_name, String id_type, int id) throws EmptySQLURLException {
        // Get ID
        int verbosity =  
            Integer.parseInt(
                queryByID(database, table_name, id_type, id, "LogVerbose")
            )
        ;

        return verbosity == 1 ? true : false;

    }


    /**
     * Get the id based on current ip
     * 
     * @param database Database to connect to
     * @param table_name Table to access
     * @param id_type ID column name
     * @param ip_type IP column name
     * @param ip IP address to use
     * 
     * @return id
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static int getID(String database, String table_name, String id_type, String ip_type, String ip, String franchise_column, int franchise) throws EmptySQLURLException {
        checkURL();

        // Get ID
        int id =  
            Integer.parseInt(
                SQL.query(
                    connectionURL,
                    "USE " + database + "; " +
                    "SELECT " + id_type + " " +
                    "FROM " + table_name + " " +
                    "WHERE " + 
                        ip_type  + " LIKE " + ip + 
                        " AND " +
                        franchise_column + " == " + franchise + 
                    ";"

                )
                .get(0)
            )
        ;

        return id;

    }

    /**
     * 
     * Checks if the remote cli is enabled
     * 
     * @param database Database to connect to
     * @param table_name Name of the table to access
     * @param id_type The column name that contains the id
     * @param id ID number
     * 
     * @return If the app should run the remote CLI
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean remoteCLI(String database, String table_name, String id_type, int id) throws EmptySQLURLException {
        // Get ID
        int verbosity =  
            Integer.parseInt(
                queryByID(database, table_name, id_type, id, "RemoteCLI")
            )
        ;

        return verbosity == 1 ? true : false;

    }

    /**
     * 
     * Checks if the local CLI is enabled
     * 
     * @param database Database to connect to
     * @param table_name Name of the table to access
     * @param id_type The column name that contains the id
     * @param id ID number
     * 
     * @return If the app should run the local CLI
     * 
     * @throws EmptySQLURLException
     */

    public synchronized static boolean localCLI(String database, String table_name, String id_type, int id) throws EmptySQLURLException {
        // Get ID
        int verbosity =  
            Integer.parseInt(
                queryByID(database, table_name, id_type, id, "LocalCLI")
            )
        ;

        return verbosity == 1 ? true : false;

    }
    
    
}
