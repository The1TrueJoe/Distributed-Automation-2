package com.jtelaa.da2.bot.util;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.console.ConsoleBanners;
import com.jtelaa.da2.lib.console.ConsoleColors;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

public class RemoteCLI extends LocalCLI {

    private boolean run_as_local;

    public RemoteCLI() { run_as_local = false; }
    public RemoteCLI(boolean run_as_local) { this.run_as_local = run_as_local; }

    @Override
    public void run() {
        if (!run_as_local && Main.me.config.getProperty("remote_cli", "false").equalsIgnoreCase("true")) {
            Log.sendMessage("Preparing Remote CLI");

            while (!run) {
                MiscUtil.waitasec();

            }

            Log.sendMessage("Starting Local CLI");

            runRX();

        } else {
            MiscUtil.waitasec();

        }
    }

    @Override
    public synchronized String terminal(Command command) {
        Command[] commands = command.split(" ");
        Command cmd = commands[0];

        // Temp change verbosity
        boolean prev_app_verbosity = Log.app_verbose;
        boolean prev_log_verbosity = Log.log_verbose;
        Log.app_verbose = !run_as_local;
        Log.log_verbose = run_as_local;

        // Shutdown
        if (Cases.exit(cmd)) {
            run = false;
            return "Shutting Down CLI";

        // CMD
        } else if (Cases.command(cmd)) {
            ComputerControl.sendCommand(command.modifyforSys());

        } else if (Cases.checkCase(cmd, "title")) {
            Log.sendMessage(ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape());
            Log.sendMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));

        } else if (Cases.help(cmd)) {
            Log.sendMessage(ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape());
            Log.sendMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));

            String help = (
                "Bot CLI Help:\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "cmd" + ConsoleColors.RESET.getEscape() + " -> pass through a command to the systems OS\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "title" + ConsoleColors.RESET.getEscape() + " -> clears the screen and prints the title\n"
                
                );

            Log.sendMessage(help);

        }

        // Reset
        Log.app_verbose = prev_app_verbosity;
        Log.log_verbose = prev_log_verbosity;

        // Default
        Log.sendLogMessage("");
        Log.sendSysMessageNoNewLine("");
        return "";
        
    }
}
