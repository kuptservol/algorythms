package ru.skuptsov.java.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Runtime.getRuntime;

/**
 * @author Sergey Kuptsov
 * @since 23/10/2016
 * <p>
 * siege 127.0.0.1:7070 -c 100 -r 10
 * <p>
 */
public abstract class BlockingServer {
    protected final int port;
    protected final ExecutorService mainThread = Executors.newSingleThreadExecutor();
    protected final HttpServerTask httpServerTask = new HttpServerTask();
    protected volatile boolean running = false;
    protected volatile boolean starting = false;
    protected ServerSocket serverSocket;

    public BlockingServer(int port) {
        this.port = port;
    }

    public void start() {
        starting = true;
        System.out.println("Starting service");
        getRuntime().addShutdownHook(new ShutdownThread(this));
        openServerSocket(port);
        mainThread.submit(getMainLoopTask());
        starting = false;
        running = true;
        System.out.println("Service started");
    }

    protected abstract Runnable getMainLoopTask();

    public void stop() {
        System.out.println("Stopping service");
        if (!running || starting) {
            throw new IllegalArgumentException();
        }
        running = false;
        mainThread.shutdown();
        closeServerSocket();

        System.out.println("Service stopped");
    }

    private void closeServerSocket() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket(int port) {
        try {
            serverSocket = new ServerSocket(port, 1000000);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + port, e);
        }
    }

    private final static class ShutdownThread extends Thread {
        private final BlockingServer server;

        private ShutdownThread(BlockingServer server) {
            this.server = server;
        }

        @Override
        public void run() {
            server.stop();
        }
    }
}
