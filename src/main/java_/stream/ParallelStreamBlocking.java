package java_.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
public class ParallelStreamBlocking {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int n = 2;
        for (int i = 0; i < n; i++) {
            list.add(i);
        }

        System.out.println(
                list.stream()
                        .parallel()
                        .map(i -> {
                            if(i==1){
                                try {
                                    System.out.println("Waiting for heavy element started");
                                    Thread.currentThread().sleep(5000);
                                    System.out.println("Waiting for heavy element finished");
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println("Map over element " + i + " with thread: " + Thread.currentThread().getName());
                            return i;
                        })
                        .findFirst());
    }
}
