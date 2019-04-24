package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * LCS Problem Statement: Given two sequences, find the length of longest subsequence present in both of them. A subsequence is a
 * sequence that appears in the same relative order, but not necessarily contiguous. For example, “abc”, “abg”, “bdf”, “aeg”, ‘”acefg”, .
 * . etc are subsequences of “abcdefg”
 */
public class LongestCommonSubsequence {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"a", "a", 1},
                {"abc", "ac", 2},
                {"abcr", "zbck", 2},
                {"abba", "bb", 2},
                {"aba", "bb", 1},
                {"acdb", "abcd", 3}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String a, String b, int count) {
        assertEquals(getLongestCommonSubsequence(a, b), count);
    }

    /**
     * Dynamic solution
     * lcs[a+1][b+1] - is max num of LCS
     *
     * if(a[i]=b[j]) lcs[a+1][b+1] = lcs[a+1][b+1] = lcs[a][b] + 1
     * else lcs[a+1][b+1] = max(lcs[a][b+1], lcs[a+1][b])
     */
    public int getLongestCommonSubsequence(String a, String b) {
        int[][] lcs = new int[a.length() + 1][b.length() + 1];

        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();

        for (int i = 0; i <= charsA.length; i++) {
            for (int j = 0; j <= charsB.length; j++) {
                if (i == 0 || j == 0) {
                    lcs[i][j] = 0;
                } else if (charsA[i - 1] == charsB[j - 1]) {
                    lcs[i][j] = lcs[i - 1][j - 1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
                }
            }
        }

        return lcs[a.length()][b.length()];
    }
}
