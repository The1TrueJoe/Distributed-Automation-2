package com.jtelaa.da2.lib.control;

import java.io.Serializable;
import java.util.ArrayList;

import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.files.FileUtil;
import com.jtelaa.da2.lib.misc.MiscUtil;
import com.jtelaa.da2.lib.net.NetTools;
/**
 * Outlines the required parameter for a command
 * 
 * @since 2
 * @author Joseph
 */

 // TODO comment

public class Command implements Serializable {

    // Command Structure
    /** Index of the user key in the command */
    public static final int USER = 0;

    /** Index of the command key in the command (For the local CLI) */
    public static final int LOCAL_COMMAND = 1;
    /** Index of the system control tag in the command (For the external mgmt) */
    public static final int CONTROL_ID = 1;

    /** Index of the command key in the command (For the external mgmt) */
    public static final int SYSTEM_COMMAND = 2;
    /** Index of the argument key in the command (For the local CLI) */
    public static final int LOCAL_ARG1 = 2;
    
    /** Index of the argument key in the command (For the external mgmt) */
    public static final int ARG_1 = 3;
    /** Index of the argument key in the command (For the local CLI) */
    public static final int LOCAL_ARG2 = 3;

    /** Index of the argument key in the command (For the external mgmt) */
    public static final int ARG_2 = 4;
    /** Index of the argument key in the command (For the local CLI) */
    public static final int LOCAL_ARG3 = 4;

    /** Index of the argument key in the command (For the external mgmt) */
    public static final int ARG_3 = 5;
    
    private String command;
    private String dest_ip;
    private String org_ip;
    
    private boolean isHeadless;

    /**
     * 
     * @param command
     */

    public Command(String command) {
        this.command = command;
        
        dest_ip = "127.0.0.1";
        org_ip = NetTools.getLocalIP();
        isHeadless = true;

    }

    /**
     * 
     * @param command
     * @param dest_ip
     */

    public Command(String command, String dest_ip) {
        this.command = command;
        this.dest_ip = dest_ip;

        org_ip = NetTools.getLocalIP();
        isHeadless = true;

    }

    /**
     * 
     * @param command
     * @param dest_ip
     * @param isHeadless
     */

    public Command(String command, String dest_ip, boolean isHeadless) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.isHeadless = isHeadless;

