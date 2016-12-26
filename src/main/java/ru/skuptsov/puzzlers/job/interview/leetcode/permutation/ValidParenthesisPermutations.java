package ru.skuptsov.puzzlers.job.interview.leetcode.permutation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static com.google.common.collect.ImmutableSet.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 03/12/2016
 * <p>
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
 * <p>
 * Solution1: build all permutation tree, make DFS constructing string with all permutations, check if it is valid
 * Solution2: build all permutation with recursive adding(virtual tree) '(' and ')', check if it is valid
 */
public class ValidParenthesisPermutations {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {3, of("((()))", "(()())", "(())()", "()(())", "()()()")}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, Set<String> validCombinations) {
        assertEquals(getAllValidPermutationsRecursive(n), validCombinations);
//        assertEquals(getAllValidPermutationsTree(n), validCombinations);
    }

    private Set<String> getAllValidPermutationsRecursive(int n) {
        Set<String> validParenthesis = new HashSet<>();
        buildLineWithNextEl(n, "(", validParenthesis);

        return validParenthesis;
    }

    private void buildLineWithNextEl(int maxRecursionDepth, String currentLine, Set<String> validParenthesis) {
        if (currentLine.length() == maxRecursionDepth * 2) {
            if (isParenthesisValid(currentLine)) {
                validParenthesis.add(currentLine);
                return;
            }
        } else {
            buildLineWithNextEl(maxRecursionDepth, currentLine + "(", validParenthesis);
            buildLineWithNextEl(maxRecursionDepth, currentLine + ")", validParenthesis);
        }
    }

//    private Set<String> getAllValidPermutationsTree(int n) {
//        return 0;
//    }

    private boolean isParenthesisValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] sChars = s.toCharArray();

        for (char sChar : sChars) {
            if (sChar == '(') {
                stack.push(sChar);
            } else {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }

        return stack.size() == 0;
    }
}
