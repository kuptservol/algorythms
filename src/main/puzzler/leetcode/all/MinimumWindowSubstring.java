package puzzler.leetcode.all;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Given a string S and a string T, find the minimum window
 * in S which will contain all the characters in T in complexity O(n)
 *
 * Same as MinimumWindowSubstringOnAlphabet but using not alphabet of unique chars but all elements of string
 */
public class MinimumWindowSubstring {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"aabc", "abc", "abc"},
                {"a", "a", "a"},
                {"a", "aa", ""},
                {"ADOBECODEBANC", "ABC", "BANC"},
                {"aaaaa", "a", "a"},
                {"bbbbbba", "a", "a"},
                {"abbbbb", "a", "a"},
                {"bbabbb", "a", "a"},
                {"bbabbb", "a", "a"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, String t, String result) {
        assertEquals(charsCountComparisonInSlidingWindow(s, t), result);
    }

    /**
     * len(s) = L
     * len(t) = l
     *
     * O(l + 2*L*Alphabet_size)
     *
     * Passed
     */
    public String charsCountComparisonInSlidingWindow(String s, String t) {
        String result = "";
        if (t == null || t.length() == 0 || s == null || s.length() == 0 || s.length() < t.length()) {
            return result;
        }

        Map<Character, Integer> tCharCounts = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            tCharCounts.compute(t.charAt(i), (k, v) -> v == null ? 1 : ++v);
        }

        int minL = Integer.MAX_VALUE;
        Map<Character, Integer> sCharCounts = new HashMap<>();
        int left = 0;
        int right = 0;

        while (true) {
            int cmpResult = compare(sCharCounts, tCharCounts);
            if (cmpResult == 0) {
                String tmpResult = s.substring(left, right);
                if (tmpResult.length() < minL) {
                    minL = tmpResult.length();
                    result = tmpResult;
                }

                sCharCounts.computeIfPresent(s.charAt(left), (k, v) -> --v);
                left++;
            } else {
                if (right == s.length()) {
                    break;
                }

                right++;
                sCharCounts.compute(s.charAt(right - 1), (k, v) -> v == null ? 1 : ++v);
            }
        }

        return result;
    }

    private int compare(Map<Character, Integer> sCharCounts, Map<Character, Integer> tCharCounts) {
        for (Map.Entry<Character, Integer> tCharCountsEntry : tCharCounts.entrySet()) {
            if (!sCharCounts.containsKey(tCharCountsEntry.getKey()) ||
                    sCharCounts.get(tCharCountsEntry.getKey()) < tCharCountsEntry.getValue())
            {
                return -1;
            }
        }

        return 0;
    }
}
