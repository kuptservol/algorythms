package puzzler.leetcode.stringarray;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 *         Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array
 *         such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
 *         <p>
 *         Example 1:
 *         <p>
 *         Input: [1,2,3,1], k = 3
 *         Output: true
 *         Example 2:
 *         <p>
 *         Input: [1,0,1,1], k = 1
 *         Output: true
 *         Example 3:
 *         <p>
 *         Input: [1,2,1], k = 0
 *         Output: false
 */
public class ContainsDuplicateII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 2, 3, 1), 3, true},
                {intA(1, 2, 3, 2, 1, 1, 1), 2, true},
                {intA(1, 0, 1, 1), 1, true},
                {intA(1, 2, 1), 0, false},
                {intA(1, 2, 1), 1, false},
                {intA(99, 99), 2, true},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, int dist, boolean res) {
        assertEquals(containsNearbyDuplicate(nums, dist), res);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (k == 0 || nums.length <= 1) {
            return false;
        }

        Map<Integer, Set<Integer>> posToVal = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int next = nums[i];
            final int l = i;
            Optional<Integer> min = posToVal.getOrDefault(next, new HashSet<>()).stream().min((o1, o2) -> o2 - o1);
            if (min.isPresent() && i - min.get() - 1 < k) {
                return true;
            }

            Set<Integer> pos = posToVal.get(next);
            if (pos == null) {
                pos = new HashSet<>();
                pos.add(i);
                posToVal.put(next, pos);
            } else {
                pos.add(i);
            }
        }

        return false;
    }
}
