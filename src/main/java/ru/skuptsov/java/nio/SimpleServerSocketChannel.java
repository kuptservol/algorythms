package ru.skuptsov.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Sergey Kuptsov
 * @since 07/08/2016
 */
public class SimpleServerSocketChannel {

    public static void main(String[] args) throws IOException {

        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.socket().bind(new InetSocketAddress(9999));
        channel.configureBlocking(false);

        while (true) {
            SocketChannel workChannel = channel.accept();

            if (workChannel != null) {

                ByteBuffer buf = ByteBuffer.allocate(48);

                workChannel.configureBlocking(false);
                int bytesRead = workChannel.read(buf);

                while (bytesRead > 0) {
                    System.out.println("Read " + bytesRead);

                    buf.flip();

                    while (buf.hasRemaining()) {
                        System.out.print((char) buf.get());
                    }

                    buf.clear();

                    bytesRead = workChannel.read(buf);
                }

                String httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: 38\r\n" +
                        "Content-Type: text/html\r\n" +
                        "\r\n" +
                        "<html><body>Hello World!</body></html>";

                byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");

                ByteBuffer writeBuffer = ByteBuffer.allocate(httpResponseBytes.length);

                writeBuffer.put(httpResponseBytes);

                writeBuffer.flip();
                int bytesWritten = workChannel.write(writeBuffer);


                while (bytesWritten > 0 && writeBuffer.hasRemaining()) {
                    bytesWritten = workChannel.write(writeBuffer);
                }

            }
        }

    }


}
