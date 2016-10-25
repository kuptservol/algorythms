package ru.skuptsov.java.server;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Sergey Kuptsov
 * @since 23/10/2016
 * <p>
 * siege -c 100 -r 10
 * <p>
 * Transactions:		        1000 hits
 * Availability:		      100.00 %
 * Elapsed time:		      103.62 secs
 * Data transferred:	        7.36 MB
 * Response time:		        9.33 secs
 * Transaction rate:	        9.65 trans/sec
 * Throughput:		            0.07 MB/sec
 * Concurrency:		            90.07
 * Successful transactions:        1000
 * Failed transactions:	           0
 * Longest transaction:	       10.28
 * Shortest transaction:	        0.11
 * <p>
 * siege -c 1000 -r 10
 * failed
 * <p>
 * siege  -c 1000 -r 100
 * failed
 */
public class SingleThreadedBlockingServer extends BlockingServer {

    public SingleThreadedBlockingServer(int port) {
        super(port);
    }

    public static void main(String[] args) {
        SingleThreadedBlockingServer server = new SingleThreadedBlockingServer(7070);
        server.start();
    }

    @Override
    protected Runnable getMainLoopTask() {
        return new ServerMainThread();
    }

    private final class ServerMainThread implements Runnable {

        @Override
        public void run() {
            while (running) {
                Socket socket;
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(
                            "Error accepting client connection", e);
                }

                try {
                    httpServerTask.process(socket);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
