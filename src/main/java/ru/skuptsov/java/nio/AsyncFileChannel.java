package ru.skuptsov.java.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Sergey Kuptsov
 * @since 07/08/2016
 */
public class AsyncFileChannel {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path filePath = Paths.get("/Users/install/work/code/algorythms/src/main/resources/deliveredOnce.txt");

        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(filePath);

        ByteBuffer buffer = ByteBuffer.allocate(1);


        long position = 0;
        Future<Integer> future = fileChannel.read(buffer, position);
        int bytesRead = future.get();

        while (bytesRead != -1) {
            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);

            System.out.println(new String(data));

            buffer.clear();

            future = fileChannel.read(buffer, position += bytesRead);
            bytesRead = future.get();
        }

    }
}
