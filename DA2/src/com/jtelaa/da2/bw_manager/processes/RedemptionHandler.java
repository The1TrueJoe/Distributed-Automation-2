package com.jtelaa.da2.bw_manager.processes;

/**
 * Handles redemption requests
 */

 // TODO Comment
 // TODO Start

public class RedemptionHandler extends Thread {

    private volatile Queue<Redemption> active_requests;
}