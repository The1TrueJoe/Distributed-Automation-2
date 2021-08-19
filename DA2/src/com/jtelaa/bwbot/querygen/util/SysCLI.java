package com.jtelaa.bwbot.querygen.util;

import java.util.Scanner;
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

public class SysCLI extends LocalCLI {

    @Override
    public void run() {
        if (Main.my_config.runLocalCLI()) {
            // Setup
            Log.sendMessage("Preparing Local CLI");
            Scanner keyboard = new Scanner(System.in);
            String type;

            while (!run) {
                MiscUtil.waitasec();

            }

            Log.sendMessage("Starting Local CLI");
        
            // While run
            while (run) {
                Log.sendSysMessageNoNewLine(">");
                type = keyboard.nextLine();

                Log.sendMessage(terminal(new Command(type)));

            }
        
            keyboard.close();

        }
    }

    @Override
    public synchronized String terminal(Command command) {
        Command[] commands = command.split(" ");
        Command cmd = commands[0];

        // Shutdown
        if (Cases.exit(command)) {
            run = false;
            return "Shutting Down CLI";

        // CMD
        } else if (Cases.command(command)) {
            ComputerControl.sendCommand(command.modifyforSys());

        // Dump Query Queue
        } else if (Cases.checkCase(cmd, new String[] {"dump"})) {
            String response = "Listing Query Queue";
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

            // Return
            return response;

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
                        return "Cleared " + i + " queries";

                    }
                }

                return "Cleared " + i + " queries";

            } else {
                QueryGenerator.query_queue = new LinkedList<>();
                QueryServer.bot_queue = new LinkedList<>();

                return "Cleared Queues";

            }

            

        // Get or change size of queue
        } else if (Cases.checkCase(cmd, new String[] {"size"})) {
            String response = "";
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

            // Return
            return response;

        } else if (Cases.checkCase(cmd, new String[] {"title"})) {
            Log.sendSysMessage(ConsoleColors.CLEAR.getEscape() + ConsoleColors.LINES.getEscape());
            Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/Rewards.txt", ConsoleColors.CYAN_BOLD));
            Log.sendSysMessage(ConsoleBanners.otherBanner("com/jtelaa/bwbot/querygen/misc/QueryGen.txt", ConsoleColors.YELLOW));
            
        }

        // Default
        Log.sendLogMessage("");
        Log.sendSysMessageNoNewLine("");
        return "";

    }
    
}
