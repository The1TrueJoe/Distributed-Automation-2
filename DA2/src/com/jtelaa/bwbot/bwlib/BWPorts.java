package com.jtelaa.bwbot.bwlib;

/**
 * Enumeration type containing port numbers for the BW system plugin
 * 
 * @since 2
 * @author Joseph
 */

public enum BWPorts {

    /** Port for query requests  */
    QUERY_REQUEST(9998),

    /** Port to receive queries */
    QUERY_RECEIVE(9999),

    /** Port to announce info (Eg. Accounts) */
    INFO_ANNOUNCE(10000),

    /** Port to request info (Explicit requests)*/
    INFO_REQUEST(10010),

    /** Port to receive info (Explicit requests) */
    INFO_RECEIVE(10011);

    /** Port number */
    private final int port;

    /**
     * Constructor
     * 
     * @param port Port number
     */

    BWPorts(int port) {
        this.port = port;
        
    }

    /** Return port */
    public int getPort() { return port; }
    
}