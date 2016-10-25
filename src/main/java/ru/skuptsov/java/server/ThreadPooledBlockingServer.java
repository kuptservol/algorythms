package ru.skuptsov.java.server;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Runtime.getRuntime;

/**
 * @author Sergey Kuptsov
 * @since 23/10/2016
 * <p>
 * siege -c 100 -r 10
 * Transactions:		        1000 hits
 * Availability:		      100.00 %
 * Elapsed time:		       10.11 secs
 * Data transferred:	        7.36 MB
 * Response time:		        0.11 secs
 * Transaction rate:	       98.91 trans/sec
 * Throughput:		        0.73 MB/sec
 * Concurrency:		       10.69
 * Successful transactions:        1000
 * Failed transactions:	           0
 * Longest transaction:	        0.20
 * Shortest transaction:	        0.10
 * <p>
 * siege -c 1000 -r 10
 * Transactions:		       10000 hits
 * Availability:		      100.00 %
 * Elapsed time:		       17.12 secs
 * Data transferred:	       73.58 MB
 * Response time:		        0.10 secs
 * Transaction rate:	      584.11 trans/sec
 * Throughput:		        4.30 MB/sec
 * Concurrency:		       61.13
 * Successful transactions:       10000
 * Failed transactions:	           0
 * Longest transaction:	        0.14
 * Shortest transaction:	        0.10
 * <p>
 * siege  -c 1000 -r 100
 * failed
 */
public class ThreadPooledBlockingServer extends BlockingServer {
    private final ExecutorService workPool;

    public ThreadPooledBlockingServer(int port, int threads) {
        super(port);
        workPool = Executors.newFixedThreadPool(threads);
    }

    public static void main(String[] args) {
        ThreadPooledBlockingServer server = new ThreadPooledBlockingServer(7070,
                getRuntime().availableProcessors() * 8);
        server.start();
    }

    @Override
    protected Runnable getMainLoopTask() {
        return new ServerMainThread();
    }

    // thread per client connection
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

                workPool.submit(() -> {
                    try {
                        httpServerTask.process(socket);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
