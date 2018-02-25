package java_.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class ParallelStreamParallelTerminal {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int n = 4;
        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        list.stream()
                .parallel()
                .map(i -> {
                    System.out.println("Map1 over element " + i + " with thread: " + Thread.currentThread().getName());
                    return i;
                })
                .map(i -> {
                    System.out.println("Map2 over element " + i + " with thread: " + Thread.currentThread().getName());
                    return i;
                })
                .forEach(i -> System.out.println("Element " + i + " finished"));
    }
}
