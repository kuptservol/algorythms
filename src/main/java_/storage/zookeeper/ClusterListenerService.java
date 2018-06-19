package java_.db.zookeeper;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Kuptsov
 */
@Configuration
public class ClusterListenerService {

    int instance;
    private ExecutorService executorService;
    private ZooKeeper zooKeeper;

    public static void main(String[] args) throws KeeperException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ClusterListenerService.class);

        int instance = scanner.nextInt();
        ClusterListenerService bean = applicationContext.getBean(ClusterListenerService.class);

        bean.instance = instance;

        bean.start();
    }

    public void start() throws KeeperException, InterruptedException {

        zooKeeper.create("/apps/cluster/" + instance, "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        executorService.submit(() -> {
                    while (true) {

                        System.out.println("Server loop instance [" + instance + "]");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    @PostConstruct
    public void init() throws IOException, KeeperException, InterruptedException {
        System.out.println("Starting instance [" + instance + "]");
        executorService = Executors.newSingleThreadExecutor();
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, null);

        zooKeeper.getChildren("/apps/cluster", new ClusterWatcher());
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Stopping instance [" + instance + "]");
        executorService.shutdown();
    }

    public class ClusterWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println("get children event" + event);
            try {
                System.out.println(zooKeeper.getChildren("/apps/cluster", false));
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                zooKeeper.getChildren("/apps/cluster", new ClusterWatcher());
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
