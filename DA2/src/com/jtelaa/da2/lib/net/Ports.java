package com.jtelaa.da2.lib.net;

public enum Ports {

    HEARTBEAT(679),
    CMD(699),
    RESPONSE(701),
    LOG(775);

    private final int port;

    Ports(int port) {
        this.port = port;
    }

    public int getPort() { return port; }
}
