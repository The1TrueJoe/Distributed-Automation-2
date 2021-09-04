package com.jtelaa.da2.lib.control;

public enum SysMessages implements Messages {

    /** Open local log */
    ESTABLISH_LOCAL_LOG("I WANT TO CONNECT TO THE LOCAL LOG!"),

    /** Close local log */
    CLOSE_LOCAL_LOG("STOP THE LOCAL LOG")

    ;

    private volatile char[] message;

    SysMessages(String sys_message) {
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
