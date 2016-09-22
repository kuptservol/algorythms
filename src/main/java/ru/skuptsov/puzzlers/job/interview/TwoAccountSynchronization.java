package ru.skuptsov.puzzlers.job.interview;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Sergey Kuptsov
 * @since 22/09/2016
 */
public class TwoAccountSynchronization {

    public static void transferNotSafe(Account from, Account to, Integer amount) {
        if (from.isSufficientFunds(amount)) {
            from.minus(amount);
            to.add(amount);
        }
        check(from, to, 2000);
    }

    public static void transferDeadLock(Account from, Account to, Integer amount) {
        synchronized (from) {
            synchronized (to) {
                System.out.println("Transfer");
                if (from.isSufficientFunds(amount)) {
                    from.minus(amount);
                    to.add(amount);
                }
                check(from, to, 2000);
            }
        }
    }

    public static void transferSimpleBlocking(Account from, Account to, Integer amount) {
        synchronized (TwoAccountSynchronization.class) {
            if (from.isSufficientFunds(amount)) {
                from.minus(amount);
                to.add(amount);
//                System.out.println("Transfer");
            }
            check(from, to, 2000);
        }
    }

    public static void transferAccLockBlocking(Account from, Account to, Integer amount) {
        System.out.println("transferAccLockBlocking started");

        Lock lockFrom = from.lock;
        Lock lockTo = to.lock;

        while (true) {
            try {
                if (lockFrom.tryLock() && lockTo.tryLock()) {
                    if (from.isSufficientFunds(amount)) {
                        from.minus(amount);
                        to.add(amount);
                        System.out.println("Transfered " + amount);
                    }
                    check(from, to, 2000);
                    break;
                }
            } finally {
                lockFrom.unlock();
                lockTo.unlock();
            }
        }

        System.out.println("transferAccLockBlocking finished");
    }

    public static void check(Account account1, Account account2, int balanceOverall) {
        int systemBalance = account1.balance + account2.balance;
        if (systemBalance != balanceOverall) {
            System.out.println("Error balance overal!= " + balanceOverall + ";systemBalance: " + systemBalance);
        }
    }

    public static void main(String[] args) {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);
        UnstoppableTransfer unstoppableTransfer1 = new UnstoppableTransfer(account1, account2);
        UnstoppableTransfer unstoppableTransfer2 = new UnstoppableTransfer(account2, account1);

        int num_threads = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(num_threads);
        for (int i = 0; i < num_threads; i++) {
            executorService.submit(unstoppableTransfer1);
            executorService.submit(unstoppableTransfer2);
        }
    }

    private static class Account {
        Lock lock;
        private Integer balance;

        public Account(Integer balance) {
            this.balance = balance;
            lock = new ReentrantLock();
        }

        public Integer getBalance() {
            return balance;
        }

        public void add(Integer amount) {
            balance += amount;
        }

        public boolean isSufficientFunds(Integer amount) {
            return balance >= amount;
        }

        public void minus(Integer amount) {
            if (!isSufficientFunds(amount)) {
                throw new IllegalArgumentException();
            }
            balance -= amount;
        }
    }

    private static class UnstoppableTransfer implements Runnable {
        private final Account account1;
        private final Account account2;
        Random r = new Random();

        private UnstoppableTransfer(Account account1, Account account2) {
            this.account1 = account1;
            this.account2 = account2;
        }

        @Override
        public void run() {
            while (true) {
                transferAccLockBlocking(account1, account2, r.nextInt(100));
            }
        }
    }

}
