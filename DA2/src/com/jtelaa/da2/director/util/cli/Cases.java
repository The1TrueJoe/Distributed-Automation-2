package com.jtelaa.da2.director.util.cli;

/**
 * Contains a list of command cases <p>
 * It is meant to clean up the cli and provide one place to check cases
 * 
 * @since 2
 * @author Joseph
 */

public class Cases {

    public static boolean exit(String command) {
        if (command.equalsIgnoreCase("exit") || command.equalsIgnoreCase("end")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean command(String command) {
        if (command.equalsIgnoreCase("command") || command.equalsIgnoreCase("cmd")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean shutdown(String command) {
        if (command.equalsIgnoreCase("shutdown") || command.equalsIgnoreCase("shut") || command.equalsIgnoreCase("off")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean help(String command) {
        if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("-h") || command.equalsIgnoreCase("-?")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean director(String command) {
        if (command.equalsIgnoreCase("director") || command.equalsIgnoreCase("direc") || command.equalsIgnoreCase("d")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hypervisor(String command) {
        if (command.equalsIgnoreCase("hypervisor") || command.equalsIgnoreCase("hyp")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean scheduler(String command) {
        if (command.equalsIgnoreCase("scheduler") || command.equalsIgnoreCase("sched")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean bot(String command) {
        if (command.equalsIgnoreCase("bot") || command.equalsIgnoreCase("bt")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean rewards_plugin(String command) {
        if (command.equalsIgnoreCase("bing") || command.equalsIgnoreCase("rewards") || command.equalsIgnoreCase("rw")) {
            return true;
        } else {
            return false;
        }
    }
    
}
