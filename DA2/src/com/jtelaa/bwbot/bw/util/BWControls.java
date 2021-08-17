package com.jtelaa.bwbot.bw.util;

import java.awt.event.KeyEvent;

import com.jtelaa.bwbot.bwlib.Query;
import com.jtelaa.da2.lib.control.Command;
import com.jtelaa.da2.lib.control.ComputerControl;

/**
 * Utilities class to interface with the local machine for this usecase
 * 
 * @author Joseph
 * @since 2
 * 
 * @see com.jtelaa.bwbot.bw.sys.SearchSystem
 * @see com.jtelaa.bwbot.bw.sys.AcctInfo
 * @see com.jtelaa.bwbot.bw.Main
 * @see com.jtelaa.da2.lib.control.Command
 * @see com.jtelaa.da2.lib.control.ComputerControl
 */

public class BWControls {

    /* Browser open */

    /** Opens Bing on chrome to the specified search query */
    public static void openBing(Query query) { openChrome(Query.BING_URL + query.getQuery());}

    /** Opens chrome to the specified URL */
    public static void openChrome(String args) { ComputerControl.sendCommand(new Command("chrome.exe " + args)); }

    /** Opens chrome to the home pageL */
    public static void openChrome() { ComputerControl.sendCommand(new Command("chrome.exe")); }


    /* Mobile control */

    /** Sets chrome to mobile mode */
    public static void enterMobile() { chromeInspect(); chromeMobile(); }

    /** Returns chrome from mobile mode */
    public static void exitMobile()  { chromeMobile(); chromeInspect(); }


    /* Control chrome */

    /** Opens chrome in inspect */
    private static void chromeInspect() { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_I}); }
    
    /** Puts chrome into mobile mode from inspect */
    private static void chromeMobile()  { ComputerControl.pressKey(new int[] {KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_M}); }

    /** Closes the current tab in chrome */
    public static void closeTab() { ComputerControl.pressKey(new int[] { KeyEvent.VK_CONTROL, KeyEvent.VK_W}); }

    /** Closes the current tab in chrome */
    public static void closeWindow() { ComputerControl.pressKey(new int[] { KeyEvent.VK_ALT, KeyEvent.VK_SHIFT, KeyEvent.VK_W}); }
    
}
