package ru.skuptsov.patterns.structural.flyweight;

import java.util.EnumSet;
import java.util.WeakHashMap;

/**
 * Created by Сергей on 25.04.2016.
 */
public class StringConstantsPool {


    public static void main(String[] args) {

        String not_in_pool = new String("not_in_pool");
        String not_in_pool2 = new String("not_in_pool");

        System.out.println(not_in_pool==not_in_pool2);

        String in_pool = "string_in_pool";
        String in_pool2 = "string_in_pool";

        System.out.println(in_pool == in_pool2);

        StringConstantsPool constantsPool = new StringConstantsPool();
    }
}
