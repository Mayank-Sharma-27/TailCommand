import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
/*
❌ Loads entire file into RAM.
❌ Extremely inefficient for large files.
✅ Easy to write.
 */
public class WorstSolution implements Tail {


    @Override
    @SneakyThrows
    public List<String> readlines(String filePath, int n) {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        Deque<String> deque = new ArrayDeque<>();

        for  (String line: lines) {
            if (deque.size() == n) {
                deque.pollFirst();
            }
            deque.offerLast(line);
        }
        return new ArrayList<>(deque);
    }
}
