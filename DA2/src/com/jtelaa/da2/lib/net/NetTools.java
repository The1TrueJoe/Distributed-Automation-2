package com.jtelaa.da2.lib.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import org.apache.commons.validator.routines.InetAddressValidator;

public class NetTools {
    
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().toString();

        } catch (Exception e) {
            return e.getMessage();

        }
    }

    public static String getExternalIP() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            return in.readLine();

        } catch (Exception e) {
            return e.getMessage();

        }
    }

    public static boolean isValid(String address) {
        InetAddressValidator valid = new InetAddressValidator();
        return valid.isValid(address);
        
    }

}
