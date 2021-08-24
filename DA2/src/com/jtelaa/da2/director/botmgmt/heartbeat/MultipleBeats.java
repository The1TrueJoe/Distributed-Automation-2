package com.jtelaa.da2.director.botmgmt.heartbeat;

import java.io.Serializable;

/**
 * Simple encapsulation of multiple heartbeats
 * 
 * @since 2
 */

public class MultipleBeats implements Serializable {

    /** IDs of responses */
    public volatile int[] ids;

    /** @param ids IDs of bots */
    public MultipleBeats(int[] ids) {
        this.ids = ids;
    }

    
}
