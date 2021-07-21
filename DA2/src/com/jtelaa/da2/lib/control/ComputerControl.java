package com.jtelaa.da2.lib.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.jtelaa.da2.lib.log.Log;

import java.awt.Robot;

/**
 * Methods for interacting with the local host
 * 
 * @since 1
 * @author Joseph
 * 
 * @see com.jtelaa.da2.lib.control.Command
 */

public class ComputerControl {

	private static Robot pc;

	/**
	 * Detects OS
	 * 
	 * @return OS
	 */

	public static String getOS() { 
		// Get os
		String os = System.getProperty("os.name"); 

		// If it is a form of linux
		if (os.toLowerCase().contains("linux")) {
			return "Linux";

		// If it is a form of windows
		} else if (os.toLowerCase().contains("windows")) {
			return "Windows";

		// This system is most efficient on linux, so it will asssume it is running on linux if the os is unknown
		} else {
			return "Linux";

		}
	
	}

    /**
     * Runs a CMD Command
	 * 
	 * @param command
	 * 
	 * @return Response
	 * 
	 * @see com.jtelaa.da2.lib.control.Command
     */

    public static String sendCommand(Command command) {
		if (!command.isHeadless()) { Log.sendMessage("Running Command: " + command.command()); }

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
			
			if (!command.isHeadless()) { 
				//Log.sendMessage(line);
				return line; 

			} else { 
				return ""; 

			}

		} catch (IOException e) { 
			return e.getMessage();

		}
	}

	/**
     * Runs a CMD Command
	 * 
	 * @param command Command to send
	 * 
	 * @return Response
     */

    public static String sendCommand(String command) { return sendCommand(new Command(command)); }

	/**
	 * Presses specified keys at the same time and then releases them
	 * 
	 * @param keys
	 */

	public static void pressKey(int[] keys) {
		// Press all keys
        for (int key : keys) {
            pc.keyPress(key);
        }

		// Release all keys
        for (int key : keys) {
            pc.keyRelease(key);
        }
    }

	/** Tells the local machine to shutdown */
	public static void shutdown() { sendCommand(new Command("shutdown")); }
	
}
