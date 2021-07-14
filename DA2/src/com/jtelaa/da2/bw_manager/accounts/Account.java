package com.jtelaa.da2.bw_manager.accounts;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Serializable object for storing account info
 * 
 * @since 1
 * @author Joseph
 */

public class Account implements Serializable {

    public static final String EMAIL_DOMAIN = "@hotmail.com";

    private String username;
    private String email;
    private String password;

    private String first_name;
    private String last_name;

    private GregorianCalendar birth_date;

    private int points;

    public Account() {}

    public Account(String username, String password) {
        this.username = username;
        this.password = password;

        email = username + EMAIL_DOMAIN;

    }

    public Account(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;

        email = username + EMAIL_DOMAIN;
        
    }

    public Account(String first_name, String last_name, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;

        email = username + EMAIL_DOMAIN;

    }

    public Account(String first_name, String last_name, String username, String password, int points) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.points = points;

        email = username + EMAIL_DOMAIN;
        
    }

    public Account(String first_name, String last_name, String username, String password, GregorianCalendar birth_date) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.birth_date = birth_date;

        email = username + EMAIL_DOMAIN;

    }

    public Account(String first_name, String last_name, String username, String password, GregorianCalendar birth_date, int points) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.birth_date = birth_date;
        this.points = points;

        email = username + EMAIL_DOMAIN;
        
    }

    public void setFirstName(String first_name) { this.first_name = first_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }

    public void setUsername(String username) { this.username = username; email = username + EMAIL_DOMAIN;}
    public void setPassword(String password) { this.password = password; }

    public void setBirthDay(GregorianCalendar birth_date) { this.birth_date = birth_date; }
    
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public int getPoints() { return points; }

    public GregorianCalendar getBirthDay() { return birth_date; }

    public String getFormattedBirthDay() { 
        return 
            birth_date.get(GregorianCalendar.YEAR) + "-" + 
            birth_date.get(GregorianCalendar.MONTH) + "-" + 
            birth_date.get(GregorianCalendar.DAY_OF_MONTH)
        ; 
    }

    public void updatePoints(int change) { points += change; }
    public void newPoints(int new_points) { points = new_points; }

    public String toString() { 
        return 
            "[Email: " + email + 
            ", Name: "+ first_name + " " + last_name + 
            ", Birthday: " + getFormattedBirthDay() +
            ", Password: " + password + "]";
    }
    
}
