package com.jtelaa.da2.querygen.util;

/**
 * Object for search queries
 * 
 * @since 2
 * @author Joseph
 */

public class Query {
    
    private String unformatted_query;
    private String formatted_query; 

    public Query(String query, Boolean formatted) {
        if (formatted) {
            query = formatted_query;
        } else {
            query = unformatted_query;
            formatted_query = formatQuery(unformatted_query);
        }

        
    }

    public Query(String formatted_query) {
        this.formatted_query = formatted_query;

    }

    public String toString() { return getQuery(); }

    public String getUnformattedQuery() { unformatted_query = unformatQuery(formatted_query); return unformatted_query; }
    public String getFormattedQuery() { formatted_query = formatQuery(unformatted_query); return formatted_query; }
    public String getQuery() { return formatted_query; }

    public static String formatQuery(String query) { return query.replaceAll(" ", "+"); }
    public static String unformatQuery(String query) { return query.replaceAll("+", " "); }
}
