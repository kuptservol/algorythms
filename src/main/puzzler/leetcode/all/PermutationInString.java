package puzzler.leetcode.all;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class PermutationInString {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"ab", "eidbaooo", true},
                {"ab", "eidboaoo", false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s1, String s2, boolean result) {
        assertEquals(checkInclusionCharPartSum(s1, s2), result);
    }

    /**
     * Idea is to mark s1 as map of partial counts of chars on every char of string
     * Then slide with s2 window comparing char counts
     * mem: O(s2^2)
     * sp: O(s2-s1)
     *
     * ACCEPTED
     */
    public boolean checkInclusionCharPartSum(String s1, String s2) {
        boolean result = false;

        Map<Character, Integer> c1Counts = new HashMap<>();
        for (char c : s1.toCharArray()) {
            c1Counts.compute(c, (k, v) -> v == null ? 1 : v + 1);
        }

        Map<Integer, Map<Character, Integer>> c2Counts = new HashMap<>();
        Map<Character, Integer> prev = new HashMap<>();

        // collect partial counts per char
        char[] s2Chars = s2.toCharArray();
        for (int i = 0; i < s2Chars.length; i++) {
            prev = new HashMap<>(prev);
            c2Counts.put(i, prev);
            prev.compute(s2Chars[i], (k, v) -> v == null ? 1 : v + 1);
        }

        // slide s1 over s2
        for (int i = 0; i < s2.length() - s1.length()+ 1; i++) {
            int right = i + s1.length() - 1;

            // check
            Map<Character, Integer> c2CountsLeft = i == 0 ? new HashMap<>() : c2Counts.get(i - 1);
            Map<Character, Integer> c2CountsRight = c2Counts.get(right);

            // collect current diff
            Map<Character, Integer> currC2Counts = new HashMap<>();
            for (Map.Entry<Character, Integer> rightC2CountsEntry : c2CountsRight.entrySet()) {
                currC2Counts.put(rightC2CountsEntry.getKey(), rightC2CountsEntry.getValue()
                        - c2CountsLeft.getOrDefault(rightC2CountsEntry.getKey(), 0));
            }

            boolean allMatch = true;

            // compare window lengthes to target c1Counts
            for (Map.Entry<Character, Integer> currC2CountsEntry : currC2Counts.entrySet()) {
                if (currC2CountsEntry.getValue()
                        - c1Counts.getOrDefault(currC2CountsEntry.getKey(), 0) != 0)
                {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                return true;
            }
        }

        return result;
    }
}
