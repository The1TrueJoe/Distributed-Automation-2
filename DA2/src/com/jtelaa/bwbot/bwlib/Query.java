package com.jtelaa.bwbot.bwlib;

import java.io.Serializable;

/**
 * Object for search queries
 * 
 * @since 2
 * @author Joseph
 */

public class Query implements Serializable {

    /** Bing Search URL */
    public static final String BING_URL = "bing.com/search?q=";
    
    /** Unformatted query */
    @Deprecated
    public String unformatted_query;
    /** Formatted query */
    public String formatted_query; 

    /**
     * Constructor
     * 
     * @param query Search query
     * @param formatted Whether it is formatted or not
     */

    public Query(String query, Boolean formatted) {
        if (formatted) {
            query = formatted_query;
        } else {
            query = unformatted_query;
            formatted_query = formatQuery(unformatted_query);
            
        }
    }

    /**
     * Constructor
     * 
     * @param formatted_query The formatted query
     */

    public Query(String formatted_query) {
        this.formatted_query = formatted_query;

    }

    /** Return query */
    public String toString() { return getQuery(); }

    /** Get unformatted query @return unformatted query */
    @Deprecated
    public String getUnformattedQuery() { unformatted_query = unformatQuery(formatted_query); return unformatted_query; }
    /** Get formatted query @return formatted query */
    public String getFormattedQuery() { formatted_query = formatQuery(unformatted_query); return formatted_query; }
    /** Return query */
    public String getQuery() { return formatted_query; }

    /** Format query @return formatted query */
    public static String formatQuery(String query) { return query.replaceAll(" ", "+"); }
    /** Unformat query @return unformatted query */
    public static String unformatQuery(String query) { return query.replaceAll("+", " "); }
    
}
