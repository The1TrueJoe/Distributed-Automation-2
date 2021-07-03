package com.jtelaa.da2.bot.plugin.bw.util;

// TODO comment

public enum BWPorts {

    QUERY_REQUEST(9998),
    QUERY_RECEIVE(9999),
    INFO_ANNOUNCE(10000),
    INFO_REQUEST(10010),
    INFO_RECEIVE(10011);

    private final int port;

    BWPorts(int port) {
        this.port = port;
    }

    public int getPort() { return port; }
}