import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SimpleDuplicateFinder {
    // Create a HashMap to store file content as keys and a list of file paths as values
    private HashMap<String, List<String>> fileHashMap = new HashMap<>();

    // Method to find and record duplicate files in the specified directory
    public void findDuplicates(Path path) throws IOException {
        // Walk through all files and directories in the given path
        Files.walk(path)
            .filter(Files::isRegularFile) // Filter only regular files, not directories
            .forEach(file -> {
                try {
                    // Read the content of the current file as a string
                    String content = new String(Files.readAllBytes(file));
                    // Get the list of file paths with the same content or create a new list if none exists
                    List<String> list = fileHashMap.getOrDefault(content, new ArrayList<>());
                    // Add the current file's path to the list
                    list.add(file.toString());
                    // Update the HashMap with the content and the list of file paths
                    fileHashMap.put(content, list);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    // Method to print out the duplicate file paths
    public void printDuplicates() {
        // Filter and process lists of file paths with more than one entry (duplicates)
        fileHashMap.values().stream()
            .filter(list -> list.size() > 1)
            .forEach(list -> System.out.println("Duplicates: " + String.join(", ", list)));
    }

    // Main method to run the duplicate file finding and printing process
    public static void main(String[] args) throws IOException {
        SimpleDuplicateFinder df = new SimpleDuplicateFinder();
        // Find duplicates starting from the current directory (".")
        df.findDuplicates(Paths.get("."));
        // Print the duplicate file paths
        df.printDuplicates();
    }
}
