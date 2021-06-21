package com.jtelaa.da2.lib.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ConfigTools {
    
    public static String getOS() { return System.getProperty("os.name"); }

    public static String getExternalIP() throws MalformedURLException, IOException { return new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())).readLine(); }

}
