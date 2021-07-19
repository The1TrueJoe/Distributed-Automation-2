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
        if (command.command(0).equals("exit") || command.command(0).equals("end")) {
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
        if (command.command(0).equals("command") || command.command(0).equals("cmd") || command.command(0).equals("-cmd")) {
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
        if (command.command(0).equals("shutdown") || command.command(0).equals("shut") || command.command(0).equals("off") || command.command(0).equals("-off")) {
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
        if (command.command(0).equals("help") || command.command(0).equals("-h") || command.command(0).equals("-?")) {
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
        if (command.command(0).equals("director") || command.command(0).equals("direc") || command.command(0).equals("d") || command.command(0).equals("-d")) {
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
        if (command.command(0).equals("hypervisor") || command.command(0).equals("hyp") || command.command(0).equals("-hyp")) {
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
        if (command.command(0).equals("scheduler") || command.command(0).equals("sched")) {
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
        if (command.command(0).equals("querygen") || command.command(0).equals("gen") || command.command(0).equals("qry")) {
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
        if (command.command(0).equals("bot") || command.command(0).equals("bt")) {
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
        if (command.command(0).equals("plugin") || command.command(0).equals("plug")) {
            return true;
        } else {
            return false;
        }
    }

}
