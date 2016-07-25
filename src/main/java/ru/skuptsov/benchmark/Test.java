package ru.skuptsov.benchmark;

/**
 * @author Sergey Kuptsov
 * @since 18/07/2016
 */
public class Test {

    static Person p1 = new Person();
    static Student p2 = new Student();

    public static void main(String[] args) {
        p1.name = "Ricky";
        p2.name = "Maru";

        System.out.println(p1.toString() + p2.name);
    }

    public static class Person {
        public String name = "2";

        @Override
        public String toString() {
            return name;
        }
    }

    public static class Student extends Person {
        public String name = "1";

        @Override
        public String toString() {
            return name;
        }
    }
}
