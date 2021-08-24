package com.jtelaa.bwbot.bwlib;

import java.io.Serializable;

/**
 * 
 */

public class BWUser implements Serializable {

    /** */
    private int entitled_points;

    /** */
    private int redemed_points;

    /** */
    private String address;
    
    /** */
    private String name;

    
    /** */
    public void setName(String name) { this.name = name; }

    /** */
    public void setAddress(String address) { this.address = address; }


    /** */
    public void setEntitledPoints(int entitled_points) { this.entitled_points = entitled_points; }

    /** */
    public void addEntitledPoints(int points) { entitled_points += points; }

    /** */
    public void subtractEntitledPoints(int points) { entitled_points -= points; }


    /** */
    public void setRedeemedPoints(int redemed_points) { this.redemed_points = redemed_points; }

    /** */
    public void addRedeemedPoints(int points) { redemed_points += points; }

    /** */
    public void subtractRedeemedPoints(int points) { redemed_points -= points; }


    /** */
    public String getName() { return name; }

    /** */
    public String getAddress() { return address; }


    /** */
    public int getEntitledPoints() { return entitled_points; }
    

    /** */
    public int getRedeemedPoints() { return redemed_points; }
    
}
