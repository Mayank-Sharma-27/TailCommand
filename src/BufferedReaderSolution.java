import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/*
✅ Only stores last 10 lines.
✅ Low memory.
❌ Still reads full file from start to end, not fastest.
 */
public class BufferedReaderSolution implements Tail {
    @Override
    @SneakyThrows
    public List<String> readlines(String filePath, int n) {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
        Deque<String> lines = new LinkedList<>();

        String line;
        while ((line = br.readLine()) != null) {
            if (lines.size() == n) {
                lines.pollFirst();
            }
            lines.push(line);
        }

        return new ArrayList<>(lines);
    }
}
