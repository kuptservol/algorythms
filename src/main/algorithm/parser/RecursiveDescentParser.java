package algorithm.parser;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.DoubleBinaryOperator;

/**
 * @author Sergey Kuptsov
 * @since 14/05/2016
 * <p>
 * BNF :
 * 1 + 2 * ((3 + 5 + 2) + 5 + 7*8) * 3
 * <p>
 * BNF:
 * statement = expression
 * expression = term {("+" | "-") term}
 * term = factor { ("*" | "/") factor}
 * factor = number | "(" expression ")"
 */
public class RecursiveDescentParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(new ByteArrayInputStream("1 + 2 * ( ( 3 + 5 + 2 ) + 5 + 7 * 8 ) * 3".getBytes()));

        Statement statement = new Statement(scanner);
        Node exp = statement.buildExpression();
        System.out.println(exp.execute());
    }

    private enum Operation {
        PLUS("+", (left, right) -> left + right),
        MINUS("-", (left, right) -> left - right),
        MULTIPLY("*", (left, right) -> left * right),
        DIVIDE("/", (left, right) -> left / right);

        private final String op;
        private final DoubleBinaryOperator evaluateOperator;

        Operation(String operationChar, DoubleBinaryOperator operator) {
            this.op = operationChar;
            this.evaluateOperator = operator;
        }

        public static Operation resolveOperation(String ch) {
            return Arrays.stream(Operation.values())
                    .filter(operation -> operation.op.equals(ch))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new);
        }
    }

    public abstract static class Node {
        protected Node left;
        protected Node right;

        public abstract double execute();
    }

    private static class Number extends Node {

        private final double number;

        private Number(String number) {
            this.number = Double.valueOf(number);
        }

        public static boolean isNumber(String number) {
            try {
                Double.valueOf(number);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        @Override
        public double execute() {
            return number;
        }
    }

    private static class Evaluation extends Node {
        private final Operation operation;

        private Evaluation(Operation operation, Node left, Node right) {
            this.operation = operation;
            this.left = left;
            this.right = right;
        }

        @Override
        public double execute() {
            return operation.evaluateOperator.applyAsDouble(left.execute(), right.execute());
        }
    }

    public final static class Statement {
        private final PeekableScanner in;

        public Statement(Scanner in) {
            this.in = new PeekableScanner(in);
        }

        public Node statementTree() {
            return buildExpression();
        }

        private Node buildExpression() {

            Node nextLeftTerm = buildTerm();

            while (in.peek().equals(Operation.PLUS.op) || in.peek().equals(Operation.MINUS.op)) {
                Operation operation = Operation.resolveOperation(in.next());
                Node nextRightTerm = buildTerm();
                nextLeftTerm = new Evaluation(operation, nextLeftTerm, nextRightTerm);
            }

            return nextLeftTerm;
        }

        private Node buildTerm() {

            Node nextLeftFactor = buildFactor();

            while (in.peek().equals(Operation.MULTIPLY.op) || in.peek().equals(Operation.DIVIDE.op)) {
                Operation operation = Operation.resolveOperation(in.next());
                Node nextRightFactor = buildFactor();
                nextLeftFactor = new Evaluation(operation, nextLeftFactor, nextRightFactor);
            }

            return nextLeftFactor;
        }

        private Node buildFactor() {

            String nextToken = in.peek();
            if (Number.isNumber(nextToken)) {
                return new Number(in.next());
            } else if (nextToken.equals("(")) {
                in.next();
                Node node = buildExpression();
                if (!in.peek().equals(")")) {
                    throw new IllegalArgumentException();
                }
                in.next();
                return node;
            } else
                throw new IllegalArgumentException();
        }
    }
}
