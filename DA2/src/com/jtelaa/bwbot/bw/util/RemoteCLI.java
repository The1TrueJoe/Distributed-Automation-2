package com.jtelaa.bwbot.bw.util;

import com.jtelaa.bwbot.bw.Main;
import com.jtelaa.bwbot.bw.sys.AcctInfo;
import com.jtelaa.bwbot.bw.sys.Redeem;
import com.jtelaa.bwbot.bwlib.Card;
import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.config.PropertiesUtils;
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
        if (!run_as_local && PropertiesUtils.isTrue(Main.config, "remote_cli", false)) {
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
            
        } else if (Cases.checkCase(cmd, "redeem")) {
            Redeem.redeemCard(Card.getCard(commands[1].command()));

        } else if (Cases.checkCase(cmd, "account")) {
            if (Cases.checkCase(commands[1], "get")) {
                AcctInfo.requestAccount();

            } else if (Cases.checkCase(commands[1], "list")) {
                Log.sendMessage("Account:\n" + AcctInfo.me.toString());

            } else if (Cases.checkCase(commands[1], "update")) {
                AcctInfo.announceAccount();

            } else {
                Log.sendMessage("Account:\n" + AcctInfo.me.toString());

            }

        } else if (Cases.help(cmd)) {
            Log.sendMessage(ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape());
            Log.sendMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/bw/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));

            String help = (
                "Bot CLI Help:\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "cmd" + ConsoleColors.RESET.getEscape() + " -> pass through a command to the systems OS\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "title" + ConsoleColors.RESET.getEscape() + " -> clears the screen and prints the title\n"+
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "account" + ConsoleColors.RESET.getEscape() + " -> account information (default: list)\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "--- get" + ConsoleColors.RESET.getEscape() + " -> request account from the rewards manager\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "--- list" + ConsoleColors.RESET.getEscape() + " -> print out the account info (used for sign-in)\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "--- update" + ConsoleColors.RESET.getEscape() + " -> manually send updated account info to the rewards manager"
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
