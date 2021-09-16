package com.jtelaa.da2.lib.user.local;

import java.io.Serializable;

import com.jtelaa.da2.lib.crypto.EncryptionUtils;
import com.jtelaa.da2.lib.crypto.SystemCrypto;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.mail.MessageGateways;

/**
 * Local user object to login to cli
 * 
 * @author Joseph
 * @since 2
 */

public class User implements Serializable {

    //default serialVersion id
    private static final long serialVersionUID = 1L;

    /** Name of the user */
    public String name;

    /** Password of the user */
    public String password;

    /** Mailing address of the user */
    public String mail;

    /** Is the password encrypted */
    public boolean password_encrypted;

    /**
     * Constructor
     * 
     * @param name Name of the user
     * @param password Password of the user
     * @param password_encrypted Is the password encrypted
     */

    @Deprecated
    public User(String name, String password, boolean password_encrypted) {
        this(name, password, "704-351-7396" + MessageGateways.VERIZON.getMMSAddress(), password_encrypted);

    }

    /**
     * Contructor
     * 
     * @param name Name of the user
     * @param password Password of the user. Assumes the password is not encrypted
     * @param mail Mailing address of the user
     */

    public User(String name, String password, String mail) {
        this(name, password, mail, false);

    }

    /**
     * Constructor
     * 
     * @param name Name of the user
     * @param password Password of the user
     * @param mail Mailing address of the user
     * @param password_encrypted Is the password encrypted
     */

    public User(String name, String password, String mail, boolean password_encrypted) {
        // Set 
        this.name = name;
        this.password_encrypted = password_encrypted;

        // Encrypt the password
        if (password_encrypted) {
            // If already encrypted
            this.password = password;

        } else {
            try {
                // Encrypt
                this.password = EncryptionUtils.encrypt(SystemCrypto.algorithm, password, SystemCrypto.key, SystemCrypto.iv);
            
            } catch (Exception e) {
                // Send error
                Log.sendMessage(e.getMessage());

            }
        }
    }

    // Ex: {User: oof, Password: test1234}

    /**
     * Import user from string
     * 
     * @param user User string
     */

    @Deprecated
    public User(String user) {
        // Set indexes
        int user_index_st = user.indexOf("{User: ") + (("{User: ").length());
        int user_index_end = user.indexOf(", Password: ");
        int pswd_index_st = user_index_end + (", Password: ").length();

        // Get name
        name = user.substring(user_index_st, user_index_end);
        password = user.substring(pswd_index_st, user.length()-1);
    }

    /**
     * To String
     */

    public String toString() { return "{User: " + name + ", Password: " + password + "}"; }
    
}
