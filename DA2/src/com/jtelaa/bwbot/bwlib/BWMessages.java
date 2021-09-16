package com.jtelaa.bwbot.bwlib;

import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.control.Messages;

/***
 * 
 */

 // TODO Comment

public enum BWMessages implements Messages {
    
    /** Request a Microsoft Account */
    ACCOUNT_REQUEST_MESSAGE("Gimme an account!"),

    /** Request a Microsoft Account */
    BOT_REQUEST_MESSAGE(MgmtMessages.BOT_REQUEST),

    /** Reponse contains microsoft account */
    @Deprecated
    ACCOUNT_REPONSE_MESSAGE("Here is da account!"),

    /** Request a query */
    QUERY_REQUEST_MESSAGE("Gimme da Query!"),

    /** Request a gateway */
    GATEWAY_REQUEST_MESSAGE("Gimme da Gateway!"),

    /** Response contains card */
    @Deprecated
    CARD_REDEMPTION_MESSAGE("Here is your card!"),

    /** Response contains code */
    @Deprecated
    CARD_RESPONSE_MESSAGE("Here is the code!")
    
    ;

    private final char[] message;

    BWMessages(String message) {
        this.message = message.toCharArray();

    }

    BWMessages(Messages message) {
        this(message.getMessage());

    }

    /** @return Message as a string */
    @Override
    public String getMessage() { return new String(message); }

    /** @return Message as a string */
    @Override
    public String toString() { return getMessage(); }

    /** @return Last index of message (Used for concatenating the contents after the message) */
    public int endIndex() { return message.length; }

    /** Remote the message header from the whole message */
    public synchronized static String remove(String message, BWMessages messages) {
        return message.substring(messages.endIndex());
        
    }
    
    /** Check if the messages are the same */
    @Override
    public boolean equals(String message) { return getMessage().equalsIgnoreCase(message); }

    /** Check if the string contains a message */
    @Override
    public boolean contains(String message) { return getMessage().contains(message.toUpperCase()); }

}