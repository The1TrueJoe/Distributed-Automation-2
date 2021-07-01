package com.jtelaa.da2.lib.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jtelaa.da2.lib.log.Log;

import java.awt.Robot;

public class ComputerControl {

	private static Robot pc;

	public static String getOS() { return System.getProperty("os.name"); }

    /**
     * Runs a CMD Command
     */
    public static String sendCommand(Command command) {
		Log.sendMessage("Sending command: " + command);

		try {
			// Creates process
			ProcessBuilder builder = new ProcessBuilder(command.command());
		
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

			if (command.isHeadless()) { return line; } else { return ""; }

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

	/** Tells the local machine to shutdown */
	public static void shutdown() { sendCommand(new Command("shutdown")); }
	
}
