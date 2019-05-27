package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Given a string, find the length of the longest substring T that contains at most k distinct characters.
 */
public class LongestSubstringWithAtMostKDistinctCharacters {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"eceba", 2, 3},
                {"aa", 2, 2},
                {"aaa", 0, 0},
                {"ab", 0, 0},
                {"ab", 1, 1},
                {"baece", 2, 3},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int k, int res) {
        assertEquals(res, lengthOfLongestSubstringKDistinct(s, k));
    }

    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k == 0) {
            return 0;
        } else {
            return slidingWindowWithCounter(s, k);
        }
    }

    /**
     * first attempt - sliding window with counter if chars in window - update and control char quantites per every symbol
     * ACCEPTED!
     */
    private int slidingWindowWithCounter(String s, int k) {
        int result = 0;

        int distinctCharCounter = 0;
        int[] windowCharFreqs = new int[256];

        int left = 0;
        int right = 0;

        while (right < s.length()) {
            if (distinctCharCounter <= k) {
                if (windowCharFreqs[s.charAt(right)] == 0) {
                    distinctCharCounter++;
                }

                ++windowCharFreqs[s.charAt(right)];
                ++right;

                if (distinctCharCounter <= k) {
                    result = Math.max(result, right - left);
                }
            } else {
                if (windowCharFreqs[s.charAt(left)] == 1) {
                    distinctCharCounter--;
                }

                --windowCharFreqs[s.charAt(left)];
                ++left;
            }
        }

        return result;
    }
}
