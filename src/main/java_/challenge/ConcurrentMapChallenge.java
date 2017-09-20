package java_.challenge;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class ConcurrentMapChallenge {

    public static void main(String[] args) {
        ConcurrentMap<String, Object> map = new ConcurrentHashMap<>();

        map.put("111", new Object());
        map.putIfAbsent("111", "LOL");
        map.put("111", new Integer(4));
        map.put("222", new Integer(4));

        System.out.println(map);
    }
}
