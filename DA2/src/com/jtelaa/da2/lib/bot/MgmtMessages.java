package com.jtelaa.da2.lib.bot;

import com.jtelaa.da2.lib.control.Messages;

/**
 * ENUM of mgmt messages that the system interprets for control purposes
 * 
 * @since 2
 * @author Joseph
 */

public enum MgmtMessages implements Messages {

    /** Heartbeat message */
    BEAT("ALIVE!"),

    /** ID Request */
    ID_REQUEST("GIMME DA ID!"),

    /** ID Request */
    BOT_REQUEST("GIMME DIS BOT!"),

    /** Enable bot CLI */
    BOT_ENABLE_MESSAGE("Wake Up Bot!"),

    NONE("NO");
    ;

    private volatile char[] message;

    MgmtMessages(String sys_message) {
        message = sys_message.toCharArray();
        
    }

    /** @return Message as a string */
    @Override
    public synchronized String getMessage() { return new String(message); }

    /** @return Message as a string */
    @Override
    public synchronized String toString() { return getMessage(); }
    
    /** Check if the messages are the same */
    @Override
    public synchronized boolean equals(String message) { return getMessage().equalsIgnoreCase(message); }

    /** Check if the string contains a message */
    @Override
    public synchronized boolean contains(String message) { return getMessage().contains(message.toUpperCase()); }
    
}