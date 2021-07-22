package com.jtelaa.da2.lib.control;

/**
 * Simple abstraction interface for message enums
 * 
 * @since 2
 * @author Joseph
 */

public interface Messages {
    
    /** @return Message as a string */
    public String getMessage();

    /** @return Message as a string */
    public String toString();
    
    /** Check if the messages are the same */
    public boolean equals(String message);

    /** Check if the string contains a message */
    public boolean contains(String message);
    
}
