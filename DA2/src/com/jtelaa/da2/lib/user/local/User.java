package com.jtelaa.da2.lib.user.local;

import java.io.Serializable;

import com.jtelaa.da2.lib.crypto.EncryptionUtils;
import com.jtelaa.da2.lib.crypto.SystemCrypto;
import com.jtelaa.da2.lib.log.Log;

// TODO comment

public class User implements Serializable {

    //default serialVersion id
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;

    public User(String name, String password, boolean password_encrypted) {
        this.name = name;

        if (password_encrypted) {
            this.password = password;

        } else {
            try {
                this.password = EncryptionUtils.encrypt(SystemCrypto.algorithm, password, SystemCrypto.key, SystemCrypto.iv);
            
            } catch (Exception e) {
                Log.sendMessage(e.getMessage());

            }
        }
    }

    // Ex: {User: oof, Password: test1234}
    public User(String user) {
        int user_index_st = user.indexOf("{User: ") + (("{User: ").length());
        int user_index_end = user.indexOf(", Password: ");
        int pswd_index_st = user_index_end + (", Password: ").length();

        name = user.substring(user_index_st, user_index_end);
        password = user.substring(pswd_index_st, user.length()-1);
    }
    
    public String getName() { return name; }
    public String getPassword() { return password; }

    public String toString() { return "{User: " + name + ", Password: " + password + "}"; }
    
}
