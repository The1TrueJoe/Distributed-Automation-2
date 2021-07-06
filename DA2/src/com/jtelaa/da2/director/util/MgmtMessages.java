package com.jtelaa.da2.director.util;

public enum MgmtMessages {
    
    INVALID_ID_MESSAGE("Invalid Bot ID"),
    BOT_ENABLE_MESSAGE("Wake Up Bot!")
    ;

    private final char[] message;

    MgmtMessages(String message) {
        this.message = message.toCharArray();
    }

    public String getMessage() { return new String(message); }
    public String toString() { return getMessage(); }
}