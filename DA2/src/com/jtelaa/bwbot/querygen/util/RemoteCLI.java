package com.jtelaa.bwbot.querygen.util;

import java.util.LinkedList;

import com.jtelaa.bwbot.querygen.Main;
import com.jtelaa.bwbot.querygen.processes.QueryGenerator;
import com.jtelaa.bwbot.querygen.processes.QueryServer;
import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.lib.misc.MiscUtil;

public class RemoteCLI extends LocalCLI {

    @Override
    public void run() {
        if (Main.my_config.runRemoteCLI()) {
            Log.sendMessage("Preparing Remote CLI");

            while (!run) {
                MiscUtil.waitasec();

            }

            Log.sendMessage("Starting Local CLI");

            runRX();

        }
    }

    @Override
    public synchronized String terminal(Command command) {
        Command[] commands = command.getSubCommands(Command.SYSTEM_COMMAND);
        Command cmd = commands[0];

        // Shutdown
        if (Cases.exit(command)) {
            run = false;
            return "Shutting Down Remote CLI";

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

            // List out queries
            for (int i = 0; i < qty_to_dump; i++) {
                response += QueryGenerator.query_queue.poll() + "\n";

            }

            // Return
            return response;

        // Clear Queues
        } else if (Cases.checkCase(cmd, new String[] {"clear"})) {
            QueryGenerator.query_queue = new LinkedList<>();
            QueryServer.bot_queue = new LinkedList<>();

            return "Clearing Queues";

        // Get or change size of queue
        } else if (Cases.checkCase(cmd, new String[] {"size"})) {
            String response = "";
            int size = 1000;

            // If only command
            if (commands.length == 0) {
                // Return size
                return "Queue Size is " + QueryGenerator.MAX_QUERY_QUEUE_SIZE;

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

        }

        // Default
        return "";

    }
}
