package com.jtelaa.da2.lib.crypto;

import java.util.ArrayList;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.files.ObjectUtils;
import com.jtelaa.da2.lib.log.Log;

/**
 * Place to store the temp keys in memory
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class SystemCrypto {

    public volatile static String algorithm;
    public volatile static SecretKey key;
    public volatile static IvParameterSpec iv;

    /**
     * Serialize info into a file
     * 
     * @param path File path
     */

    public synchronized static void exportCryp(String path) {
        FileUtil.writeObjectToFile(new Object[] {"Algorithm: ", algorithm, "Key: ", key, "IV: ", iv}, path);
    }

    /**
     * Import info from a file
     * 
     * @param path
     */

    public synchronized static void importCryp(String path) {
        importCryp(FileUtil.listLinesFile(path));
    }

    /**
     * Import info from an internal file
     * 
     * @param path
     */

    public synchronized static void importInternalCryp(String path) {
        importCryp(FileUtil.listLinesInternalFile(path));
    }

     /**
     * Import info from a file
     * 
     * @param file_out lines of a file as an arraylist
     */

    public synchronized static void importCryp(ArrayList<String> file_out) {
        for (int i = 0; i < file_out.size(); i++) {
            if (file_out.get(i).contains("Algorithm: ")) {
                algorithm = file_out.get(i+1).substring(("Algorithm: ").length());

            } else if (file_out.get(i).contains("Key: ")) {
                try {
                    key = (SecretKey) ObjectUtils.objfromString(file_out.get(i+1).substring(("Key: ").length()));
                
                } catch (Exception e) {
                    Log.sendMessage(e.getMessage());

                }
                
            } else if (file_out.get(i).contains("IV: ")) {
                try {
                    iv = (IvParameterSpec) ObjectUtils.objfromString(file_out.get(i+1).substring(("IV: ").length()));
                
                } catch (Exception e) {
                    Log.sendMessage(e.getMessage());

                }
            }
        }
    }
    
}
