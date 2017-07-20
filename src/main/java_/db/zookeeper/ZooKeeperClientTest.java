package java_.db.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.testng.annotations.Test;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class ZooKeeperClientTest {

    @Test
    public void createNode() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 5000, null);

        zooKeeper.create("/apps", "1".getBytes(), null, CreateMode.PERSISTENT);
    }
}
