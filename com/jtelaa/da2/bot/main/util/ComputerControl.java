public class ComputerControl {

    /**
     * Runs a CMD Command
     */
    public static void CMDProcess(String command) throws IOException {
		// Creates process
		ProcessBuilder builder = new ProcessBuilder(command);
		
		// Redirect errorStream
		builder.redirectErrorStream(true);
		
		// Start process
		Process process = builder.start();
		
		// Read lines
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		
		// Read output
		while (true) {
			// Build string
            line = reader.readLine();
            		
            // Check if available
            if (line == null) {
            	break; 
            }
            
            // Print output
            System.out.println(line);
        }
	}
}
