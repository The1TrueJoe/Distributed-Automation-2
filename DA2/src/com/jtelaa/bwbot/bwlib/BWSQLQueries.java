package com.jtelaa.bwbot.bwlib;

import com.jtelaa.da2.lib.sql.SQL;

/**
 * Utilies class containing methods to query the database for server configs
 * 
 * @author Joseph
 * @since 2
 */

public class BWSQLQueries {

    /**
     * 
     * @param id
     * @param table_name
     * @param connectionURL
     * @return
     */

    public static int getReceivePort(int id, String table_name, String connectionURL) {
        int port = 
            Integer.parseInt(
                SQL.query(
                    connectionURL,

                    "SELECT ReceivePort " +
                    "FROM " + table_name + " " + 
                    "WHERE ID = " + id

                )
                .get(0)
            )
        ;

        return port;

    }

    /**
     * 
     * @param id
     * @param table_name
     * @param connectionURL
     * @return
     */

    public static int getRequestPort(int id, String table_name, String connectionURL) {
        int port = 
            Integer.parseInt(
                SQL.query(
                    connectionURL,

                    "SELECT RequestPort " +
                    "FROM " + table_name + " " + 
                    "WHERE ID = " + id

                )
                .get(0)
            )
        ;

        return port;

    }


    
}
