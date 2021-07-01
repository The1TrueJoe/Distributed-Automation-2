package com.jtelaa.da2.lib.control;

import java.io.Serializable;

import com.jtelaa.da2.director.cli.Cases;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;

/**
 * Outlines the required parameter for a command
 * 
 * @since 2
 * @author Joseph
 */

public class Command implements Serializable {

    // Command Structure
    public static final int USER = 0;
    public static final int SYSTEM = 1;
    public static final int COMMAND = 2;
    public static final int ARG = 3;
    public static final int ARG2 = 4;
    public static final int ARG3 = 5;

    private Command[] split;
    
    private String command;
    private String dest_ip;
    private String org_ip;
    
    private boolean isHeadless;

    public Command(String command) {
        this.command = command;
        
        dest_ip = "127.0.0.1";
        org_ip = NetTools.getLocalIP();
        isHeadless = true;

        split = split(" ");

    }

    public Command(String command, String dest_ip) {
        this.command = command;
        this.dest_ip = dest_ip;

        org_ip = NetTools.getLocalIP();
        isHeadless = true;

        split = split(" ");

    }

    public Command(String command, String dest_ip, boolean isHeadless) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.isHeadless = isHeadless;

        org_ip = NetTools.getLocalIP();

        split = split(" ");

    }

    public Command(String command, String dest_ip, String org_ip) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.org_ip = org_ip;

        isHeadless = true;

        split = split(" ");

    }

    public Command(Command command, String new_command) {
        this.command = new_command;
        this.dest_ip = command.destination();
        this.org_ip = command.origin();

        split = split(" ");

    }

    public Command(String command, String dest_ip, String org_ip, boolean isHeadless) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.org_ip = org_ip;
        this.isHeadless = isHeadless;

        split = split(" ");

    }

    /**
     * Checks if the command is valid
     * 
     * @return Command validitiy
     */

    public boolean isValid() { 
        return (
            MiscUtil.notBlank(command) 
            && NetTools.isValid(dest_ip)
            && NetTools.isValid(org_ip)
        ); 
    }

    /**
     * Splits the command down into an array of core modifiers
     * 
     * @return array of individual commands
     */

    public Command[] split(String regex) { 
        String[] tmps = command.split(regex);
        Command[] cmds = new Command[tmps.length]; 

        for (int i = 0; i < tmps.length; i++) {
            cmds[i] = new Command(tmps[i], dest_ip, org_ip, isHeadless);
            
        }

        return cmds;

    }

    /**
     * Gets the command modifier
     * 
     * @return modifier
     */

    public Command getModifier(int index) {
        return split[index];
    }

    /**
     * Modifies the command for execution by the local system
     * <p> (Removes cmd modifiers and modifiers before it)
     * 
     * @return Modified command
     */

    public Command modifyforSys() {
        String cmd = this.command;
        String[] tmp_split = cmd.split(" ");

        for (String tmp : tmp_split) {
            if (Cases.command(new Command(this, tmp))) {
                cmd = cmd.substring(cmd.indexOf(tmp + (tmp.length() - 1)));
                return new Command(this, cmd);
            }
        }

        return this;
    }

    public String command() { return command; }
    public String destination() { return dest_ip; }
    public String origin() { return org_ip; }
    public boolean isHeadless() { return isHeadless; }

    public String toString() { return "[{From: " + org_ip + ", To: " + dest_ip + "} -> " + command + "]"; }

}
