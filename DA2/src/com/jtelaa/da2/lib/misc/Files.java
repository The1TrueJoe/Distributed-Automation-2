package com.jtelaa.da2.lib.misc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jtelaa.da2.lib.log.Log;

public class Files {

    public static String readInternalFile(String path) {
        try {
            InputStream inputStream = ClassLoader.getSystemClassLoader().getSystemResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader in = new BufferedReader(inputStreamReader);

            String out = "";
            for (String line; (line = in.readLine()) != null;) {
                out += line + "\n";

            }

            return out;

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            
            return "";
        }
    }

}