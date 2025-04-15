import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TailCommand {

    // You are given a file with text content (can
    // be simulated as a list of strings or a string with line breaks).
    // Implement a method to return the last n lines from the file.

    // Basic Java Implementation In-Memory
    public static List<String> tail(List<String> fileLines, int n) {
        int size = fileLines.size();
        return fileLines.subList(Math.max(0, size - n), size);
    }

    // Draw back of this implementation is that everything will be in memory


    // Implementation for large file
    public static List<String> tail(BufferedReader reader, int n) throws IOException {
        Deque<String> deque = new ArrayDeque<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (deque.size() == n) {
                deque.pollFirst();
            }
            deque.offerLast(line);
        }
        return new ArrayList<>(deque);
    }

    // Tail with Pattern Filter (e.g tail only lines containing "ERROR"
    public static List<String> tailWithFilter(BufferedReader reader, int n, String pattern) throws IOException {
        Deque<String> deque = new ArrayDeque<>();
        String line;

        while((line = reader.readLine()) != null) {
            if (line.contains(pattern)) {
                if (deque.size() == n) {
                    deque.pollFirst();
                }
                deque.offerLast(line);
            }
        }
        return new ArrayList<>(deque);
    }

    // Method to tail the log file in real-time
    public static void tailFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        long filePointer = file.length();
        reader.skip(filePointer);

        // Continuously monitor the file for new lines
        while (true) {
            long currentFileLength = file.length();
            if (currentFileLength > filePointer) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                filePointer = currentFileLength;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }

}
