package com.jtelaa.da2.lib.net;

/**
 * Enumeration type for basic system ports
 * 
 * @since 2
 * @author Joseph
 */

public enum SysPorts implements Ports {

    /** Port for bot heartbeats */
    BOT_ANNOUNCE(8678),

    /** Port for bot heartbeats */
    HEARTBEAT(8679),

    /** Port for bot heartbeats (Master director) */
    HEARTBEAT_MASTER(8680),

    /** Port for commands */
    CMD(8699),

    /** Port for info */
    INFO(8700),

    /** Port for responses */
    RESPONSE(8701),

    /** Port for info */
    INFO_RESPONSE(8702),

    /** Port for logging */
    LOG(8775);

    /** Port number */
    private final int port;

    /**
     * Constructor
     * 
     * @param port Port 
     */
    
    SysPorts(int port) {
        this.port = port;
    }

    /** @return port */
    public int getPort() { return port; }
}
