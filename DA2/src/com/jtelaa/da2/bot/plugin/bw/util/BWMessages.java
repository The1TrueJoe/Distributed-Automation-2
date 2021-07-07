package com.jtelaa.da2.bot.plugin.bw.util;

public enum BWMessages {
    
    ACCOUNT_REQUEST_MESSAGE("Gimme an account!"),
    ACCOUNT_REPONSE_MESSAGE("Here is da account!"),
    QUERY_REQUEST_MESSAGE("Gimme da Query!")
    ;

    private final char[] message;

    BWMessages(String message) {
        this.message = message.toCharArray();
    }

    public String getMessage() { return new String(message); }
    public String toString() { return getMessage(); }
}