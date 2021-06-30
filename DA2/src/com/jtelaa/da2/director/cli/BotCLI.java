package com.jtelaa.da2.director.cli;

import com.jtelaa.da2.director.botmgmt.BotMgmt;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.QueuedCommandSender;
import com.jtelaa.da2.lib.control.QueuedResponseReceiver;
import com.jtelaa.da2.lib.misc.MiscUtil;

public class BotCLI extends CLIType {

    @Override
    public String startCLI(Command command) {
        String id = command.split(" ")[2].command();

        if (MiscUtil.isNumeric(id)) {
            cmd_tx.add(new Command(BotMgmt.getBot(Integer.parseInt(id)).getIP(), terminal(command)));
            MiscUtil.waitasec(.5);
            return cmd_rx.getMessage();

        } else {
            return BotMgmt.INVALID_ID_MESSAGE;
            
        }

    }

    @Override
    public String terminal(Command command) {
        // TODO Auto-generated method stub
        return null;
    }

}
