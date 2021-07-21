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
    
    /** Check if the mgmt messages are equal */
    public boolean equals(Messages mgmt_message);
    
}
