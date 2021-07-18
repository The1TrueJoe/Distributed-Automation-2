package com.jtelaa.da2.bot.util;

import com.jtelaa.bwbot.bw.BingRewards;
import com.jtelaa.da2.director.botmgmt.MgmtMessages;
import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;
import com.jtelaa.da2.lib.log.Log;

// TODO comment

public class CLI extends LocalCLI {

    @Override
    public void run() {
        // Uses supers method for running CLI
        runRX(MgmtMessages.BOT_ENABLE_MESSAGE);

    }

    @Override
    public String terminal(Command command) {
        if (Cases.command(command)) {
            // Send command to local system
            return ComputerControl.sendCommand(command.modifyforSys());

        } else if (Cases.exit(command)) {
            // Exiting CLI (Will wait to be enabled)
            Log.sendMessage("Exiting CLI");
            cli_enabled = false;

        } else if (Cases.shutdown(command)) {
            // Shutting down bot
            Log.sendLogMessage("Goodbye!");

            cmd_rx.stopReceiver();
            cmd_tx.stopSender();

            ComputerControl.shutdown();
        
        } else if (Cases.rewards_plugin(command)) {
            BingRewards bw = new BingRewards();
            bw.args(command);
            bw.start();
            
        }

        return "";
    }

    
    
}
