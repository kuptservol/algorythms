package java_.server;

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
 * Elapsed time:		       10.11 secs
 * Data transferred:	        7.36 MB
 * Response time:		        0.10 secs
 * Transaction rate:	       98.91 trans/sec
 * Throughput:		        0.73 MB/sec
 * Concurrency:		       10.33
 * Successful transactions:        1000
 * Failed transactions:	           0
 * Longest transaction:	        0.11
 * Shortest transaction:	        0.10
 * <p>
 * siege -c 1000 -r 10
 * Transactions:		       10000 hits
 * Availability:		      100.00 %
 * Elapsed time:		       17.88 secs
 * Data transferred:	       73.58 MB
 * Response time:		        0.11 secs
 * Transaction rate:	      559.28 trans/sec
 * Throughput:		        4.11 MB/sec
 * Concurrency:		       58.76
 * Successful transactions:       10000
 * Failed transactions:	           0
 * Longest transaction:	        0.15
 * Shortest transaction:	        0.10
 * <p>
 * siege  -c 1000 -r 100
 * Transactions:		       55497 hits
 * Availability:		       98.11 %
 * Elapsed time:		      128.71 secs
 * Data transferred:	      408.32 MB
 * Response time:		        1.60 secs
 * Transaction rate:	      431.18 trans/sec
 * Throughput:		        3.17 MB/sec
 * Concurrency:		      688.92
 * Successful transactions:       55497
 * Failed transactions:	        1067
 * Longest transaction:	       23.09
 * Shortest transaction:	        0.10
 */
public class MultiThreadedBlockingServer extends BlockingServer {

    public MultiThreadedBlockingServer(int port) {
        super(port);
    }

    public static void main(String[] args) {
        MultiThreadedBlockingServer server = new MultiThreadedBlockingServer(7070);
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

                new Thread(() -> {
                    try {
                        httpServerTask.process(socket);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
