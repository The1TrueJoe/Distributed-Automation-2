package com.jtelaa.da2.bot.util;

import com.jtelaa.da2.lib.cli.Cases;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;

// TODO comment

public class CLI extends LocalCLI {

    @Override
    public void run() {
        runRX();
        
    }

    @Override
    public String terminal(Command command) {
        // TODO Update Terminal Cases

        if (Cases.command(command)) {
            return ComputerControl.sendCommand(command.modifyforSys());

        }

        return "";
    }

    
    
}
