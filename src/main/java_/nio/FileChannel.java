package java_.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

/**
 * @author Sergey Kuptsov
 * @since 07/08/2016
 */
public class FileChannel {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path filePath = Paths.get("/Users/kuptservol/work/code/algorythms/src/main/resources/deliveredOnce.txt");

        java.nio.channels.FileChannel fileChannel = new FileInputStream(filePath.toFile()).getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(5);

        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            System.out.print(new String(buffer.array()).substring(0, buffer.limit()));
        }
    }
}
