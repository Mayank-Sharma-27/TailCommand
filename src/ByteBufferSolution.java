import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/*
✅ Reads only necessary chunks.
✅ Efficient for large files.
❌ Complex logic (reverse traversal, partial reads).
 */
public class ByteBufferSolution implements Tail {

    @Override
    @SneakyThrows
    public List<String> readlines(String filePath, int n) {
        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        FileChannel channel = raf.getChannel();
        long size = channel.size();

        int bufferSize = 8192;
        List<String> result = new LinkedList<>();
        long position = size;
        StringBuilder line = new StringBuilder();
        while (position > 0 && result.size() < n) {
            int bytesToRead =( int )Math.min(bufferSize, position);
            position -= bytesToRead;
            channel.position(position);
            ByteBuffer buffer = ByteBuffer.allocate(bytesToRead);
            channel.read(buffer);
            buffer.flip();

            for (int i =bytesToRead -1; i >= 0; i--) {
                char c = (char) buffer.get(i);
                if (c == '\n') {
                    result.add(line.reverse().toString());
                    line.setLength(0);
                    if (result.size() == n) {
                        break;
                    }
                } else {
                    line.append(c);
                }
            }

        }

        Collections.reverse(result);

        channel.close();
        raf.close();
        return result;

    }

    @Override
    @SneakyThrows
    public void follow(String filePath, int n) {
        RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
        FileChannel channel = raf.getChannel();
        long position = channel.size();

        StringBuilder line = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Deque<String> lines = new LinkedList<>(readlines("test", n));

        while (true) {
            long currentSize = channel.size();

            if (currentSize > position) {
                channel.position(position);
                int bytesRead = channel.read(buffer);
                buffer.flip();
                for (int i =0; i < bytesRead; i++) {
                    char c = (char) buffer.get();
                    if (c == '\n') {
                        if (lines.size() == n) {
                            lines.pollLast();
                        }
                        lines.offerLast(line.toString());
                        System.out.println(line);
                        line.setLength(0);
                    } else {
                        line.append(c);
                    }
                }
                position = channel.position();
                buffer.clear();
            } else {
                Thread.sleep(500);
            }
        }
    }
}
