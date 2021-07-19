package com.jtelaa.bwbot.bwlib;

import java.io.IOException;
import java.io.Serializable;
import java.util.GregorianCalendar;

import com.jtelaa.bwbot.bw_manager.accounts.Accounts;
import com.jtelaa.da2.lib.config.ConfigHandler;
import com.jtelaa.da2.lib.files.ObjectUtils;

/**
 * Serializable object for storing account info
 * 
 * @since 1
 * @author Joseph
 */

public class Account implements Serializable {

    /** */
    public static final String EMAIL_DOMAIN = "@hotmail.com";

    /** */
    private String username;
    /** */
    private String email;
    /** */
    private String password;

    /** */
    private String first_name;
    /** */
    private String last_name;

    /** */
    private GregorianCalendar birth_date;

    /** */
    private int points;

    /**
     * Default Constructor
     */

    public Account() {

    }

    /**
     * Creates a new account and loads it from a file
     * 
     * @param user_path
     */

    public Account(String user_path) {
        ConfigHandler config = new ConfigHandler(user_path);

        setFirstName(config.getProperty("first_name"));
        setLastName(config.getProperty("last_name"));
        setUsername(config.getProperty("user_name"));
        setPassword(config.getProperty("password"));

        newPoints(Integer.parseInt(config.getProperty("points")));
        
        try {
            GregorianCalendar bday = new GregorianCalendar();
            bday.setTimeInMillis(Long.parseLong(config.getProperty("password")));
            setBirthDay(bday);
        
        } catch (Exception e) {
            setBirthDay(Accounts.getRandomBirthDate());
            
        }

    }

    /**
     * Exports account into a properties file
     * 
     * @param path
     */

    public void exportAccount(String path) {
        ConfigHandler config = new ConfigHandler(path);

        config.setProperty("first_name", first_name);
        config.setProperty("last_name", last_name);
        config.setProperty("user_name", username);
        config.setProperty("password", password);
        config.setProperty("points", points + "");

        try {
            config.setProperty("birthday", ObjectUtils.objtoString(birth_date));

        } catch (IOException e) {

        }
    }

    /** */
    public void setFirstName(String first_name) { this.first_name = first_name; }
    /** */
    public void setLastName(String last_name) { this.last_name = last_name; }

    /** */
    public void setUsername(String username) { this.username = username; email = username + EMAIL_DOMAIN;}
    /** */
    public void setPassword(String password) { this.password = password; }

    /** */
    public void setBirthDay(GregorianCalendar birth_date) { this.birth_date = birth_date; }
    
    /** */
    public String getFirstName() { return first_name; }
    /** */
    public String getLastName() { return last_name; }

    /** */
    public String getUsername() { return username; }
    /** */
    public String getEmail() { return email; }
    /** */
    public String getPassword() { return password; }
    /** */
    public int getPoints() { return points; }

    /** */
    public GregorianCalendar getBirthDay() { return birth_date; }

    /** */
    public String getFormattedBirthDay() { 
        return 
            birth_date.get(GregorianCalendar.YEAR) + "-" + 
            birth_date.get(GregorianCalendar.MONTH) + "-" + 
            birth_date.get(GregorianCalendar.DAY_OF_MONTH)
        ; 
    }

    /** */
    public void updatePoints(int change) { points += change; }
    /** */
    public void newPoints(int new_points) { points = new_points; }

    /** */
    public String toString() { 
        return 
            "[Name: "+ first_name + " " + last_name + 
            ", Birthday: " + getFormattedBirthDay() +
            ", Password: " + password + 
            ", Email: " + email + "]";
    }
    
    public String formatToString() {
        return String.format(
            "%-40s%-32s%-24s%-32s",
            "Email: " + email,
            "Name: "+ first_name + " " + last_name,
            "Birthday: " + getFormattedBirthDay(),
            "Password: " + password
            );

    }

}