        org_ip = NetTools.getLocalIP();

    }

    /**
     * 
     * @param command
     * @param dest_ip
     * @param org_ip
     */

    public Command(String command, String dest_ip, String org_ip) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.org_ip = org_ip;

        isHeadless = true;

    }

    /**
     * 
     * @param command
     * @param new_command
     */

    public Command(Command command, String new_command) {
        this.command = new_command;
        this.dest_ip = command.destination();
        this.org_ip = command.origin();

    }

    /**
     * 
     * @param command
     * @param dest_ip
     * @param org_ip
     * @param isHeadless
     */

    public Command(String command, String dest_ip, String org_ip, boolean isHeadless) {
        this.command = command;
        this.dest_ip = dest_ip;
        this.org_ip = org_ip;
        this.isHeadless = isHeadless;

    }

    /**
     * 
     * @param message
     * @return
     */

    public boolean equals(MgmtMessages message) {
        return equals(message.getMessage());

    }

    /**
     * 
     * @param other_command
     * @return
     */

    public boolean equals(Command other_command) {
        return equals(other_command.command());

    }

    /**
     * 
     * @param command
     * @return
     */

    public boolean equals(String command) {
        return this.command.equalsIgnoreCase(command);

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
        if (command.equals(regex) || !MiscUtil.notBlank(command)) { return new Command[] {new Command("")}; }

        String[] tmps = command.split(regex);
        Command[] cmds = new Command[tmps.length]; 

        for (int i = 0; i < tmps.length; i++) {
            cmds[i] = new Command(tmps[i], dest_ip, org_ip, isHeadless);
            
        }

        return cmds;

    }

    /**
     * Basically substring for command
     * <p> Gets the arguments between indexes
     * 
     * @param begin_index Starting index (inclusive)
     * 
     * @return Command array
     */

    public Command[] getSubCommands(int begin_index) { return getSubCommands(begin_index, command.split(" ").length); }

    /**
     * Basically substring for command
     * <p> Gets the arguments between indexes
     * 
     * @param begin_index Starting index (inclusive)
     * @param regex Regex
     * 
     * @return Command array
     */
    
    public Command[] getSubCommands(int begin_index, String regex) { return getSubCommands(begin_index, command.split(regex).length, regex); }

    /**
     * Basically substring for command
     * <p> Gets the arguments between indexes
     * 
     * @param begin_index Starting index (inclusive)
     * @param end_index Ending index (excusive)
     * 
     * @return Command array
     */

    public Command[] getSubCommands(int begin_index, int end_index) { return getSubCommands(begin_index, end_index, " "); }

    /**
     * Basically substring for command
     * <p> Gets the arguments between indexes
     * 
     * @param begin_index Starting index (inclusive)
     * @param end_index Ending index (excusive)
     * @param regex Regex
     * 
     * @return Command array
     */

    public Command[] getSubCommands(int begin_index, int end_index, String regex) {
        Command[] commands = split(regex);
        if (commands.length == 1) { return commands; }

        int size = end_index - begin_index;
        if (size < 0) { size = 0; }

        Command[] sub_commands = new Command[size];

        int x = 0;
        for (int i = 0; i < commands.length; i++) {
            if (i >= begin_index && i < end_index) {
                sub_commands[x] = commands[i];
                x++;

            }
        }

        return sub_commands;
    }

    /**
     * Gets the command modifier
     * 
     * @return modifier
     */

    public Command getModifier(int index) {
        return split(" ")[index];
    }

    /**
     * Add a blank user the the command
     * 
     * @return new command
     */

    public Command addBlankUser() {
        return new Command("user" + this.command);

    }

    /**
     * Add a blank control id to the command (Add user before hand if local cli)
     * 
     * @return new command
     * 
     * @see com.jtelaa.da2.lib.control.Command.addBlankUser();
     */

    public Command addBlankControlID() {
        return new Command("local" + this.command);
    }

    /**
     * Modifies the command for execution by the local system
     * <p> (Removes cmd modifiers and modifiers before it)
     * 
     * @return Modified command
     */

    public Command modifyforSys() {
        Command[] split = split(" ");
        int start_index = Command.SYSTEM_COMMAND;

        if (!Cases.command(split[start_index])) {
            for (int i = 0; i < split.length; i++) {
                if (Cases.command(split[i])) {
                    start_index = i;

                }
            }
        }

        Command[] sys_commands = getSubCommands(start_index);
        String new_command = "";

        for (Command sys_command : sys_commands) {
            new_command += sys_command + " ";

        }

        new_command = new_command.substring(0, new_command.length()-1);
        return new Command(new_command);
        
    }

    /**
     * Alters the command with a new message
     * 
     * @param command Command to change to
     * 
     * @return This command with previous command's parameters
     */

    public Command changeCommand(String command) {
        this.command = command;
        return this;

    }

    /**
     * Alters the destination ip with a destination ip
     * 
     * @param dest_ip Destination ip to change to
     * 
     * @return This command with previous command's parameters
     */

    public Command changeDestination(String dest_ip) {
        this.dest_ip = dest_ip;
        return this;

    }

    /**
     * Sets the command so it forwards back to the previous destination
     */

    public void forward() {
        dest_ip = org_ip;
        org_ip = NetTools.getLocalIP();

    }

    /**
     * Loads a list of commands from a file
     * 
     * @param path File path
     * 
     * @return An array of commands
     */

    public static Command[] loadfromfile(String path) { return loadfromfile(path, false); }

    /**
     * Loads a list of commands from a file
     * 
     * @param path File path
     * @param internal Whether or not the file is internal
     * 
     * @return An array of commands
     */

    public static Command[] loadfromfile(String path, boolean internal) {
        ArrayList<String> lines;

        if (internal) {
            lines = FileUtil.listLinesInternalFile(path);

        } else {
            lines = FileUtil.listLinesFile(path);
            
        }

        ArrayList<Command> commands = new ArrayList<Command>();

        for (String line : lines) {
            commands.add(new Command(line));

        }

        return (Command[]) commands.toArray();

    }

    /**
     * 
     * @return
     */
    public String command() { return command; }

    /**
     * @param index
     * 
     * @return
     */
    public Command command(int index) { return split(" ")[index]; }

    /**
     * 
     * @return
     */
    public String destination() { return dest_ip; }

    /**
     * 
     * @return
     */
    public String origin() { return org_ip; }

    /**
     * 
     * @return
     */
    public boolean isHeadless() { return isHeadless; }

    /**
     * Converts an array of commands into an array of strings
     * 
     * @param commands Command array
     * 
     * @return Array of commands
     */

    public static String[] toString(Command[] commands) {
        String[] str = new String[commands.length];

        for (int i = 0; i < str.length; i++) {
            str[i] = commands[i].command();
        }

        return str;
    }

    public String toString() { return "[{From: " + org_ip + ", To: " + dest_ip + "} -> " + command + "]"; }

}
