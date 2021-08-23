package com.jtelaa.da2.lib.cli;

import com.jtelaa.da2.lib.control.Command;

/**
 * Contains a list of command cases <p>
 * It is meant to clean up the cli and provide one place to check cases
 * 
 * @since 2
 * @author Joseph
 */

public class Cases {

     /**
     * Checks to see if the command matches a certain case
     * 
     * @param command Command to check
     * @param options Case to check against
     * 
     * @return if the command matches
     */

    public static boolean checkCase(Command command, String options) {
        return checkCase(command, new String[] { options });
    }

    /**
     * Checks to see if the command matches a certain case
     * 
     * @param command Command to check
     * @param options Cases to check against
     * 
     * @return if the command matches
     */

    public static boolean checkCase(Command command, String[] options) {
        // Sort through all options to see if command applues
        for (String option : options) {
            // If equal
            if (command.command().equalsIgnoreCase(option)) {
                return true;

            }
        }

        // Default
        return false;
    }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean exit(Command command) { return checkCase(command, new String[] {"exit", "end"}); }

    /**
     * Case for command (command, cmd or -cmd)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean command(Command command) { return checkCase(command, new String[] {"command", "cmd"}); }

    /**
     * Case for shutdown (shutdown, shut, off, or -off)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean shutdown(Command command) { return checkCase(command, new String[] {"shutdown", "shut", "off", "-off"}); }

    /**
     * Case for help (help, -h, or -?)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean help(Command command) { return checkCase(command, new String[] {"help", "-h", "-?"}); }

    /**
     * Case for director (director, direc, d, or -d)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean director(Command command) { return checkCase(command, new String[] {"director", "-d"}); }

    /**
     * Case for hypervisor (hypervisor, hyp, -hyp)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean hypervisor(Command command) { return checkCase(command, new String[] {"hypervisor", "-hyp"}); }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean scheduler(Command command) { return checkCase(command, new String[] {"scheduler", "-sch"}); }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean querygen(Command command) { return checkCase(command, new String[] {"querygen", "gen", "-gen"}); }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean bot(Command command) { return checkCase(command, new String[] {"bot", "-bot"}); }

    /**
     * Case for exit (exit or end)
     * 
     * @param command Command
     * 
     * @return if case is applicable
     */

    public static boolean plugin(Command command) { return checkCase(command, new String[] {"plugin", "-plugin"}); }

}
