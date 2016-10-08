package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 08/10/2016
 * Implement regular expression matching with support for '.' and '*'.
 * '.' Matches any single character.
 * '*' Matches zero or more of the preceding element.
 * <p>
 * The matching should cover the entire input string (not partial).
 */
public class RegularExpressionMatching {

    private static boolean isMatch(String or, String pat) {
        int i = 0;
        int j = 0;
        char prevStarChar = 0;

        while (i < or.length()) {
            if (prevStarChar != 0 && (prevStarChar == '.' || or.charAt(i) == prevStarChar)) {
                i++;
            } else if (j < pat.length() &&
                    (or.charAt(i) == pat.charAt(j) || pat.charAt(j) == '.')) {
                i++;
                j++;
            } else if (j < pat.length() && pat.charAt(j) == '*') {
                prevStarChar = pat.charAt(j - 1);
                j++;
            } else if (j < pat.length() && prevStarChar != 0) {
                prevStarChar = 0;
            } else if (j < pat.length() - 1 && pat.charAt(j + 1) == '*') {
                if (i < or.length() - 1) {
                    i++;
                }
                j++;
            } else {
                return false;
            }
        }

        return j == pat.length();
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"aa", "a"},
                {"aa", "aa"},
                {"aaa", "aa"},
                {"aa", "a."},
                {"aab", "a."},
                {"aa", "a*"},
                {"aaaaaaaaa", "a*"},
                {"aa", ".*"},
                {"aa", ".."},
                {"ab", ".*"},
                {"ab", "a*"},
                {"aab", "c*a*b"},
                {"aab", "c*a*cbaab"},
                {"d", "c*a*d"},
                {"c", "c*a*c"},//does not works - greedy not resolved
                {"cbaab", "c*a*cbaab"}, //does not works - greedy not resolved
                {"baab", "c*a*cbaab"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String data, String pattern) {
        assertEquals(isMatch(data, pattern), Pattern.matches(pattern, data));
    }
}
