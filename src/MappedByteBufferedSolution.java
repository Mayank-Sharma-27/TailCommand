import lombok.SneakyThrows;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
✅ Fastest possible.

✅ Uses OS-paged memory, minimizes syscalls.

✅ Reads only needed chunks.

❌ More code complexity.
 */
public class MappedByteBufferedSolution implements Tail{
    @Override
    @SneakyThrows
    public List<String> readlines(String filePath, int n) {
        FileChannel fc = FileChannel.open(Paths.get(filePath), StandardOpenOption.READ);
        long fileSize = fc.size();
        int readChunk = 8192;
        List<String> lines = new LinkedList<>();
        StringBuilder currentLine = new StringBuilder();
        long position = fileSize;

        while (position > 0 && lines.size() < n) {
            int bytesToRead = (int) Math.min(position, readChunk);
            position -= bytesToRead;
            MappedByteBuffer mappedByteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, position, bytesToRead);

            for (int i= bytesToRead; i > 0; i--) {
                char c = (char) mappedByteBuffer.get(i);
                if (c == '\n') {
                    lines.add(currentLine.reverse().toString());
                    currentLine.setLength(0);
                    if (lines.size() >= n) {
                        break;
                    }
                } else {
                    lines.add(currentLine.toString());
                }
            }
        }
        if (!currentLine.isEmpty() && lines.size() <= n) {
            lines.add(currentLine.reverse().toString());
        }
        Collections.reverse(lines);
        return lines;
    }


}
