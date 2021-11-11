package com.jtelaa.da2.lib.sql;

import com.jtelaa.da2.lib.net.NetTools;

/**
 * Utilies class containing methods to query the database for server configs
 * 
 * @author Joseph
 * @since 2
 */

public class DA2SQLQueries {

    /**
     * 
     * @param table_name
     * @param connectionURL get url from com.jtelaa.da2.lib.sql.SQL.getConnectionURL();
     * @return
     */

    public static int getID(String table_name, String connectionURL) {
        // Get ID
        int id =  
            Integer.parseInt(
                SQL.query(
                    connectionURL,

                    "SELECT ID " +
                    "FROM " + table_name + " " +
                    "WHERE LastKnownIP LIKE '" + NetTools.getLocalIP() + "';"

                )
                .get(0)
            )
        ;

        return id;
    }
    
}
