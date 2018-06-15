package puzzler.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * Return the minimum cuts needed for a palindrome partitioning of s.
 */
public class PalindromePartitioningII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"aab", 1},
                {"aabaa", 0},
                {"aabba", 1},
                {"aaaaaaaaaaaaaaaaaaaaaaaaa", 0},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int num) {
        assertEquals(minCut(s), num);
    }

    public int minCut(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        int L = s.length();
        char[] S = s.toCharArray();

        // PalI [i][j] = true means s[i..j] is a palindrome
        boolean PalI[][] = new boolean[L][L];
        // MinCuts[i] = min cut for s[0..i]
        int MinCuts[] = new int[L];

        // l - size of sliding window
        for (int l = 1; l <= L; l++) {
            for (int i = 0; i <= L - l; i++) {
                if (l == 1) {
                    // single el is palindrome for sure
                    PalI[i][i] = true;
                } else if (l == 2) {
                    // 2 el is palindrome if elements are the same
                    if (S[i] == S[i + 1]) {
                        PalI[i][i + 1] = true;
                    }
                } else {
                    // abba is a palindrome if b=b and aa is a palindrome
                    int j = i + l - 1;
                    if (S[i] == S[j] && PalI[i + 1][j - 1]) {
                        PalI[i][j] = true;
                    }
                }
            }
        }

        for (int j = 0; j < L; j++) {
            // if s[0..j] is palindrom -> S[j] = 0 - zero cuts
            if (PalI[0][j]) {
                MinCuts[j] = 0;
            } else {
                // let's do cuts to the left of j finding min cuts
                MinCuts[j] = Integer.MAX_VALUE;
                for (int i = j - 1; i >= 0; i--) {
                    if (PalI[i + 1][j]) {
                        if (MinCuts[i] + 1 < MinCuts[j]) {
                            MinCuts[j] = MinCuts[i] + 1;
                        }
                    }
                }
            }
        }

        // return min cuts for last element
        return MinCuts[L - 1];
    }
}
