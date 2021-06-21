package com.jtelaa.da2.lib.net;

public enum StateCodes {
    
    WRONG_IP(1),
    CANNOT_CONNECT(2);

    private final int code;

    StateCodes(int code) {
        this.code = code;
    }

    public int getCode() { return code; }
}
