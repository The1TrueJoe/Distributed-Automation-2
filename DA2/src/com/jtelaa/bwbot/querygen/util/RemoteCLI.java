package com.jtelaa.bwbot.querygen.util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.jtelaa.bwbot.querygen.Main;
import com.jtelaa.bwbot.querygen.processes.QueryGenerator;
import com.jtelaa.bwbot.querygen.processes.QueryServer;
import com.jtelaa.da2.lib.bot.Bot;
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
        if (!run_as_local && Main.my_config.getProperty("remote_cli", "false").equalsIgnoreCase("true")) {
            Log.sendMessage("CLI: Preparing Remote CLI");

            while (!run) {
                MiscUtil.waitasec();

            }

            Log.sendMessage("CLI: Starting Local CLI");

            runRX();

        } else {
            MiscUtil.waitasec();

        }
    }

    @Override
    public synchronized String terminal(Command command) {
        Command[] commands = command.split(" ");
        Command cmd = commands[0];

        String response = "";

        // Shutdown
        if (Cases.exit(cmd)) {
            run = false;
            return "Shutting Down CLI";

        // CMD
        } else if (Cases.command(cmd)) {
            ComputerControl.sendCommand(command.addBlankUser().addBlankControlID().modifyforSys());

        // Dump Query Queue
        } else if (Cases.checkCase(cmd, new String[] {"dump"})) {
            response = "Listing Query Queue";
            int qty_to_dump = 100;

            // If command has arg
            if (commands.length > 1) {
                try {
                    // Parse the integer
                    qty_to_dump = Integer.parseInt(commands[1].command());

                } catch (NumberFormatException e) {
                    // Add error message
                    response += "\nInvalid Arg (Default 100)\n";

                }
            }

            if (qty_to_dump > QueryGenerator.query_queue.size()) { qty_to_dump = QueryGenerator.query_queue.size(); }
            response += "\nDumping " + qty_to_dump + "\n\n\n";

            // List out queries
            for (int i = 0; i < qty_to_dump; i++) {
                response += QueryGenerator.query_queue.poll() + "\n";

            }

        // Clear Queues
        } else if (Cases.checkCase(cmd, new String[] {"clear"})) {

            if (commands.length > 1) {
                int qty_to_remove;

                try {
                    qty_to_remove = Integer.parseInt(commands[1].command());

                } catch (NumberFormatException e) {
                    qty_to_remove = 10;

                }
                
                if (qty_to_remove > QueryGenerator.query_queue.size()) { qty_to_remove = QueryGenerator.query_queue.size(); }

                int i = 0;
                for (; i < qty_to_remove; i++) {
                    try {
                        QueryGenerator.query_queue.remove();

                    } catch (NoSuchElementException e) {
                        response = "Cleared " + i + " queries";

                    }
                }

                response = "Cleared " + i + " queries";

            } else {
                QueryGenerator.query_queue = new LinkedList<>();
                QueryServer.bot_queue = new LinkedList<>();

                response = "Cleared Queues";

            }

            

        // Get or change size of queue
        } else if (Cases.checkCase(cmd, new String[] {"size"})) {
            response = "";
            int size = 1000;

            // If only command
            if (commands.length == 1) {
                // Return size
                return "Queue Size is " + QueryGenerator.query_queue.size();

            // If command has arg
            } else if (commands.length > 1) {
                try {
                    // Change size
                    size = Integer.parseInt(commands[1].command());

                } catch (NumberFormatException e) {
                    // Report error
                    response += "\nInvalid Arg (Default 1000)\n";

                }
            }

            // Change and record chage
            QueryGenerator.MAX_QUERY_QUEUE_SIZE = size;
            response += "Changed Queue Size to " + size;

        } else if (Cases.checkCase(cmd, new String[] {"title"})) {
            response += ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape();
            response += ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/Rewards.txt", ConsoleColors.CYAN_BOLD) + "\n";
            response += ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/QueryGen.txt", ConsoleColors.YELLOW) + "\n";
            
        } else if (Cases.help(cmd)) {
            response += ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape();
            response += ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/Rewards.txt", ConsoleColors.CYAN_BOLD) + "\n";
            response += ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/QueryGen.txt", ConsoleColors.YELLOW) + "\n";

            String help = (
                "Query Generator CLI Help:\n"
                + ConsoleColors.YELLOW_UNDERLINED.getEscape() + "cmd" + ConsoleColors.RESET.getEscape() + " -> pass through a command to the systems OS\n"
                + ConsoleColors.YELLOW_UNDERLINED.getEscape() + "dump x" + ConsoleColors.RESET.getEscape() + " -> remove x queries from queue and print them (default 100)\n"
                + ConsoleColors.YELLOW_UNDERLINED.getEscape() + "clear x" + ConsoleColors.RESET.getEscape() + " -> clear x queries from the queue and clear bot queue (default all)\n"
                + ConsoleColors.YELLOW_UNDERLINED.getEscape() + "size x" + ConsoleColors.RESET.getEscape() + " -> change the size to x (default print the queue size)\n"
                + ConsoleColors.YELLOW_UNDERLINED.getEscape() + "add x y" + ConsoleColors.RESET.getEscape() + " -> add ip x to the request queue y times(s)\n"  
            );

            response += help + ConsoleColors.LINES_SHORT.getEscape();

        } else if (Cases.checkCase(cmd, "add")) {
            if (commands.length == 1) {
                response += "Error: Add IP";
                
            } else if (commands.length == 2) {
                Command ip = commands[1];
                response += "Adding 1 request from " + ip.command() + " to request queue";
                QueryServer.bot_queue.add(new Bot(ip.command()));

            } else if (commands.length == 3) {
                Command ip = commands[1];
                int count = Integer.parseInt(commands[2].command());

                response += "Adding " + count + " request(s) from " + ip.command() + " to request queue";

                for (int i = 0; i < count; i++) { QueryServer.bot_queue.add(new Bot(ip.command())); }

            } else {
                response += "Error: To many args";

            }

        }

        return response;
        
    }
}
