package com.jtelaa.da2.bot.plugin.bw.util;

public enum BWPorts {

    QUERY_REQUEST(9998),
    QUERY_RECEIVE(9999),
    INFO_ANNOUNCE(10000);

    private final int port;

    BWPorts(int port) {
        this.port = port;
    }

    public int getPort() { return port; }
}