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

    private String first_name;
    private String last_name;

    private int points;

    public Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public Account(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        
    }

    public Account(String first_name, String last_name, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;

    }

    public Account(String first_name, String last_name, String username, String password, int points) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.points = points;
        
    }

    public void setFirstName(String first_name) { this.first_name = first_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }

    public void updatePoints(int change) { points += change; }
    public void newPoints(int new_points) { points = new_points; }
    
}
