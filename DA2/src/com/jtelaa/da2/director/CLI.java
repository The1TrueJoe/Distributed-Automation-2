package com.jtelaa.da2.director;

import com.jtelaa.da2.lib.bot.MgmtMessages;
import com.jtelaa.da2.lib.cli.LocalCLI;
import com.jtelaa.da2.lib.control.Command;

/**
 * Local CLI
 * 
 * @author Joseph
 * @since 2
 * 
 */

public class CLI extends LocalCLI {

    @Override
    public void run() {
        // Uses supers method for running CLI
        runRX(MgmtMessages.BOT_ENABLE_MESSAGE);

    }

    @Override
    public String terminal(Command command) {

        return "";
    }

    
    
}
