package com.jtelaa.da2.redemption_manager.util;

import java.io.Serializable;

/**
 * Serializable object for storing account info
 * 
 * @since 1
 * @author Joseph
 */

public class Account implements Serializable {

    private String username;
    private String password;

    private int points;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public Account(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }

    public void updatePoints(int change) { points += change; }
    public void newPoints(int new_points) { points = new_points; }
    
}
