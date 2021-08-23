package com.jtelaa.da2.lib.net.ports;

public class ManualPort implements Ports {

    /** Port number */
    private int port;

    /**
     * Constructor
     * 
     * @param port Port 
     */
    
    public ManualPort(int port) {
        this.port = port;
    }

    /** @return port */
    public int getPort() { return port; }
    
}
