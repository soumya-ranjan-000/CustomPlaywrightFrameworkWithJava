package srg.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BashScriptExecuter {
    public static void main(String[] args) {
        try {
            String scriptPath = "bashscripts/script.sh";
            // Execute a bash command using Runtime
            Process process = Runtime.getRuntime().exec(new String[]{"bash", scriptPath});

            // Capture the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
