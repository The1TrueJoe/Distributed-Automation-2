package com.jtelaa.da2.director.util.cli;

import com.jtelaa.da2.lib.control.Command;

public abstract class CLIType {
    
    public abstract String startCLI(Command command);

    public abstract String terminal(Command command);

}
