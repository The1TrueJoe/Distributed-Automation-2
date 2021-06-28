package com.jtelaa.da2.lib.misc;

import com.jtelaa.da2.lib.log.Log;

public class MiscUtil {

    public static boolean isNumeric(String value) {
        if (value == null || value.equals("")) { return false; }
        
        try {
            Integer.parseInt(value);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean notBlank(String text) {
        if (text != null && !text.equals("") && !text.equals(" ")) {
            return true;

        } else {
            return false;
            
        }
    }

    public static void waitasec() {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            Log.sendMessage(e.getMessage());
            
        }
    }

    public static void waitasec(double frac) {
        try {
            Thread.sleep((long)(1000*frac));

        } catch (InterruptedException e) {
            Log.sendMessage(e.getMessage());
            
        }
    }
}
