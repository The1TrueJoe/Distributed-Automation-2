package com.jtelaa.da2.director.util.cli.clis;

import com.jtelaa.da2.director.botmgmt.BotMgmt;
import com.jtelaa.da2.director.util.cli.CLIType;
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


    /** Accessible CMD Sender */

    public static QueuedResponseReceiver cmd_rx;
    public static QueuedCommandSender cmd_tx;

    public static void open() { 
        cmd_rx = new QueuedResponseReceiver();
        cmd_tx = new QueuedCommandSender();

    }

    public static void close() {
        cmd_rx.stopReceiver();
        cmd_tx.stopSender();

    }

}
