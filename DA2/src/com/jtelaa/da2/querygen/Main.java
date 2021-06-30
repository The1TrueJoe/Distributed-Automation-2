package com.jtelaa.da2.querygen;

import com.jtelaa.da2.lib.control.QueuedCommandReceiver;
import com.jtelaa.da2.lib.control.QueuedResponseSender;
import com.jtelaa.da2.lib.log.Log;
import com.jtelaa.da2.querygen.processes.QueryGenerator;
import com.jtelaa.da2.querygen.processes.QueryServer;
import com.jtelaa.da2.querygen.processes.RequestServer;

public class Main {

    public static void main(String[] args) {
        // Start Logging
        Log.loadConfig();
        Log.openClient("logging_server_ip");

        // Request server setup
        RequestServer req_srv = new RequestServer();
        Log.sendMessage("Starting request server");
        req_srv.start();
        
        // Query server setup
        QueryServer qry_serv = new QueryServer();
        Log.sendMessage("Starting query server");
        qry_serv.start();

        // Query generator setup
        QueryGenerator qry_gen = new QueryGenerator();
        Log.sendMessage("Starting query generator");
        qry_gen.start();

        // CMD setup
        QueuedCommandReceiver cmd_rx = new QueuedCommandReceiver();
        QueuedResponseSender cmd_tx = new QueuedResponseSender();
        cmd_rx.start();
        cmd_tx.start();
        Log.sendMessage("CMD Started");

        // TODO add CLI & Manual Intervention

        

        Log.sendLogMessage("Done! Shutting down");
        Log.closeLog();
        req_srv.stopServer();
        qry_serv.stopServer();
        qry_gen.stopGen();
    }
    
}
