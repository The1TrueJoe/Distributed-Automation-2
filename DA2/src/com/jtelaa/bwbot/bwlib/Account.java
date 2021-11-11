package com.jtelaa.bwbot.bwlib;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;

import com.jtelaa.da2.lib.config.PropertiesUtils;
import com.jtelaa.da2.lib.files.ObjectUtils;
import com.jtelaa.da2.lib.log.Log;

/**
 * Serializable object for storing BW Rewards account info
 * 
 * @since 1
 * @author Joseph
 */

public class Account implements Serializable {

    /** Unique system id */
    public int id;

    /** Email domain */
    public static final String EMAIL_DOMAIN = "@hotmail.com";

    /** Is the account blank */
    public boolean is_blank;

    /** Account username */
    public String username;
    /** Account email */
    public String email;
    /** Account password */
    public String password;

    /** Account first name */
    public String first_name;
    /** Account last name */
    public String last_name;

    /** Account birthday */
    public GregorianCalendar birth_date;

    /** Account points */
    public int points;

    /**
     * Default Constructor
     */

    public Account() {
        is_blank = true;

    }

    /**
     * Creates a new account and loads it from a file
     * 
     * @param user_path
     */

    // TODO Add SQL Version
    public Account(String user_path) {
        // Config 
        Properties config = PropertiesUtils.importConfig(user_path);

        // Set info
        setFirstName(config.getProperty("first_name"));
        setLastName(config.getProperty("last_name"));
        setUsername(config.getProperty("user_name"));
        setPassword(config.getProperty("password"));
        setID(Integer.parseInt(config.getProperty("id")));
        newPoints(Integer.parseInt(config.getProperty("points")));
        
        // Birthday
        GregorianCalendar bday = new GregorianCalendar();

        try {
            // Set birthday
            bday.setTimeInMillis(Long.parseLong(config.getProperty("birthday")));
        
        } catch (Exception e) {
            // Send error
            Log.sendMessage(e);

            // Generate random birthday
            Random rand = new Random();
            bday.set(2002, rand.nextInt(11) + 1, rand.nextInt(27) + 1);
            
        }
        
        // Apply birthday
        setBirthDay(bday);

        // Change blanking status
        is_blank = false;

    }

    /**
     * Exports account into a properties file
     * 
     * @param path
     */

    // TODO Add SQL Version
    public void exportAccount(String path) {
        // Config
        Properties config = PropertiesUtils.importConfig(path);

        // Load info
        config.setProperty("first_name", first_name);
        config.setProperty("last_name", last_name);
        config.setProperty("user_name", username);
        config.setProperty("password", password);
        config.setProperty("points", points + "");
        config.setProperty("id", id + "");

        try {
            // Set birthday
            config.setProperty("birthday", ObjectUtils.objtoString(birth_date));

        } catch (Exception e) {
            // Send error
            Log.sendMessage(e);

        }
    }

    /** @return if account has any info stored */
    public boolean isBlank() { return is_blank; }

    /** Set ID */
    public void setID(int id) { this.id = id; is_blank = false; }

    /** Set first name */
    public void setFirstName(String first_name) { this.first_name = first_name; is_blank = false; }
    /** Set last name */
    public void setLastName(String last_name) { this.last_name = last_name; is_blank = false; }

    /** Set username */
    public void setUsername(String username) { this.username = username; email = username + EMAIL_DOMAIN; is_blank = false; }
    /** Set password */
    public void setPassword(String password) { this.password = password; is_blank = false; }

    /** Set birthday */
    public void setBirthDay(GregorianCalendar birth_date) { this.birth_date = birth_date; is_blank = false; }
    
    /** @return first name */
    public String getFirstName() { return first_name; }
    /** @return last name */
    public String getLastName() { return last_name; }

    /** @return ID */
    public int getID() { return id; }

    /** @return username */
    public String getUsername() { return username; }
    /** @return email */
    public String getEmail() { return email; }
    /** @return password */
    public String getPassword() { return password; }
    /** @return points */
    public int getPoints() { return points; }

    /** @return birthday */
    public GregorianCalendar getBirthDay() { return birth_date; }

    /** @return formatted birthday */
    public String getFormattedBirthDay() { 
        return 
            birth_date.get(GregorianCalendar.YEAR) + "-" + 
            birth_date.get(GregorianCalendar.MONTH) + "-" + 
            birth_date.get(GregorianCalendar.DAY_OF_MONTH)
        ; 
    }

    /** Update points @param change points to add */
    public void updatePoints(int change) { points += change; }
    /** Change points @param new_points points to change to */
    public void newPoints(int new_points) { points = new_points; }

    /** To String */
    public String toString() { 
        return 
            "[Name: "+ first_name + " " + last_name + 
            ", Birthday: " + getFormattedBirthDay() +
            ", Password: " + password + 
            ", Email: " + email + "]";
    }
    
    /** Formatted to String (Good for tables) */
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
