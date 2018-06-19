package java_.challenge;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Kuptsov
 */
public class GenericList {

    public static void main(String[] args) {
        printList(new ArrayList<>());
    }

    private static void printList(List<? extends Object> list) {
        //list.add(7);
    }
}
