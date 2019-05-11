package puzzler.leetcode.all;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * Given a string S and a string T, find the minimum window
 * in S which will contain all the characters in T in complexity O(n)
 *
 * aka: Pangram of min length
 */
public class MinimumWindowSubstringOnAlphabet {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"ADOBECODEBANC", "ABC", "BANC"},
                {"a", "a", "a"},
                {"aaaaa", "a", "a"},
                {"aaaaabc", "abc", "abc"},
                {"bbbbbba", "a", "a"},
                {"abbbbb", "a", "a"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, String t, String result) {
        assertEquals(slidingWindow(s, t), result);
    }

    /**
     * sliding window
     * O(N)
     */
    public String slidingWindow(String s, String t) {
        String result = "";
        if (t == null || t.length() == 0 || s == null || s.length() == 0) {
            return result;
        }

        Set<Character> alphabet = new HashSet<>();
        for (Character ch : t.toCharArray()) {
            alphabet.add(ch);
        }

        int targetCharsFromAlphabetCount = alphabet.size();
        int windowCharsFromAlphabetCount = 0;

        Map<Character, Integer> windowCharsFromAlphabet = new HashMap<>();

        int minLength = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;

        char[] sChars = s.toCharArray();
        if (alphabet.contains(sChars[0])) {
            windowCharsFromAlphabet.put(sChars[0], 1);
            windowCharsFromAlphabetCount++;
        }

        while (true) {

            if (windowCharsFromAlphabetCount == targetCharsFromAlphabetCount) {
                String potResult = s.substring(left, right + 1);
                if (potResult.length() < minLength) {
                    minLength = potResult.length();
                    result = potResult;
                }

                if (left == sChars.length - alphabet.size()) {
                    break;
                } else {
                    Character charLeft = sChars[left];
                    if (alphabet.contains(charLeft)) {
                        Integer count = windowCharsFromAlphabet.get(charLeft);
                        count -= 1;
                        if (count == 0) {
                            windowCharsFromAlphabetCount--;
                        }
                        windowCharsFromAlphabet.put(charLeft, count);
                    }
                    left += 1;
                }
            } else {
                if (right == sChars.length - 1) {
                    break;
                } else {
                    right += 1;
                    Character charRight = sChars[right];
                    if (alphabet.contains(charRight)) {
                        Integer count = windowCharsFromAlphabet.get(charRight);
                        if (count == null || count == 0) {
                            windowCharsFromAlphabetCount++;
                            count = 1;
                        } else {
                            count += 1;
                        }

                        windowCharsFromAlphabet.put(charRight, count);
                    }
                }
            }
        }

        return result;
    }
}
