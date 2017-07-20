package java_.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Lock;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.Striped;

import static com.google.common.util.concurrent.Striped.lock;

/**
 * @author Sergey Kuptsov
 * @since 22/11/2016
 */
public class MonitorSyncObjectEqualityTest {

    private final static Striped<Lock> locksPool = lock(2);

    public static void runWithLocks(User user) {
        Lock lock = locksPool.get(user);
        lock.lock();
        System.out.println("Entered first");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }

    public static void runSync(User user) {
        synchronized (user.id) {
            System.out.println("Entered first");
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        run();
    }

    protected static void runWithLocks() throws InterruptedException, ExecutionException {
        User user1 = new User(10000000L);
        User user2 = new User(10000000L);

        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> runWithLocks(user1));
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> runWithLocks(user2));

        f1.get();
        f2.get();
    }

    protected static void run() throws InterruptedException, ExecutionException {
        User user1 = new User(10000000L);
        User user2 = new User(10000000L);

        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> runSync(user1));
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() -> runSync(user2));

        f1.get();
        f2.get();
    }

    private static final class User {
        private final Long id;

        private User(Long id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            System.out.println("running equals");
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equal(id, user.id);
        }

        @Override
        public int hashCode() {
            System.out.println("running hashCode");
            return Objects.hashCode(id);
        }
    }
}
