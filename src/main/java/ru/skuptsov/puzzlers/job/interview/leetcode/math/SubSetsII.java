package ru.skuptsov.puzzlers.job.interview.leetcode.math;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEqualsNoOrder;
import static ru.skuptsov.puzzlers.Utils.toIntArray;

/**
 * Given a set of distinct integers, nums, return all possible subsets.
 * <p>
 * Note: The solution set must not contain duplicate subsets.
 * <p>
 * For example,
 * If nums = [1,2], a solution is:
 * <p>
 * [
 * [3],
 * [1],
 * [2],
 * [1,2,3],
 * [1,3],
 * [2,3],
 * [1,2],
 * []
 * ]
 */
public class SubSetsII {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toIntArray(of(1, 2, 3)), of(of(3), of(1), of(2), of(1, 2, 3), of(1, 3), of(2, 3), of(1, 2), of())},
                {toIntArray(of(3, 2, 4, 1)), of(of(), of(3), of(2), of(2, 3), of(4), of(3, 4), of(2, 4), of(2, 3, 4), of(1), of(1, 3), of(1, 2), of(1, 2, 3), of(1, 4), of(1, 3, 4), of(1, 2, 4), of(1, 2, 3, 4))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] nums, List<List<Integer>> correctResult) {
        List<List<Integer>> subsets = subsetsWithDup(nums);
        System.out.println(subsets);
        assertEqualsNoOrder(subsets.toArray(), correctResult.toArray());
    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Set<List<Integer>> subsets = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            List<List<Integer>> tmp = new ArrayList<>();

            for (List<Integer> subset : subsets) {
                tmp.add(new ArrayList<>(subset));
            }


            for (List<Integer> lists : tmp) {
                lists.add(nums[i]);
            }

            List<Integer> n = new ArrayList<>();
            n.add(nums[i]);
            tmp.add(n);

            subsets.addAll(tmp);
        }

        subsets.add(Collections.emptyList());

        return new ArrayList<>(subsets);
    }
}