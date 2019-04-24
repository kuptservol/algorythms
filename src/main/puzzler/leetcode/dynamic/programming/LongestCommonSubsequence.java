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
                {"aba", "bb", 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String a, String b, int count) {
        assertEquals(getLongestCommonSubsequence(a, b), count);
    }

    public int getLongestCommonSubsequence(String a, String b) {
        int count = 0;

        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();

        int nextAJ = 0;

        for (int i = 0; i < charsB.length; i++) {
            for (int j = nextAJ; j < charsA.length; j++) {
                if (charsB[i] == charsA[j]) {
                    count++;
                    nextAJ = j + 1;
                    break;
                }
            }
        }

        return count;
    }
}
