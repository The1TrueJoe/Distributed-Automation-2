package com.jtelaa.da2.lib.crypto;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * Place to store the temp keys in memory
 */

public class SystemCrypto {

    public volatile static String algorithm;
    public volatile static SecretKey key;
    public volatile static IvParameterSpec iv;
    
}
