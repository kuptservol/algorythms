package ru.skuptsov.puzzlers.job.interview.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.Objects;

import static java.lang.Math.max;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 19/10/2016
 * <p>
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.
 * <p>
 * For "(()", the longest valid parentheses substring is "()", which has length = 2.
 * Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.
 */
public class LongestValidParenthesis {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"(()", 2},
                {")()())", 4},
                {"(((())))", 8},
                {"(())(()", 6},
                {"())()()", 4}
        };
    }

    private Integer getValidParenthesisLength(String s) {
        LinkedList<Character> stack = new LinkedList<>();
        int maxLength = 0;
        int curLength = 0;

        for (char next : s.toCharArray()) {
            if (next == '(') {
                stack.addFirst(next);
            } else if (next == ')') {
                if (!Objects.equals(stack.pollFirst(), '(')) {
                    curLength = 0;
                } else {
                    curLength += 2;
                }
            }

            maxLength = max(maxLength, curLength);
        }

        return maxLength;
    }

    @Test(dataProvider = "testData")
    public void test(String s, Integer correct) {
        assertEquals(getValidParenthesisLength(s), correct);
    }
}
