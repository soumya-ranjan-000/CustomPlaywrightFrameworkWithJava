package srg.testreport;

import java.io.IOException;
import java.nio.file.*;

public class GenerateTestReport {
    public static void main(String[] args) {
        Path folderPath = Paths.get("./extent-reports");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
            // Iterate over the files and folders in the directory
            for (Path file : stream) {
                if (!Files.isDirectory(file)) {
                    System.out.println("File: " + file.getFileName());
                } else {
                    System.out.println("Directory: " + file.getFileName());
                }
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println("Error accessing directory: " + e.getMessage());
        }
    }
}
