package com.jtelaa.bwbot.querygen.util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import com.jtelaa.bwbot.querygen.Main;
import com.jtelaa.bwbot.querygen.processes.QueryGenerator;
import com.jtelaa.bwbot.querygen.processes.QueryServer;
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
        if (!run_as_local && Main.my_config.runRemoteCLI()) {
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

        String response = "";

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
            Log.sendSysMessage(ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape());
            Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));
            Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/QueryGen.txt", ConsoleColors.YELLOW));
            
        } else if (Cases.help(cmd)) {
            Log.sendMessage(ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape());
            Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));
            Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/QueryGen.txt", ConsoleColors.YELLOW));

            String help = (
                "Query Generator CLI Help:\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "cmd" + ConsoleColors.RESET.getEscape() + " -> pass through a command to the systems OS\n" +
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "dump x" + ConsoleColors.RESET.getEscape() + " -> remove x queries from queue and print them (default 100)\n"+
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "clear x" + ConsoleColors.RESET.getEscape() + " -> clear x queries from the queue and clear bot queue (default all)\n"+
                ConsoleColors.YELLOW_UNDERLINED.getEscape() + "size x" + ConsoleColors.RESET.getEscape() + " -> change the size to x (default print the queue size)"  
            );

            Log.sendMessage(help);
            Log.sendMessage(ConsoleColors.LINES_SHORT.getEscape());

        }

        // Reset
        Log.app_verbose = prev_app_verbosity;
        Log.log_verbose = prev_log_verbosity;

        return response;
        
    }
}
