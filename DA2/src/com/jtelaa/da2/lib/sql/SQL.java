package com.jtelaa.da2.lib.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.log.Log;

/**
 * SQL utils for queries
 * 
 * @author Joseph
 * @since 2
 */

public class SQL {

    /** Database server default ip */
    public static volatile String DATABASE_DEFAULT_IP = "172.16.2.3";

    /**
     * Return the connection url to query the server
     * 
     * @param url IP of the server
     * @param database Database name
     * @param user Username
     * @param pass Password
     * 
     * @return The connection url to query the database
     */

    public static synchronized String getConnectionURL(String url, String database, String user, String pass) {
        // Setup URL
        String connectionUrl =
                "jdbc:sqlserver://" + url + ";"
                + "database=" + database + ";"
                + "user=" + user + ";"
                + "password=" + pass + ";"
                + "encrypt=true;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

        // Return
        return connectionUrl;

    }

    /**
     * Create the connection URL to connect to the database
     * 
     * @param config Configl handler to load info from
     * 
     * @return Return connection URL
     */
    
    public static synchronized String getConnectionURL(Properties config) {
        // Setup variables
        String url, database, user, pass;

        // Load properties
        url = config.getProperty("URL", DATABASE_DEFAULT_IP);
        database = config.getProperty("database", "DistributedAutomation");
        user = config.getProperty("user", "default");
        pass = config.getProperty("password", "Defau1tpass!");

        // Create and return URL
        return getConnectionURL(url, database, user, pass);
        
    }

    /**
     * Load a query from a file
     * 
     * @param path .sql File path
     * 
     * @return query as a single line
     */

    public static synchronized String getQuery(String path) {
        return getQuery(new File(path));

    }

    /**
     * Replaces the indentifiers in a template query
     * 
     * @param query Query to alter
     * @param replace Map of <identifier, value> to replace in the original query
     * 
     * @return New modified query
     */

    public static synchronized String replace(String query, Map<String, String> replace) {
        String[] words = query.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (replace.containsKey(words[i])) {
                words[i] = replace.get(words[i]);
                
            }
        }

        String new_query = "";

        for (String word : words) {
            new_query += word + " ";
        }

        return new_query;

    }

     /**
     * Load a query from a file
     * 
     * @param path .sql File
     * 
     * @return query as a single line
     */

    public static synchronized String getQuery(File file) {
        // Setup array and string
        String query = "";
        ArrayList<String> lines = FileUtil.listLinesFile(file);

        // Run through all lines
        for (String line : lines) {
            query = query + line + " ";

        }

        // Return
        return query;

    }

    /**
     * Load a query from an internal file
     * 
     * @param path .sql File
     * 
     * @return query as a single line
     */

    public static synchronized String getInternalQuery(String path) {
        // Setup array and string
        String query = "";
        ArrayList<String> lines = FileUtil.listLinesInternalFile(path);

        // Run through all lines
        for (String line : lines) {
            query = query + line + " ";

        }

        // Return
        return query;

    }

    /**
     * Query the database
     * 
     * @param url URL to query
     * @param sql SQL query
     * 
     * @return results
     */

    public static synchronized ArrayList<String> query(String url, String sql) {
        // Setup
        ResultSet set = null;
        ArrayList<String> lines = new ArrayList<String>();

        try {
            // Setup connection
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            // Execute
            set = statement.executeQuery(sql);

            // Read reponse
            int i = 0;
            while (set.next()) {
                lines.add(set.getString(i));
                i++;

            }

        } catch (Exception e) {
            // Log error
            Log.sendMessage(e);
            lines.add(e.getMessage());

        }

        // Return
        return lines;

    }

    /**
     * Run an SQL insertion query
     * 
     * @param url URL to query
     * @param sql SQL query
     * 
     * @return response
     */

    public static synchronized ArrayList<String> insert(String url, String sql) {
        // Setup
        ResultSet set = null;
        ArrayList<String> lines = new ArrayList<String>();

        try {
            // Setup connection
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Execure
            preparedStatement.execute();
            set = preparedStatement.getGeneratedKeys();

            // Read response
            int i = 0;
            while (set.next()) {
                lines.add(set.getString(i));
                i++;

            }

        } catch (Exception e) {
            // Log error
            Log.sendMessage(e);
            lines.add(e.getMessage());

        }

        // Return
        return lines;

    }
}
