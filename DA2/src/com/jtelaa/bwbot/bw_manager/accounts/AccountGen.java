package com.jtelaa.bwbot.bw_manager.accounts;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import com.jtelaa.bwbot.bwlib.Account;
import com.jtelaa.bwbot.bwlib.Accounts;
import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.misc.MiscUtil;

/**
 * Class that stores the utilities needed for random account creation
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.bwbot.bwlib.Account
 */

public class AccountGen {

    /** The account last created by the generator */
    public volatile static Account last_created_account = new Account();

    /** Random */
    private static Random rng;

    /** Path of name files */
    private static final String NAME_PATH = "com/jtelaa/bwbot/bw_manager/accounts/namedata/";
    
    /**
     * Generate new account info
     * 
     * @return new Account
     */
    
    public static Account generateAccount() {
        // Setup local vars
        rng = new Random();
        Account account = new Account();

        // Set attributes
        account.setFirstName(getRandomFirstName());
        account.setLastName(getRandomLastName());
        account.setBirthDay(getRandomBirthDate());
        account.setUsername(getRandomUsername(account));
        account.setPassword(getRandomPassword(8 + rng.nextInt(6)));
        account.setID(last_created_account.getID() + 1);

        // Return account
        last_created_account = account;
        return account;

    }

    /**
     * Creates a random username
     * 
     * <p> Requires an account to already have a
     * first name, last name, and birthday.
     * 
     * @param account Account 
     * @param latency Latency to introduce (If generating using an API)
     * 
     * @return random username
     */

    public static String getRandomUsername(Account account, int latency) {
        MiscUtil.waitamoment(latency);
        return getRandomUsername(account);

    }

    /**
     * Creates a random username
     * 
     * <p> Requires an account to already have a
     * first name, last name, and birthday.
     * 
     * @param account Account 
     * 
     * @return random username
     */

    public static String getRandomUsername(Account account) {
        // Setup local vars
        int num = rng.nextInt(20);
        String username = "";

        // First name 
        if (num >= 10) {
            username += account.getFirstName();
        } else {
            username += account.getFirstName().substring(0, (int) account.getFirstName().length()/2);
        }
        
        // Add a dot
        if (num >= 5 && num <= 9) { username += "."; }
        
        // Last name
        if (num <= 10) {
            username += account.getLastName();
        } else {
            username += account.getLastName().substring(0, account.getLastName().length() - 2);
        }
        
        // Add numbers at the end
        if (num <= 5) {
            username += account.getBirthDay().get(GregorianCalendar.DAY_OF_MONTH);
        } else if (num <= 10) {
            username += account.getBirthDay().get(GregorianCalendar.MONTH);
        } else if (num <= 17) {
            username += (account.getBirthDay().get(GregorianCalendar.YEAR) + "").substring(2);
        } else {
            username += rng.nextInt(99);
        }

        // Validate username and if invalid, regenerate
        if (Accounts.validateUsernameRapidAPI(username.toLowerCase())) {
            // Return username
            return username.toLowerCase();

        } else {
            // Generate new username
            return getRandomUsername(account, 1000);

        }
    }

    /**
     * Generates a random alphanumeric password
     * 
     * @param length Length of the password to generate
     * 
     * @return Password
     */

    public static String getRandomPassword(int length) {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        // Setup builder
        StringBuilder stringBuilder = new StringBuilder();
 
        // Generate rand
        SecureRandom random = new SecureRandom();
        int num = rng.nextInt(3);

        // Start with $ char
        if (num <= 1) { stringBuilder.append("$"); }

        // Build password
        for (int i = 0; i < length; i++) {
            int random_character = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(random_character));

        }

        // End with ! char
        if (num >= 2) { stringBuilder.append("!"); }
 
        // Return
        return stringBuilder.toString();

    }

    /**
     * Generates random birth date between 1977 and 2011
     * 
     * @return Birthday
     */

    public static GregorianCalendar getRandomBirthDate() {
        // Setup calendar
        GregorianCalendar calendar = new GregorianCalendar();
        
        // Year
        int year = 1977 + rng.nextInt(2011 - 1977);
        calendar.set(GregorianCalendar.YEAR, year);

        // Month
        int month = 1 + rng.nextInt(calendar.getActualMaximum(GregorianCalendar.MONTH) - 1);
        calendar.set(GregorianCalendar.MONTH, month);

        // Day
        int day = 1 + rng.nextInt(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - 1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, day);

        // Return
        return calendar;

    }

    /**
     * Generates random first name from a list of the most popular first names
     * 
     * @return First name
     */

    public static String getRandomFirstName() {
        // Generate year
        int year = rng.nextInt(25) + 2000;

        // Year limits
        if (year > 2018) { year = 2018; }
        if (year < 2000) { year = 2000; }

        // Load file
        ArrayList<String> lines = FileUtil.listLinesInternalFile(NAME_PATH + year + "Names.txt");

        // If invalid file
        if (lines.size() < 1) { return "Chad"; }

        // Return random first name
        return (lines.get(rng.nextInt(lines.size())));

    }

    /**
     * Generates random last name from a list of the most popular last names
     * 
     * @return Last name
     */

    public static String getRandomLastName() {
        // Load file
        ArrayList<String> lines = FileUtil.listLinesInternalFile(NAME_PATH + "LastNames.txt");

        // If invalid file
        if (lines.size() < 1) { return "Lee"; }

        // Return random last name
        return (lines.get(rng.nextInt(lines.size())));
        
    }


    /**
     * Test to list out generated accounts
     */

    public static void main(String[] args) {
        while (true) {
            System.out.println(generateAccount().formatToString());
        }

    }
    
}
