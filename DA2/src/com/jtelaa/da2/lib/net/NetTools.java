package com.jtelaa.da2.lib.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;

import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Misc networking tools
 * 
 * @since 2
 * @author Joseph
 * 
 */

// TODO comment

public class NetTools {
    
    /**
     * Gets internal ip address
     * 
     * @return IP
     */

    public static String getLocalIP() {
        try {
            // Get intenal ip
            return InetAddress.getLocalHost().getHostAddress();

        } catch (Exception e) {
            // Send error to log
            Log.sendMessage(e, ConsoleColors.RED);

            // Return loopback address
            return "127.0.0.1";

        }
    }

    /**
     * Get local subnet mask 
     * 
     * @return subnet in form (x.x.x.x)
     */

    public static String getSubnet() {
        try {
            // Look at network interface
            InetAddress localHost = Inet4Address.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
        
            // Get subnet (/x)
            int subnet = networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();

            // TODO support more subnets
            // Get subnet (x.x.x.x)
            switch (subnet) {
                case (24): return "255.255.255.0";
                case (16): return "255.255.0.0";
                case (8): return "255.0.0.0";
                default: return "255.0.0.0";

            }

        } catch (Exception e) {
            // Send error to log
            Log.sendMessage(e, ConsoleColors.RED);

            // Return loopback address
            return "255.0.0.0";

        }
    }

    /**
     * Check external ip address
     * 
     * @return external ip address
     */

    public static String getExternalIP() {
        try {
            // Use Amazon AWS
            URL whatismyip = new URL("http://checkip.amazonaws.com");

            // Read results
            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
            return in.readLine();

        } catch (Exception e) {
            // Send error to log
            Log.sendMessage(e, ConsoleColors.RED);

            // Return local ip
            return getLocalIP();

        }
    }

    /**
     * Check external ip address
     * 
     * @param url Url of franchise server
     * 
     * @return external ip address
     */

    public static int getFranchise(String url) {
        try {
            // Use Amazon AWS
            URL franchise_server = new URL(url);

            // Read results
            BufferedReader in = new BufferedReader(new InputStreamReader(franchise_server.openStream()));
            return Integer.parseInt(in.readLine());

        } catch (Exception e) {
            // Send error to log
            Log.sendManSysMessage(e.getMessage(), ConsoleColors.RED);

            // Return local ip
            return 0;

        }
    }

    /**
     * Set a new default gateway
     * 
     * @param default_gateway New default gateway
     */

    public static void setDefaultGateway(String default_gateway) { 
        // If linux
        if (ComputerControl.getOS().equalsIgnoreCase("linux")) {
            // Clear default gateway
            ComputerControl.sendCommand("sudo route delete default gw 10.0.2.2 eth0");

            // Set new defult gateway
            ComputerControl.sendCommand("sudo route delete default gw " + default_gateway + " eth0");

        } else if (ComputerControl.getOS().equalsIgnoreCase("windows")) {
            // Get internal ip & info
            String internal_ip = getLocalIP();
            String net_mask = getSubnet();

            // Set static ip w/ new gateway
            ComputerControl.sendCommand("netsh int ip set address \"local area connection\" static " + internal_ip + " " + net_mask + " " + default_gateway);

        }
    }

    /**
     * Checks if address is valid (Assumes valid if exception thrown)
     * 
     * @param address IP address to check
     * 
     * @return address validity
     */

    public static boolean isValid(String address) {
        try {
            // Validate address
            InetAddressValidator valid = new InetAddressValidator();
            return valid.isValid(address);

        } catch (NoClassDefFoundError x) {
            Log.sendMessage("InetAddressValidator class not found", ConsoleColors.RED);
            return true;
            // TODO Solve so this wont happen

        } catch (Exception e) {
            Log.sendMessage(e, ConsoleColors.RED);
            return true;

        }
        
    }

    /**
     * Check if a host is alive by pinging it
     * 
     * @param ip IP to ping
     * 
     * @return life status
     */

    public static boolean isAlive(String ip) {
        try {
            // Try pinging address
            if (isValid(ip)) {
                return InetAddress.getByName(ip).isReachable(100);

            }

        // Send error
        } catch (IOException e) {
            Log.sendMessage(e, ConsoleColors.RED);

        }

        // Default return
        return false;
        
    }

}
