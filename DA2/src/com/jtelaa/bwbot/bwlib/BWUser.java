package com.jtelaa.bwbot.bwlib;

import java.util.ArrayList;

import com.jtelaa.da2.lib.mail.MessageGateways;
import com.jtelaa.da2.lib.user.local.User;

/**
 * Serializable object for storing user data
 * 
 * @author Joseph
 * @since 2
 */

public class BWUser extends User {

    /**
     * Constructor 
     * 
     * @param user User object
     */

    public BWUser(User user) {
        super(user.name, user.password, user.mail, user.password_encrypted);

    }

    /**
     * Constructor 
     * 
     * @param user User name
     * 
     * @deprecated No longer using just user
     */

    @Deprecated
    public BWUser(String user) {
        super(user);

    }

    /**
     * Constructor
     * 
     * @param name Name of the user
     * @param password Password
     * @param mail Mail
     * @param password_encrypted Is the password already encrypted
     */

    public BWUser(String name, String password, String mail, boolean password_encrypted) {
        super(name, password, mail, password_encrypted);

    }

    /** IDs that are associated with the user */
    public ArrayList<Integer> entitled_bot_ids;

    /** Points that the user is entitled too */
    public int entitled_points;

    /** Redeemed points */
    public int redemed_points;

    /** Sets the email address to associate with the user */
    public void setAddress(String phone_number, MessageGateways gateways) { mail = (phone_number + "@" + gateways.getMMSAddress()); }


    /** Sets the entitled points */
    public void setEntitledPoints(int entitled_points) { this.entitled_points = entitled_points; }

    /** Add entitled points */
    public void addEntitledPoints(int points) { entitled_points += points; }

    /** Subtracte entitled points*/
    public void subtractEntitledPoints(int points) { entitled_points -= points; }


    /** Set redeemed points */
    public void setRedeemedPoints(int redemed_points) { this.redemed_points = redemed_points; }

    /** Add the number of redeemed points */
    public void addRedeemedPoints(int points) { redemed_points += points; }

    /** Subtract the number of redeemed points */
    public void subtractRedeemedPoints(int points) { redemed_points -= points; }
    
}
