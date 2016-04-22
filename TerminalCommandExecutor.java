// package diadrom.profiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author jaxonisack
 */

public class CommandTerminalExecutor {
    
    public static String runCommand(String commandToRun, boolean waitForResult) {
		
		String commandResponse = "";

		ProcessBuilder commandProcessBuilder = null;
		
		// Find OS running on VM
		String operatingSystem = System.getProperty("os.name");
 
		if (operatingSystem.toLowerCase().contains("window")) {
			// In case of windows run command using "commandToRun"
			commandProcessBuilder = new ProcessBuilder("cmd", "/c", commandToRun);
		} else {
			// In case of Linux/Ubuntu run command using /bin/bash
			commandProcessBuilder = new ProcessBuilder("/bin/bash", "-c", commandToRun);
		}
 
		commandProcessBuilder.redirectErrorStream(true);
 
		try {
			
			Process process = commandProcessBuilder.start();
			
			if (waitForResult) {
            
                InputStream commandStream = process.getInputStream();
				commandResponse = getStringFromStream(commandStream);
				commandStream.close();
			
			}
 
		} catch (Exception e) {

			System.out.println("Error Executing command command" + e);
		
		}
		return commandResponse;
	}
 
	private static String getStringFromStream(InputStream commandStream) throws IOException {
		
		if (commandStream != null) {
            
            Writer crunchifyWriter = new StringWriter();
 
			char[] commandBuffer = new char[2048];
		
			try {
        
                Reader commandReader = new BufferedReader(new InputStreamReader(commandStream, "UTF-8"));
				
				int count;
				
				while ((count = commandReader.read(commandBuffer)) != -1) {
					crunchifyWriter.write(commandBuffer, 0, count);
				}

			} finally {
				
				commandStream.close();
			
			}
			
			return crunchifyWriter.toString();
		
		} else {
			
			return "";
		
		}
	}
}
