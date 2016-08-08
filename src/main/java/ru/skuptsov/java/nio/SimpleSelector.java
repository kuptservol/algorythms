package ru.skuptsov.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Sergey Kuptsov
 * @since 06/08/2016
 */
public class SimpleSelector {

    public static void main(String[] args) throws IOException {

        SocketAddress address = new InetSocketAddress("dev1.dating.kama.gs", 80);
        SocketAddress address2 = new InetSocketAddress("lb1.dating.kama.gs", 80);

        SocketChannel socketChannel = SocketChannel.open(address);
        SocketChannel socketChannel2 = SocketChannel.open(address2);
        socketChannel.configureBlocking(false);
        socketChannel2.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        socketChannel2.register(selector, SelectionKey.OP_READ);

        ByteBuffer charBuffer = ByteBuffer.allocate(50);

        charBuffer.put("ssdfsdfsdfsd".getBytes());

        charBuffer.flip();

        while (charBuffer.hasRemaining()) {
            socketChannel.write(charBuffer);
        }

        while (true) {
            int readyChannels = selector.select();

            if (readyChannels == 0) continue;

            Set<SelectionKey> selectedKeys = selector.selectedKeys();


            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();


                if (key.isAcceptable()) {

                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.

                } else if (key.isReadable()) {

                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(48);

                    int bytesRead = channel.read(buf);

                    while (bytesRead != -1) {
                        System.out.println("Read " + bytesRead);

                        buf.flip();

                        while (buf.hasRemaining()) {
                            System.out.print((char) buf.get());
                        }

                        buf.clear();

                        bytesRead = channel.read(buf);
                    }

                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }

                keyIterator.remove();
            }

        }
    }
}
