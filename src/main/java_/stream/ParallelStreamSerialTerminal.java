package java_.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class ParallelStreamSerialTerminal {

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
                .skip(1)
                .map(i -> {
                    System.out.println("Map2 over element " + i + " with thread: " + Thread.currentThread().getName());
                    return i;
                })
                .collect(Collectors.toList());
    }
}
