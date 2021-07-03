package com.jtelaa.da2.lib.cli;

import com.jtelaa.da2.lib.control.Command;

/**
 * Contains a list of command cases <p>
 * It is meant to clean up the cli and provide one place to check cases
 * 
 * @since 2
 * @author Joseph
 */

 // TDOD comment

public class Cases {

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean exit(Command command) {
        if (command.command().equalsIgnoreCase("exit") || command.command().equalsIgnoreCase("end")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for command (command, cmd or -cmd)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean command(Command command) {
        if (command.command().equalsIgnoreCase("command") || command.command().equalsIgnoreCase("cmd") || command.command().equalsIgnoreCase("-cmd")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for shutdown (shutdown, shut, off, or -off)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean shutdown(Command command) {
        if (command.command().equalsIgnoreCase("shutdown") || command.command().equalsIgnoreCase("shut") || command.command().equalsIgnoreCase("off") || command.command().equalsIgnoreCase("-off")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for help (help, -h, or -?)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean help(Command command) {
        if (command.command().equalsIgnoreCase("help") || command.command().equalsIgnoreCase("-h") || command.command().equalsIgnoreCase("-?")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for director (director, direc, d, or -d)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean director(Command command) {
        if (command.command().equalsIgnoreCase("director") || command.command().equalsIgnoreCase("direc") || command.command().equalsIgnoreCase("d") || command.command().equalsIgnoreCase("-d")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for hypervisor (hypervisor, hyp, -hyp)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean hypervisor(Command command) {
        if (command.command().equalsIgnoreCase("hypervisor") || command.command().equalsIgnoreCase("hyp") || command.command().equalsIgnoreCase("-hyp")) {
            return true;
        } else {
            return false;
        }
    }

    // TODO Continue Commenting

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean scheduler(Command command) {
        if (command.command().equalsIgnoreCase("scheduler") || command.command().equalsIgnoreCase("sched")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean querygen(Command command) {
        if (command.command().equalsIgnoreCase("querygen") || command.command().equalsIgnoreCase("gen") || command.command().equalsIgnoreCase("qry")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean bot(Command command) {
        if (command.command().equalsIgnoreCase("bot") || command.command().equalsIgnoreCase("bt")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean plugin(Command command) {
        if (command.command().equalsIgnoreCase("plugin") || command.command().equalsIgnoreCase("plug")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean rewards_plugin(Command command) {
        if (command.command().equalsIgnoreCase("bing") || command.command().equalsIgnoreCase("rewards") || command.command().equalsIgnoreCase("rw")) {
            return true;
        } else {
            return false;
        }
    }
    
}
