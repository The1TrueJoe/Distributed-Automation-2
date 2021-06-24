package com.jtelaa.da2.bot.main;

import com.jtelaa.da2.lib.config.Config;
import com.jtelaa.da2.lib.log.Log;

public class Main {
    public static void main(String[] args) {

        // TODO Add config loading
        Log.loadConfig();
        
        Log.sendMessage("Welcome to the DA2 Bot Client! I am bot " + Config.getID());
        

    }

    
}