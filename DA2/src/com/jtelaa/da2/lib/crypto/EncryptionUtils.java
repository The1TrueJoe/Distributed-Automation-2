package com.jtelaa.da2.lib.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utilities class for encrypting and decrypting data
 * 
 * @since 2
 * @author Joseph
 * @author Baeldung
 */

public class EncryptionUtils {

    /**
     * Generates a secret AES key
     * 
     * @param n Number of bits (128, 192, 256)
     * 
     * @return Key
     * 
     * @throws NoSuchAlgorithmException
     */

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);

        SecretKey key = keyGenerator.generateKey();

        return key;
        
    }

    /**
     * Turns a password into a secret key
     * 
     * @param password Password to turn into a secret
     * @param salt Salt value (random)
     * 
     * @return Secret Key
     * 
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */

    public static SecretKey getKeyFromPassword(String password, String salt)
        throws NoSuchAlgorithmException, InvalidKeySpecException {
    
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        
        return secret;

    }

    /**
     * Creates the IV
     * 
     * @return IV
     */

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];

        new SecureRandom().nextBytes(iv);
    
        return new IvParameterSpec(iv);
    
    }

    /**
     * Encrypts a string
     * 
     * @param algorithm Algorithm to use
     * @param input Input String
     * @param key Key to use
     * @param iv IV to use
     * 
     * @return Encrypted string
     * 
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */

    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv) 
        throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        
        return Base64.getEncoder().encodeToString(cipherText);

    } 

    /**
     * Decrypts a string
     * 
     * @param algorithm Algorithm used
     * @param cipherText Encrypted String
     * @param key Key used
     * @param iv IV used
     * 
     * @return Decrypted String
     * 
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */

    public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv) 
        throws NoSuchPaddingException, NoSuchAlgorithmException,
        InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        
        return new String(plainText);

    }

    /*

    private static void test()
        throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
        BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException { 
    
        String input = "EncryptionUtils";
        
        SecretKey key = EncryptionUtils.generateKey(128);
        IvParameterSpec ivParameterSpec = EncryptionUtils.generateIv();
        
        String algorithm = "AES/CBC/PKCS5Padding";
        String cipherText = EncryptionUtils.encrypt(algorithm, input, key, ivParameterSpec);
        String plainText = EncryptionUtils.decrypt(algorithm, cipherText, key, ivParameterSpec);

    }

    */

    /**
     * Encrypts a file
     * 
     * @param algorithm Algorithm to use
     * @param key Key to use
     * @param iv IV to use
     * @param inputFile Input file
     * @param outputFile Output file
     * 
     * @throws IOException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */

    public static void encryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile, File outputFile) 
        throws IOException, NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        byte[] buffer = new byte[64];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byte[] output = cipher.update(buffer, 0, bytesRead);
            
            if (output != null) {
               outputStream.write(output);
            
            } 
        }
        
        byte[] outputBytes = cipher.doFinal();
        
        if (outputBytes != null) {
            outputStream.write(outputBytes);
        
        }
        
        inputStream.close();
        outputStream.close();
    }

    /*

    void givenFile_whenEncrypt_thenSuccess() 
        throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, 
        InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, 
        NoSuchPaddingException {
    
        SecretKey key = AESUtil.generateKey(128);
        String algorithm = "AES/CBC/PKCS5Padding";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        Resource resource = new ClassPathResource("inputFile/baeldung.txt");
        
        File inputFile = resource.getFile();
        File encryptedFile = new File("classpath:baeldung.encrypted");
        File decryptedFile = new File("document.decrypted");
        
        EncryptionUtils.encryptFile(algorithm, key, ivParameterSpec, inputFile, encryptedFile);
        EncryptionUtils.decryptFile(algorithm, key, ivParameterSpec, encryptedFile, decryptedFile);
    
    }

    */

    /*

    @Test
    void givenPassword_whenEncrypt_thenSuccess() 
        throws InvalidKeySpecException, NoSuchAlgorithmException, 
        IllegalBlockSizeException, InvalidKeyException, BadPaddingException, 
        InvalidAlgorithmParameterException, NoSuchPaddingException {
    
        String plainText = "www.baeldung.com";
        String password = "baeldung";
    
        String salt = "12345678";
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        SecretKey key = AESUtil.getKeyFromPassword(password,salt);
        String cipherText = AESUtil.encryptPasswordBased(plainText, key, ivParameterSpec);
        String decryptedCipherText = AESUtil.decryptPasswordBased(cipherText, key, ivParameterSpec);
        
    }

    */

    /**
     * Encrypts an object
     * 
     * @param algorithm Algorithm to use
     * @param object Object to encrypt
     * @param key Key to use
     * @param iv IV
     * 
     * @return Encrypted object
     * 
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws IllegalBlockSizeException
     */

    public static SealedObject encryptObject(String algorithm, Serializable object, SecretKey key, IvParameterSpec iv) 
        throws NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
        InvalidKeyException, IOException, IllegalBlockSizeException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        SealedObject sealedObject = new SealedObject(object, cipher);
    
        return sealedObject;

    }

    /**
     * Decrypts an object
     * 
     * @param algorithm Algorithm used
     * @param sealedObject Object to decrypt
     * @param key Key to use
     * @param iv IV to use
     * 
     * @return Object
     * 
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws ClassNotFoundException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws IOException
     */

    public static Serializable decryptObject(String algorithm, SealedObject sealedObject, SecretKey key, IvParameterSpec iv) 
        throws NoSuchPaddingException,
        NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
        ClassNotFoundException, BadPaddingException, IllegalBlockSizeException,
        IOException {
    
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        
        Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
        
        return unsealObject;

    }

    /*

    public class Student implements Serializable {
    private String name;
    private int age;

    }

    @Test
    void givenObject_whenEncrypt_thenSuccess() 
        throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
        InvalidAlgorithmParameterException, NoSuchPaddingException, IOException, 
        BadPaddingException, ClassNotFoundException {
    
        Student student = new Student("Baeldung", 20);

        SecretKey key = AESUtil.generateKey(128);
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        
        SealedObject sealedObject = AESUtil.encryptObject(algorithm, student, key, ivParameterSpec);
        Student object = (Student) AESUtil.decryptObject(algorithm, sealedObject, key, ivParameterSpec);
    
    }   

    */

}