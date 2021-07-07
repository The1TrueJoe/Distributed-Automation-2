package com.jtelaa.da2.director.botmgmt;

/**
 * ENUM of mgmt messages that the system interprets for control purposes
 * 
 * @since 2
 * @author Josepj
 */

public enum MgmtMessages {

    INVALID_ID_MESSAGE("Invalid Bot ID"),
    BOT_ENABLE_MESSAGE("Wake Up Bot!"),
    NONE("NO");
    ;

    public char[] message;

    MgmtMessages(String sys_message) {
        message = sys_message.toCharArray();
    }

    /** @return Message as a string */
    public String getMessage() { return new String(message); }

    public String toString() { return getMessage(); }
    
    /** Check if the mgmt messages are equal */
    public boolean equals(MgmtMessages mgmt_message) { return getMessage().equalsIgnoreCase(mgmt_message.getMessage()); }
    
}