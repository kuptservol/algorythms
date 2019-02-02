package puzzler.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *
 * Given a string, find the length of the longest substring without repeating characters
 */
public class LongestSubstringWithoutRepeatingCharacters {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"abcabcbb", 3},
                {"bbbbb", 1},
                {"pwwkew", 3},
                {"a", 1},
                {"dvdf", 3},
                {"abccccdaf", 4},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int res) {
        assertEquals(lengthOfLongestSubstring(s), res);
    }

    /**
     * Try increase sliding window with no repeat sequence - jumping left 1 after position found repeating char immediately
     * For that indexing found char-> position in index
     * Some character can be repeated before left - so getting nearest pos - ignoring it
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }

        int maxSeqLen = 0;
        int[] chToPosIndex = new int[128];

        char[] chars = s.toCharArray();
        for (int right = 0, left = 0; right < chars.length; right++) {
            int nextCh = chars[right];

            // if already seen nextCh - check if it's in window - if in window - move left next to this pos
            if (chToPosIndex[nextCh] != 0) {
                int lastSeenChPos = chToPosIndex[nextCh];
                // do not update left if lastSeenChPos is not in window
                left = Math.max(left, lastSeenChPos);
            }

            chToPosIndex[nextCh] = right + 1;

            maxSeqLen = Math.max(right - left + 1, maxSeqLen);
        }

        return maxSeqLen;
    }
}
