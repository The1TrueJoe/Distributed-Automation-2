package com.jtelaa.da2.lib.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jtelaa.da2.lib.log.Log;

import java.awt.Robot;

public class ComputerControl {

	private static Robot pc;

    /**
     * Runs a CMD Command
     */
    public static String sendCommand(String command) {
		Log.sendMessage("Sending command: " + command);

		try {
			// Creates process
			ProcessBuilder builder = new ProcessBuilder(command);
		
			// Redirect errorStream
			builder.redirectErrorStream(true);
		
			// Start process
			Process process = builder.start();
		
			// Read lines
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
		
			// Read output
			while (line == null || line != "") {
				// Build string
        	    line += reader.readLine();

        	}

			return line;

		} catch (IOException e) { 
			return e.getMessage();

		}
	}

	/**
	 * Presses specified keys at the same time and then releases them
	 * 
	 * @param keys
	 */

	public static void pressKey(int[] keys) {
        for (int key : keys) {
            pc.keyPress(key);
        }

        for (int key : keys) {
            pc.keyRelease(key);
        }
    }
}
