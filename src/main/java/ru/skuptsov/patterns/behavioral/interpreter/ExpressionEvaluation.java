package ru.skuptsov.patterns.behavioral.interpreter;

import ru.skuptsov.algorythms.parser.RecursiveDescentParser;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

/**
 * @author Sergey Kuptsov
 * @since 14/05/2016
 */
public class ExpressionEvaluation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new ByteArrayInputStream("1 + 2 * ( ( 3 + 5 + 2 ) + 5 + 7 * 8 ) * 3".getBytes()));

        RecursiveDescentParser.Statement statement = new RecursiveDescentParser.Statement(scanner);
        RecursiveDescentParser.Node exp = statement.statementTree();

        double value = 1 + 2 * ((3 + 5 + 2) + 5 + 7 * 8) * 3;
        System.out.println(value);
        System.out.println(exp.execute());
    }
}
