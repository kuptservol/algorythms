package ru.skuptsov.algorythms.parser;

import java.util.Scanner;

/**
 * @author Sergey Kuptsov
 * @since 14/05/2016
 */
public class PeekableScanner {

    private Scanner scanner;
    private String next;

    public PeekableScanner(Scanner scanner) {
        this.scanner = scanner;
        next();
    }

    public boolean hasNext() {
        return next != null;
    }

    public String next() {
        String current = next;
        next = scanner.hasNext() ? scanner.next() : "EMPTY";
        return current;
    }

    public String peek() {
        return next;
    }
}
