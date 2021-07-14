package com.jtelaa.da2.lib.net;

// TODO comment

public enum Ports {

    HEARTBEAT(8679),
    CMD(8699),
    RESPONSE(8701),
    LOG(8775);

    private final int port;

    Ports(int port) {
        this.port = port;
    }

    public int getPort() { return port; }
}
