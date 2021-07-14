package com.jtelaa.da2.lib.misc;

import java.util.Random;

import com.jtelaa.da2.lib.log.Log;

// TODO comment

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

    public static void waitasec() { waitamoment(1000); }

    public static void waitasec(double frac) { waitamoment(1000*frac); }

    public static void waitamoment(double time) {
        try {
            Thread.sleep((long) time);

        } catch (InterruptedException e) {
            Log.sendMessage(e.getMessage());
            
        }
    }

    public static void randWait(double limit, double tolerance) {
        Random rand = new Random();
        double wait = 1000;

        do {
            wait = rand.nextInt((int) (limit + (limit * tolerance)));

        } while (wait != (limit - (limit * tolerance)));

        waitamoment(wait);
    }
}
