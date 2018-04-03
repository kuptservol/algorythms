package puzzler.leetcode.dynamic.programming;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 * <p>
 * Return all possible palindrome partitioning of s.
 * <p>
 * For example, given s = "aab",
 * Return
 * <p>
 * [
 * ["aa","b"],
 * ["a","a","b"]
 * ]
 */
public class PalindromePartitioning {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(of("aa", "b"), of("a", "a", "b")), "aab"},
                {of(of("aa", "a"), of("a", "a", "a"), of("a", "aa"), of("aaa")), "aaa"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<List<String>> list, String s) {
        assertEquals(new HashSet<>(partition(s)), new HashSet<>(list));
    }

    public List<List<String>> partition(String s) {
//        return dfs(s);
        return dynamic(s);
    }

    private List<List<String>> dynamic(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        List<List<String>> palindroms = new ArrayList<>();

        int[][] palindromeT = new int[s.length()][s.length()];
        char[] originalS = s.toCharArray();


        for (int length = 1; length <= s.length(); length++) {
            for (int left = 0; left < s.length(); left++) {
                int right = left + length - 1;
                if (right >= s.length()) {
                    continue;
                }
                if (originalS[left] == originalS[right]) {
                    if (length == 1 || length == 2) {
                        palindromeT[left][right] = 1;
                    } else {
                        //  abba is a palindrome if bb is palindrome and a = a
                        palindromeT[left][right] = palindromeT[left + 1][right - 1];
                    }
                } else {
                    palindromeT[left][right] = 0;
                }
            }
        }

        if (palindromeT[0][0] == 0 || palindromeT[s.length() - 1][s.length() - 1] == 0) {
            return palindroms;
        }

        dfs(palindroms, s, palindromeT, new ArrayList<>(), 0);

        return palindroms;
    }

    private void dfs(List<List<String>> palindroms, String original, int[][] palindromeT, List<String> current, int fromPos) {
        int[] row = palindromeT[fromPos];

        for (int right = fromPos; right < original.length(); right++) {
            if (row[right] == 1) {
                String newAddition = original.substring(fromPos, right + 1);
                List<String> nextPal = new ArrayList<>(current);
                nextPal.add(newAddition);
                if (right == original.length() - 1) {
                    palindroms.add(nextPal);
                } else {
                    dfs(palindroms, original, palindromeT, nextPal, right + 1);
                }
            }
        }
    }

    private List<List<String>> dfs(String s) {
        if (s == null || s.length() == 0) {
            return new ArrayList<>();
        }

        List<List<String>> palindroms = new ArrayList<>();
        dfs(s, 0, palindroms, new ArrayList<>());

        return palindroms;
    }

    private void dfs(String original, int fromSIndex, List<List<String>> palindroms, List<String> current) {
        char[] originalCh = original.toCharArray();

        for (int i = fromSIndex + 1; i < originalCh.length + 1; i++) {
            String newAddition = original.substring(fromSIndex, i);

            boolean isPalindrome = isPalindrome(newAddition);

            if (isPalindrome) {
                List<String> nextPal = new ArrayList<>(current);
                nextPal.add(newAddition);
                if (i == originalCh.length) {
                    palindroms.add(nextPal);
                } else {
                    dfs(original, i, palindroms, nextPal);
                }
            }
        }
    }

    private boolean isPalindrome(String s) {
        if (s.length() == 1) {
            return true;
        }

        char[] chars = s.toCharArray();

        for (int i = 0, j = s.length() - 1; i < s.length() / 2; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }

        return true;
    }
}
