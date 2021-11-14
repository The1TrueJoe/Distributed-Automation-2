package com.jtelaa.da2.scheduler.logserver;

import java.io.File;
import java.util.ArrayList;
import java.util.Queue;

import com.jtelaa.da2.lib.misc.MiscUtil;

public class Logger extends Thread {

    private volatile ArrayList<File> logs;

    private volatile Queue<String> entry_queue;
    private volatile Queue<String> address_queue;

    private volatile String path;

    public synchronized void setPath(String path) { this.path = path; }

    public synchronized void add(String entry, String from) { entry_queue.add(entry); address_queue.add(from); }

    public void run() {

        while (!run) {
            MiscUtil.waitasec();
            
        }

        while (run) {

        }
    }


    /** Boolean to control the receiver */
    private volatile boolean run = false;

    /** Stops the command receiver */
    public synchronized void stopLogger() { run = false; }

    /** Stops the command receiver */
    public synchronized void startLogger() { run = true; }

    /** Checks if the receier is ready */
    public synchronized boolean loggerReady() { return run; }
    // TODO Implement
    
}
