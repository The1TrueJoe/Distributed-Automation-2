package com.jtelaa.bwbot.bwlib;

import com.jtelaa.da2.lib.control.Messages;

/***
 * 
 */

 // TODO Comment

public enum BWMessages implements Messages {
    
    ACCOUNT_REQUEST_MESSAGE("Gimme an account!"),
    ACCOUNT_REPONSE_MESSAGE("Here is da account!"),
    QUERY_REQUEST_MESSAGE("Gimme da Query!"),
    GATEWAY_REQUEST_MESSAGE("Gimme da Gateway!"),
    CARD_REDEMPTION_MESSAGE("Here is your card!"),
    CARD_RESPONSE_MESSAGE("Here is the code!")
    ;

    private final char[] message;

    BWMessages(String message) {
        this.message = message.toCharArray();

    }

    /** @return Message as a string */
    public String getMessage() { return new String(message); }

    /** @return Message as a string */
    public String toString() { return getMessage(); }
    
    /** Check if the messages are the same */
    public synchronized boolean equals(String message) { return getMessage().equalsIgnoreCase(message); }

    /** Check if the string contains a message */
    public synchronized boolean contains(String message) { return getMessage().contains(message.toUpperCase()); }

}