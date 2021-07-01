package com.jtelaa.da2.director.cli;

import com.jtelaa.da2.lib.control.Command;

public abstract class CLIType {
    
    public abstract String startCLI(Command command);

    public abstract String terminal(Command command);

}
