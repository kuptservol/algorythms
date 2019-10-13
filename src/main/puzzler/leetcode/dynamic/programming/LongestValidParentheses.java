package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.gradle.internal.impldep.org.junit.Assert.assertEquals;

/**
 * Given a string containing just the characters '(' and ')',
 * find the length of the longest valid (well-formed) parentheses substring.
 *
 * @author Sergey Kuptsov
 */
public class LongestValidParentheses {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"(())()", 6},
                {"(()", 2},
                {")()())", 4},
                {")(()))", 4},
                {")", 0},
                {"(", 0},
                {"((", 0},
                {"(((", 0},
                {"()(", 2},
                {"(()())", 6},
                {"(()())(()())", 12}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String seq, int cnt) {
        assertEquals(longestValidParentheses(seq), cnt);
        assertEquals(longestValidParenthesesWithoutMem(seq), cnt);
    }

    /**
     * attempt 1 - dynamic approach
     * first find every () - cause this is a start of correct sequence - then for each grow outside
     * left and right to find correct parenthes over ( "()" ) - if exist - grow further - else stop and go to next ()
     *
     * also we need to track consequitive valid parenthesises
     *
     * time: O(N) - cause every parenthes we go 1 time
     * mem: O(N) - using dynamic array
     */
    public int longestValidParentheses(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        int maxcorrect = 0;
        int[] longestValParLenTrack = new int[s.length()];

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // found () - add prev seq len + 2
                    longestValParLenTrack[i] = 2 + (i >= 2 ? longestValParLenTrack[i - 2] : 0);
                } else if (s.charAt(i - 1) == ')') {
                    // found )) - this seq is correct - if (...num of correct ))  -> (())
                    int numOfLastCorrectLen = longestValParLenTrack[i - 1];
                    if (i - 1 - numOfLastCorrectLen >= 0) {
                        // found corresponding ( for current )
                        if (s.charAt(i - 1 - numOfLastCorrectLen) == '(') {
                            // add current + 2
                            longestValParLenTrack[i] = 2 + numOfLastCorrectLen;
                            // + prev if exists
                            if (i - 1 - numOfLastCorrectLen - 1 >= 0) {
                                longestValParLenTrack[i] += longestValParLenTrack[i - 1 - numOfLastCorrectLen - 1];
                            }
                        }
                    }
                }
            }

            maxcorrect = Math.max(maxcorrect, longestValParLenTrack[i]);
        }

        return maxcorrect;
    }

    /**
     * Without mem
     * first scan from left to right then another direction
     * if curr == ( - then left ++ if curr == ) then right ++ if left == right - len of str is max
     * speed: O(N)
     * mem: O(1)
     */
    public int longestValidParenthesesWithoutMem(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        int maxcorrect = 0;
        int left = 0;
        int right = 0;
        // (()())
        // one direction
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxcorrect = Math.max(right * 2, maxcorrect);
            } else if (right > left) {
                right = left = 0;
            }
        }

        // another direction
        right = left = 0;
        // (()
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ')') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxcorrect = Math.max(right * 2, maxcorrect);
            } else if (right > left) {
                right = left = 0;
            }
        }

        return maxcorrect;
    }
}
