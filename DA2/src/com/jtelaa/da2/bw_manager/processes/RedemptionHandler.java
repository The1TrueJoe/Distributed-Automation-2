package com.jtelaa.da2.bw_manager.processes;

import java.util.Queue;

import com.jtelaa.da2.bw_manager.redemptions.Redemption;

/**
 * Handles redemption requests
 */

 // TODO Comment
 // TODO Start

public class RedemptionHandler extends Thread {

    private volatile Queue<Redemption> active_requests;
}