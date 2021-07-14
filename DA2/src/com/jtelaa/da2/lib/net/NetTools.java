package com.jtelaa.da2.lib.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

import com.jtelaa.da2.lib.log.Log;

import org.apache.commons.validator.routines.InetAddressValidator;

// TODO comment

public class NetTools {
    
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().toString();

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            return "127.0.0.1";

        }
    }

    public static String getExternalIP() {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            return in.readLine();

        } catch (Exception e) {
            Log.sendMessage(e.getMessage());
            return getLocalIP();

        }
    }

    public static boolean isValid(String address) {
        InetAddressValidator valid = new InetAddressValidator();
        return valid.isValid(address);
        
    }

    public static boolean isAlive(String ip) {
        try {
            if (isValid(ip)) {
                return InetAddress.getByName(ip).isReachable(100);

            }
        } catch (IOException e) {
            Log.sendMessage(e.getMessage());

        }

        return false;
        
    }

}
