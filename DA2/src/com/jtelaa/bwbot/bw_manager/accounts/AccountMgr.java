package com.jtelaa.bwbot.bw_manager.accounts;

import java.io.File;
import java.util.ArrayList;

import com.jtelaa.bwbot.bwlib.Account;
import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.log.Log;

/**
 * 
 */

// TODO Commment

public class AccountMgr {

    private volatile static ArrayList<Account> accounts;

    public volatile static String accounts_path;




    /**
     * Load accounts from a file with standardized bath
     * 
     * <p> Ex loadAccouts('a') -> file_path = -/Accounta.txt
     * 
     * @param starting_character First character of the last name
     */

    public static synchronized void loadAccounts(char starting_character) { loadAccounts(new File(accounts_path + "/Account"+starting_character+".txt"));}

    /**
     * Load accounts from file at a given path
     * 
     * @param path Path for accounts file
     */

    public static synchronized void loadAccounts(String path) { loadAccounts(new File(path)); }

    /**
     * Load accounts from a file
     * 
     * @param file File to load from
     */

    public static synchronized void loadAccounts(File file) {
        // Read file
        ArrayList<Object> lines = FileUtil.readObjectFromFile(file);

        // Check if lines contain a account object
        if (!Account.class.isInstance(lines.get(0))) {
            Log.sendMessage(new Exception("File does not contain Account objects!"));
        }

        // Reset loaded accounts
        accounts = new ArrayList<Account>();

        // Load and cast all lines as accounts
        for (Object line : lines) {
            accounts.add((Account) line);
            
        }

    }

    /**
     * Checks if the account exists 
     * 
     * @param email Email to look for
     * 
     * @return Existance
     */

    public static synchronized boolean accountExistsByEmail(String email) { return getByEmail(email).isBlank(); }

    /**
     * Checks if the account exists 
     * 
     * @param user Username to look for
     * 
     * @return Existance
     */

    public static synchronized boolean accountExistsByUsername(String user) { return getByUser(user).isBlank(); }

    /**
     * Checks if the account exists 
     * 
     * @param account Account to look for
     * 
     * @return Existance
     */

    public static synchronized boolean accountExistsByName(Account account) { return getByName(account).isBlank(); }

    /**
     * Checks if the account exists
     * 
     * @param first_name First name to search
     * @param last_name Last name to search
     * 
     * @return Existance
     */

    public static synchronized boolean accountExistsByName(String first_name, String last_name) { return getByName(first_name, last_name).isBlank(); }

    /**
     * Gets the account info from the server storage
     * 
     * @param account
     * 
     * @return
     */

    public static synchronized Account getByName(Account account) { return getByName(account.getFirstName(), account.getLastName()); }

    /**
     * Get an account by the accounts name
     * 
     * @param first_name First name of user
     * @param last_name Last name of user
     * 
     * @return The account (If cannot find, looks for a similar one)
     */

    public static synchronized Account getByName(String first_name, String last_name) {
        // Checks if account info is currently stored in memmory 
        if (accounts.get(0).getLastName().charAt(0) != last_name.charAt(0)) {
            loadAccounts(last_name.charAt(0));
        }

        // Find account with same name
        for (Account account : accounts) {
            if (account.getLastName().equalsIgnoreCase(last_name) &&
                account.getFirstName().equalsIgnoreCase(first_name)) {

                return account;
                
            }
        }

        // Get a similar account if nothing else found
        for (Account account : accounts) {
            if (account.getFirstName().equalsIgnoreCase(first_name)) {
                return account;
                
            }
        }

        // Get a similar account if nothing else found
        for (Account account : accounts) {
            if (account.getLastName().equalsIgnoreCase(first_name)) {
                return account;
                
            }
        }

        // Return a default
        return new Account();

    }

    /**
     * Look for an account by username
     * 
     * @param user username to look for
     * 
     * @return Account associated
     */

    public static synchronized Account getByUser(String user) {
        // Find account with same username
        for (Account account : accounts) {
            if (account.getUsername().equalsIgnoreCase(user)) {
                return account;
                
            }
        }

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        // Check all files
        for (char character : alphabet) {
            loadAccounts(character);

            // Find account with same username
            for (Account account : accounts) {
                if (account.getUsername().equalsIgnoreCase(user)) {
                    return account;
                
                }
            }

        }

        // Return a default
        return new Account();

    }

    /**
     * Look for an account by email
     * 
     * @param email Email to look for
     * 
     * @return Account associated
     */

    public static synchronized Account getByEmail(String email) {
        // Find account with same email
        for (Account account : accounts) {
            if (account.getEmail().equalsIgnoreCase(email)) {
                return account;
                
            }
        }

        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        // Check all files
        for (char character : alphabet) {
            loadAccounts(character);

            // Find account with same email
            for (Account account : accounts) {
                if (account.getEmail().equalsIgnoreCase(email)) {
                    return account;
                
                }
            }

        }

        // Return a default
        return new Account();

    }

    
    
}
