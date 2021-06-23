package com.jtelaa.da2.querygen.util;

public class Query {
    
    private String unformatted_query;
    private String formatted_query; 

    public Query(String query, Boolean formatted) {
        if (formatted) {
            query = formatted_query;
            unformatted_query = unformatQuery(formatted_query);
        } else {
            query = unformatted_query;
            formatted_query = formatQuery(unformatted_query);
        }

        
    }

    public Query(String formatted_query) {
        this.formatted_query = formatted_query;
        unformatted_query = unformatQuery(formatted_query);

    }

    public String getUnformattedQuery() { return unformatted_query; }
    public String getQuery() { return formatted_query; }

    public static String formatQuery(String query) { return query.replaceAll(" ", "+"); }
    public static String unformatQuery(String query) { return query.replaceAll("+", " "); }
}
