package com.jtelaa.da2.bot.plugin.bw;

import java.awt.KeyEvent;

import com.jtelaa.da2.lib.config.Config;
import com.jtelaa.da2.lib.misc.ComputerControl;
import com.jtelaa.da2.querygen.util.Query;

public class BingRewards extends Thread {

    public final String BING_URL = "bing.com/search?1=";

    private String query_ip;

    public void run() {
        query_ip = Config.getQueryGenIP();

    }

    public String requestQuery() {
        return null;
    }

    public void openBing(Query query) { openChrome(BING_URL + query.getQuery());}
    public void openChrome(String args) { ComputerControl.sendCommand("chrome.exe " + args);}

    public void enterMobile() { chromeInspect(); chromeMobile(); }
    public void exitMobile()  { chromeMobile(); chromeInspect(); }

    public void chromeInspect() { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_I}); }
    public void chromeMobile()  { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_M}); }

    
}
