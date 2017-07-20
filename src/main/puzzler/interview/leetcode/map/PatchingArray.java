package puzzler.interview.leetcode.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 04/02/2017
 * Given a sorted positive integer array nums and an integer n,
 * add/patch elements to the array such that any number in range [1, n] inclusive
 * can be formed by the sum of some elements in the array. Return the minimum number of patches required.
 * <p>
 * Example 1:
 * nums = [1, 3], n = 6
 * Return 1.
 */
public class PatchingArray {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toArray(of(1, 3)), 6, 1},
                {toArray(of(1, 5, 10)), 20, 2},
                {toArray(of(1, 2, 2)), 5, 0},
                {toArray(of(1, 2, 31, 33)), 2147483647, 0},
                {toArray(of()), 7, 3},
                {toArray(of()), 8, 4},
                {toArray(of(2, 4, 14, 18, 20, 25, 25, 35, 73, 94)), 42, 2},
                {toArray(of(10, 30, 36, 42, 50, 76, 87, 88, 91, 92)), 54, 5},
                {toArray(of(20, 22, 39, 53, 55, 61, 65, 78, 87, 90)), 20, 5},
                {toArray(of(2, 3, 9, 10, 13, 15, 15, 18, 19, 20, 21, 30, 33, 34, 47, 48, 66, 70, 71, 71, 82, 97)), 72, 5}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] dict, int n, int patchesNum) {
//        assertEquals(minPatchesSolution1(dict, n), patchesNum);
        assertEquals(minPatchesSolution2(dict, n), patchesNum);
    }

    public int minPatchesSolution2(int[] dict, int n) {

        long missed = 1;
        int patchesNum = 0;
        int i = 0;

        while (missed <= n) {
            if (i < dict.length && dict[i] <= missed) {
                missed = missed + dict[i];
                i++;
            } else {
                missed += missed;
                patchesNum++;
            }
        }

        return patchesNum;

    }

    public int minPatchesSolution1(int[] dict, int n) {
        Map<Integer, Integer> elemIndex = new HashMap<>();
        int patchesNum = 0;

        if (dict.length == 0) {
            dict = new int[1];
            dict[0] = 1;
            patchesNum++;
        }

        int maxDictEl = dict[dict.length - 1];

        Map<Integer, Boolean> attainabilityMap = new HashMap<>(maxDictEl * 2);

        elemIndex.put(0, 1);

        for (int i = 0; i < dict.length; i++) {
            elemIndex.put(dict[i], elemIndex.computeIfAbsent(dict[i], (a) -> 0) + 1);
        }

        setInitialAttainability(attainabilityMap, elemIndex, 0, n);

        for (int i = 1; i <= Math.min(maxDictEl, n); i++) {
            if (!attainabilityMap.containsKey(i)) {
                patchesNum++;
                reSetAttainability(attainabilityMap, i, n);
            }
        }

        int currMaxSum = attainabilityMap
                .keySet()
                .stream()
                .max(Integer::compareTo)
                .orElse(0);

        if (currMaxSum >= n) {
            return patchesNum;
        }

        while (currMaxSum < n && currMaxSum > 0) {
            patchesNum++;
            currMaxSum = 2 * currMaxSum + 1;
        }

        return patchesNum;
    }

    private void reSetAttainability(Map<Integer, Boolean> attainabilityMap,
            int nextElemIndex,
            int n)
    {

        List<Integer> integers = attainabilityMap.keySet().stream().parallel()
                .collect(Collectors.toList());

        integers.stream().parallel()
                .forEach(v -> {
                    int nextSum = v + nextElemIndex;
                    if (nextSum <= n) {
                        attainabilityMap.put(nextSum, true);
                    }
                });
    }

    private void setInitialAttainability(Map<Integer, Boolean> attainabilityMap,
            Map<Integer, Integer> elemIndexLeft,
            int currSum,
            int n)
    {
        attainabilityMap.put(currSum, true);
        elemIndexLeft.keySet().parallelStream().forEach(i -> {
            if (i > n)
                return;
            Integer counter = elemIndexLeft.get(i);
            if (counter > 0) {
                Map<Integer, Integer> elemIndexLeftNew = new HashMap<>(elemIndexLeft);
                elemIndexLeftNew.put(i, counter - 1);
                int nextSum = currSum + i;
                if (nextSum <= n) {
                    setInitialAttainability(attainabilityMap, elemIndexLeftNew, nextSum, n);
                }
            }
        });
    }
}
