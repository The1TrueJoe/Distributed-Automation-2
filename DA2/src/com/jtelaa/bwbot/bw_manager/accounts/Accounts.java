package com.jtelaa.bwbot.bw_manager.accounts;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import com.jtelaa.da2.lib.files.FileUtil;

/**
 * Class that stores the utilities needed for random account creation
 * 
 * @since 2
 * @author Joseph
 * 
 * @see com.jtelaa.bwbot.bw_manager.accounts.Account
 */

// TODO Comment

public class Accounts {

    private static Random rng;

    private static final String NAME_PATH = "com/jtelaa/da2/redemption_manager/accounts/namedata/";

    
    /**
     * Generate new account info
     * 
     * @return new Account
     */
    
    public static Account generateAccount() {
        rng = new Random();
        Account account = new Account();

        account.setFirstName(getRandomFirstName());
        account.setLastName(getRandomLastName());
        account.setBirthDay(getRandomBirthDate());
        account.setUsername(getRandomUsername(account));
        account.setPassword(getRandomPassword(8 + rng.nextInt(6)));

        return account;

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
        int num = rng.nextInt(20);
        String username = "";

        if (num >= 10) {
            username += account.getFirstName();
        } else {
            username += account.getFirstName().substring(0, (int) account.getFirstName().length()/2);
        }
        
        if (num >= 5 && num <= 9) { username += "."; }
        
        if (num <= 10) {
            username += account.getLastName();
        } else {
            username += account.getLastName().substring(0, account.getLastName().length() - 2);
        }
        
        if (num <= 5) {
            username += account.getBirthDay().get(GregorianCalendar.DAY_OF_MONTH);
        } else if (num <= 10) {
            username += account.getBirthDay().get(GregorianCalendar.MONTH);
        } else if (num <= 17) {
            username += (account.getBirthDay().get(GregorianCalendar.YEAR) + "").substring(2);
        } else {
            username += rng.nextInt(99);
        }
        
        return username.toLowerCase();

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
 
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();
 
        int num = rng.nextInt(3);
        if (num <= 1) { stringBuilder.append("$"); }

        for (int i = 0; i < length; i++) {
            int random_character = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(random_character));

        }

        if (num >= 2) { stringBuilder.append("!"); }
 
        return stringBuilder.toString();

    }

    /**
     * Generates random birth date between 1977 and 2011
     * 
     * @return Birthday
     */

    public static GregorianCalendar getRandomBirthDate() {
        GregorianCalendar calendar = new GregorianCalendar();
        
        int year = 1977 + rng.nextInt(2011 - 1977);
        calendar.set(GregorianCalendar.YEAR, year);

        int month = 1 + rng.nextInt(calendar.getActualMaximum(GregorianCalendar.MONTH) - 1);
        calendar.set(GregorianCalendar.MONTH, month);

        int day = 1 + rng.nextInt(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH) - 1);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, day);

        return calendar;

    }

    /**
     * Generates random first name from a list of the most popular first names
     * 
     * @return First name
     */

    public static String getRandomFirstName() {
        int year = rng.nextInt(25) + 2000;

        if (year > 2018) { year = 2018; }
        if (year < 2000) { year = 2000; }

        ArrayList<String> lines = FileUtil.listLinesInternalFile(NAME_PATH + year + "Names.txt");
        return (lines.get(rng.nextInt(lines.size())));

    }

    /**
     * Generates random last name from a list of the most popular last names
     * 
     * @return Last name
     */

    public static String getRandomLastName() {
        ArrayList<String> lines = FileUtil.listLinesInternalFile(NAME_PATH + "LastNames.txt");
        return (lines.get(rng.nextInt(lines.size())));
        
    }


    /**
     * Test to list out generated accounts
     */

    public static void main(String[] args) {
        while (true) {
            System.out.println(generateAccount());
        }

    }
    
}
