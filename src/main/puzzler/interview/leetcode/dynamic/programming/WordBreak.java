package puzzler.interview.leetcode.dynamic.programming;

import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * Given a string s and a dictionary of words dict, determine if s can be segmented into
 * a space-separated sequence of one or more dictionary words.
 * <p>
 * For example, given
 * s = "leetcode",
 * dict = ["leet", "code"].
 * <p>
 * Return true because "leetcode" can be segmented as "leet code".
 */
public class WordBreak {

    public static void main(String[] args) {
        WordBreak w = new WordBreak();

        System.out.println(w.wordBreak("cars", of("ca", "car", "rs")));
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"aba", of("a", "ba"), true},
                {"aba", of("a", "bab"), false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, List<String> dict, boolean test) {
        assertEquals(wordBreak(s, dict), test);
    }

    public boolean wordBreak(String s, List<String> wordDict) {
//        return wordBreakRecursive(s, wordDict);
        return wordBreakDynamic(s, wordDict);
    }

    /**
     * Dynamic O(N * dictLength)
     * <p>
     * pos[i] = true if s.substr(0, i) can break
     */
    private boolean wordBreakDynamic(String s, List<String> wordDict) {

        boolean[] pos = new boolean[s.length() + 1];
        pos[0] = true;

        for (int i = 0; i < s.length(); i++) {
            if (!pos[i])
                continue;

            for (String w : wordDict) {
                if (w.length() + i > s.length())
                    continue;

                if (pos[i + w.length()])
                    continue;

                if (s.substring(i, i + w.length()).equals(w)) {
                    pos[i + w.length()] = true;
                }
            }
        }

        return pos[s.length()];
    }

    /**
     * recursive O(N^2)
     */
    private boolean wordBreakRecursive(String s, List<String> dict) {
        return checkWordBreak(dict, s, 0);
    }

    private boolean checkWordBreak(List<String> dict, String s, int nextI) {
        if (nextI == s.length()) {
            return true;
        }

        for (String dW : dict) {
            if (dW.length() + nextI > s.length()) {
                continue;
            }

            if (s.substring(nextI, dW.length() + nextI).equals(dW)) {
                if (checkWordBreak(dict, s, nextI + dW.length())) {
                    return true;
                }
            }
        }

        return false;
    }
}
