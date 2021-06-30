package com.jtelaa.da2.lib.user;

import java.io.Serializable;

import com.jtelaa.da2.lib.crypto.EncryptionUtils;
import com.jtelaa.da2.lib.crypto.SystemCrypto;

public class User implements Serializable {

    private String name;
    private String password;

    public User(String name, String password, boolean password_encrypted) {
        this.name = name;

        if (password_encrypted) {
            this.password = password;

        } else {
            this.password = EncryptionUtils.encrypt(SystemCrypto.algorithm, password, SystemCrypto.key, SystemCrypto.iv);

        }

    }
    
    
}
