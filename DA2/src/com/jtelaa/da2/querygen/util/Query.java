package com.jtelaa.da2.querygen.util;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.client.ClientUDP;
import com.jtelaa.da2.lib.net.server.ServerUDP;

public class Query {

    public static final String QUERY_REQUEST_MESSAGE = "Gimme da Query!";
    
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

    /**
     * Sends a request for a query
     * 
     * @return The search query
     */

    public static Query requestQuery(String ip, int request, int receive) {
        String response = "";
        sendRequest(ip, request);

        ServerUDP get = new ServerUDP(receive);
        get.startServer();
        
        while (!MiscUtil.notBlank(response)) {
            response = get.getMessage();

        }

        get.closeServer();
        return new Query(response, false);

    }

    /**
     * Sends out a request for a search query
     * 
     * @param ip IP address to access
     * @param request Port for sending requests
     */

    private static void sendRequest(String ip, int request) {
        ClientUDP send = new ClientUDP(ip, request);
        send.startClient();
        send.sendMessage(QUERY_REQUEST_MESSAGE);
        send.closeClient();

    }

    public String toString() { return getQuery(); }

    public String getUnformattedQuery() { return unformatted_query; }
    public String getQuery() { return formatted_query; }

    public static String formatQuery(String query) { return query.replaceAll(" ", "+"); }
    public static String unformatQuery(String query) { return query.replaceAll("+", " "); }
}
