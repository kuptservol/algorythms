package java_.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Sergey Kuptsov
 * @since 06/08/2016
 */
public class SimpleChannel {

    public static void main(String[] args) throws IOException {
        RandomAccessFile file = new RandomAccessFile("file.txt", "rw");
        FileChannel fileChannel = file.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(3);

        int bytesRead = fileChannel.read(buf);

        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);

            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }

            buf.clear();

            bytesRead = fileChannel.read(buf);
        }

        file.close();
    }
}
