package com.jtelaa.da2.lib.files;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.jtelaa.da2.lib.log.Log;

/**
 * Utilities class for handling files
 * 
 * @since 2
 * @author Joseph
 */

public class Files {

    public static String readInternalFile(String path) {
        ArrayList<String> list = listLinesInternalFile(path);
        String out = "";

        for (String item : list) {
            out += item + "\n";

        }

        return out;

    }

    public static ArrayList<String> listLinesInternalFile(String path) {
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            @SuppressWarnings ({"all"})
            InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader in = new BufferedReader(inputStreamReader);

            for (String line; (line = in.readLine()) != null;) {
                list.add(line);

            }

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            
        }

        return list;
    }

    public static void writeLinesInternalFile(String[] lines, String path) {
        try {
            


        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            
        }
    }

}