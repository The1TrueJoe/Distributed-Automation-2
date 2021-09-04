package com.jtelaa.da2.lib.net.ports;

/**
 * Enumeration type for basic system ports
 * 
 * @since 2
 * @author Joseph
 */

public enum SysPorts implements Ports {
    /** Port for bot heartbeats */
    HEARTBEAT(8679),

    /** Port for bot heartbeats (Master director) */
    HEARTBEAT_MASTER(8680),

    /** Port to enroll new bots */
    ENROLL(8699),

    /** Port for commands */
    CMD(9000),

    /** Port for info */
    INFO(8700),

    /** Port for responses */
    RESPONSE(1000),

    /** Port for info */
    INFO_RESPONSE(8702),

    /** Port for logging */
    LOG(8775),

    /** Additional port for logging */
    @Deprecated 
    LOG_2(8776),

    /** Port for local logging */
    LOCAL_LOG(8776),

    /** Establish log */
    ESTABLISH_LOCAL_LOG(8780)

    ;

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
