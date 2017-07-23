package puzzler.leetcode.stringarray;

import java.util.Stack;

/**
 * @author Sergey Kuptsov
 * @since 04/09/2016
 * <p>
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, /. Each operand may be an integer or another expression. For example:
 * <p>
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 */
public class ReversePolishNotation {

    static String[] not1 = {"2", "1", "+", "3", "*"};
    static String[] not2 = {"4", "13", "5", "/", "+"};

    public static void main(String[] args) {
        // stack - based
        check(stackBased(not1), 9);
        check(stackBased(not2), 6);
    }

    /**
     * (Deikstra's two stack)
     */
    private static int stackBased(String[] notation) {

        Stack<Double> stack = new Stack<>();

        for (String symbol : notation) {
            if (symbol.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (symbol.equals("-")) {
                stack.push(stack.pop() - stack.pop());
            } else if (symbol.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (symbol.equals("/")) {
                double delitel = stack.pop();
                stack.push(stack.pop() / delitel);
            } else {
                stack.push(Double.valueOf(symbol));
            }
        }

        return stack.pop().intValue();
    }

    static void check(int a, int b) {
        System.out.println(a == b);
    }
}
