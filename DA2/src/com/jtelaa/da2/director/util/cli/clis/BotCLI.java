package com.jtelaa.da2.director.util.cli.clis;

import com.jtelaa.da2.director.botmgmt.BotMgmt;
import com.jtelaa.da2.director.util.cli.CLI;
import com.jtelaa.da2.director.util.cli.CLIType;
import com.jtelaa.da2.lib.misc.Misc;

public class BotCLI extends CLIType {

    public static String BOT_ENABLE_MESSAGE = "Wake Up Bot!";

    @Override
    public String startCLI(String command) {
        String id = command.split(" ")[2];

        if (Misc.isNumeric(id)) {
            CLI.openCMDClient(BotMgmt.getBot(Integer.parseInt(id)).getIP());
            CLI.sendCommand(terminal(command));

            return CLI.getResponse();

        } else {
            return "Invalid bot id";
            
        }

    }

    @Override
    public String terminal(String command) {
        // TODO Auto-generated method stub
        return null;
    }

    
    
}
