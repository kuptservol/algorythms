package ru.skuptsov.performance;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Sergey Kuptsov
 * @since 25/04/2016
 */
public class LockSpeedTest {

    interface Execution {
        void execute();
    }

    private static class ExecutionCommand<R> implements Execution {

        public void execute() {
            double value = 0;
            for (long i = 0; i < 5000000l; i++) {
                value += 1;
            }
        }
    }

    private static abstract class AbstractExecutionDecorator implements Execution {

        protected final ExecutionCommand executionCommand;

        public AbstractExecutionDecorator(ExecutionCommand executionCommand) {
            this.executionCommand = executionCommand;
        }

        @Override
        public void execute() {
            //warm up
//            System.out.println("warm up");
//            for (int i = 0; i < 100; i++) {
//                run();
//            }

            System.out.println(this.getClass());
            long start = System.currentTimeMillis();
            System.out.println("starting to execute");
            for (long i = 0; i < 100; i++) {
                run();
            }
            long runningTime = System.currentTimeMillis() - start;
            System.out.println("running time, ms: " + runningTime);
            run();
        }

        protected abstract void run();
    }

    private static class SimpleExecutionDecorator extends AbstractExecutionDecorator {

        public SimpleExecutionDecorator(ExecutionCommand executionCommand) {
            super(executionCommand);
        }

        @Override
        public void run() {
            executionCommand.execute();
        }
    }

    private static class SyncExecutionDecorator extends AbstractExecutionDecorator {

        public SyncExecutionDecorator(ExecutionCommand executionCommand) {
            super(executionCommand);
        }

        @Override
        public void run() {
            synchronized (this) {
                executionCommand.execute();
            }
        }
    }

    private static class LockExecutionDecorator extends AbstractExecutionDecorator {
        private final Lock lock = new ReentrantLock();

        public LockExecutionDecorator(ExecutionCommand executionCommand) {
            super(executionCommand);
        }

        @Override
        public void run() {
            lock.lock();
            executionCommand.execute();
            lock.unlock();
        }

    }

    //todo: add more precise warm up
    public static void main(String[] args) {
        new SyncExecutionDecorator(new ExecutionCommand<>()).execute();
        new LockExecutionDecorator(new ExecutionCommand<>()).execute();
        new SimpleExecutionDecorator(new ExecutionCommand<>()).execute();
    }
}
