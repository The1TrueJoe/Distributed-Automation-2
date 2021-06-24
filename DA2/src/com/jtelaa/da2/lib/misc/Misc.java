package com.jtelaa.da2.lib.misc;

public class Misc {

    public static boolean isNumeric(String value) {
        if (value == null || value.equals("")) { return false; }
        
        try {
            Integer.parseInt(value);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
