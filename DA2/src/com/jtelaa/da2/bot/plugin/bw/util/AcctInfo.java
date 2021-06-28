package com.jtelaa.da2.bot.plugin.bw.util;

import com.jtelaa.da2.bot.main.Main;
import com.jtelaa.da2.redemption_manager.util.Account;

/**
 * Allows for the auto-redemption of points
 * 
 * @since 2
 */

public class AcctInfo {

    private static String ptmgr_ip;

    public static void setup() {
        ptmgr_ip = Main.me.getPointMgrIP();

    }

    public static void announcePoints(double points) {

    }

    public void announceAccount(Account account) {
        
    }

    public static double getPointCount() {
        return 0;

    }

    public static void setupAccount() {

    }
    
}
