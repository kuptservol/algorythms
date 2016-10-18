package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedList;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 19/10/2016
 * Given a string containing just the characters '(', ')',
 * '{', '}', '[' and ']', determine if the input string is valid.
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 */
public class ValidParenthesis {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"[[([])]]", true},
                {"[[([))]]", false}
        };
    }

    private Boolean isValid(String s) {
        LinkedList<Character> stack = new LinkedList<>();

        for (char next : s.toCharArray()) {
            if (next == '(' || next == '[') {
                stack.addFirst(next);
            } else if (next == ')') {
                if (stack.pollFirst() != '(')
                    return false;
            } else if (next == ']') {
                if (stack.pollFirst() != '[')
                    return false;
            }
        }

        return stack.size() == 0;
    }

    @Test(dataProvider = "testData")
    public void test(String s, Boolean valid) {
        assertEquals(isValid(s), valid);
    }
}
