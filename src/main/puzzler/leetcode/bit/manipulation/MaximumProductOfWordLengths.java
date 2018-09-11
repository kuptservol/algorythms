package puzzler.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.strA;

/**
 * @author Sergey Kuptsov
 *         Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where the two words do not share common letters.
 *         You may assume that each word will contain only lower case letters. If no such two words exist, return 0.
 */
public class MaximumProductOfWordLengths {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {16, strA("abcw", "baz", "foo", "bar", "xtfn", "abcdef")},
                {4, strA("a", "ab", "abc", "d", "cd", "bcd", "abcd")},
                {0, strA("a", "aa", "aaa", "aaaa")}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int res, String[] words) {
        assertEquals(maxProduct(words), res);
    }

    public int maxProduct(String[] words) {
        int res = 0;

        if (words == null || words.length < 2) {
            return res;
        }

        return maxProductWithBitMaskIndex(words);
    }

    /**
     * First attempt brute force with char indexing as bit representation comparing with bit mask
     * O(N^2)
     */
    private int maxProductWithBitMaskIndex(String[] words) {
        long[] wordsIndex = new long[words.length];

        for (int i = 0; i < words.length; i++) {
            char[] chars = words[i].toCharArray();
            long index = 0;

            for (int j = 0; j < chars.length; j++) {
                index = index | (1 << (chars[j] - 'a' + 1));
            }

            wordsIndex[i] = index;
        }

        int maxProduct = 0;

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i == j) {
                    continue;
                }

                if ((wordsIndex[i] & wordsIndex[j]) == 0) {
                    maxProduct = Math.max(maxProduct, words[i].length() * words[j].length());
                }
            }
        }

        return maxProduct;
    }
}
