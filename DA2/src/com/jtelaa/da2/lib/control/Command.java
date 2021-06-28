package com.jtelaa.da2.lib.control;

import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * Outlines the required parameter for a command
 * 
 * @since 2
 * @author Joseph
 */

public class Command {
    
    private String command;
    private String dest_ip;
    private String org_ip;
    
    private boolean isHeadless;

    public Command(String command) {
        this.command = command;
        
        dest_ip = "127.0.0.1";
        org_ip = NetTools.getLocalIP();
        isHeadless = true;

    }

    public Command(String command, String dest_ip) {
        this.command = command;
        this.dest_ip = dest_ip;

        org_ip = NetTools.getLocalIP();
        isHeadless = true;

    }

    public Command(String command, String dest_ip, boolean isHeadless) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.isHeadless = isHeadless;

        org_ip = NetTools.getLocalIP();

    }

    public Command(String command, String dest_ip, String org_ip) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.org_ip = org_ip;

        org_ip = NetTools.getLocalIP();
        isHeadless = true;

    }

    public Command(String command, String dest_ip, String org_ip, boolean isHeadless) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.org_ip = org_ip;
        this.isHeadless = isHeadless;

        org_ip = NetTools.getLocalIP();

    }

    public boolean isValid() { 
        return (
            MiscUtil.notBlank(command) 
            && InetAddressValidator.isValid(dest_ip)
            && InetAddressValidator.isValid(org_ip)
        ); 
    }

    public Command[] split(String regex) { 
        String[] tmps = command.split(regex);
        Command[] cmds = new Command[tmps.length]; 

        for (int i = 0; i < tmps.length; i++) {
            cmds[i] = new Command(dest_ip, tmps[i]);
            
        }

        return cmds;

    }

    public String command() { return command; }
    public String destination() { return dest_ip; }
    public String origin() { return org_ip; }
    public boolean isHeadless() { return isHeadless; }

    public String toString() { return "[{From: " + org_ip + ", To: " + dest_ip + "} -> " + command + "]"; }

}
