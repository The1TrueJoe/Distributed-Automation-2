package com.jtelaa.da2.lib.net;

public enum Ports {

    HEARTBEAT(8679),
    CMD(8699),
    RESPONSE(8701),
    LOG(8775),
    QUERY_REQUEST(9998),
    QUERY_RECEIVE(9999);

    private final int port;

    Ports(int port) {
        this.port = port;
    }

    public int getPort() { return port; }
}
