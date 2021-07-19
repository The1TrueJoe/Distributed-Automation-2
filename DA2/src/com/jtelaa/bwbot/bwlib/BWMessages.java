package com.jtelaa.bwbot.bwlib;

/***
 * 
 */

 // TODO Comment

public enum BWMessages {
    
    ACCOUNT_REQUEST_MESSAGE("Gimme an account!"),
    ACCOUNT_REPONSE_MESSAGE("Here is da account!"),
    QUERY_REQUEST_MESSAGE("Gimme da Query!"),
    CARD_REDEMPTION_MESSAGE("Here is your card!"),
    CARD_RESPONSE_MESSAGE("Here is the code!")
    ;

    private final char[] message;

    BWMessages(String message) {
        this.message = message.toCharArray();
    }

    public String getMessage() { return new String(message); }
    public String toString() { return getMessage(); }
}