package puzzler.leetcode.permutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableSet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 28/01/2017
 */
public class CombinationSum {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(1, 2, 3, 6, 7), 7, ImmutableSet.of(of(7), of(2, 2, 3))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> dict, Integer v, Set<List<Integer>> combinations) {
        Set<List<Integer>> combinations1 = findCombinations(dict, v);
        System.out.println(combinations1);
        assertEquals(combinations1, combinations);
    }

    /**
     * Leetcode adapter
     */
    private Set<List<Integer>> findCombinations(List<Integer> dict, Integer v) {
        int[] candidats = new int[dict.size()];
        for (int i = 0; i < dict.size(); i++) {
            candidats[i] = dict.get(i);
        }
        return combinationSum(candidats, v)
                .stream()
                .collect(Collectors.toSet());
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        fork(candidates, target, new ArrayList<>(), 0, result, 0);

        return result;
    }

    private void fork(int[] candidates, int target, List<Integer> current, int candidatesPointer, List<List<Integer>> result, int curSum) {
        if (curSum > target)
            return;
        else if (curSum == target)
            result.add(current);

        for (int i = candidatesPointer; i < candidates.length; i++) {
            List<Integer> forkedResult = new ArrayList<>(current);
            int candidate = candidates[i];
            forkedResult.add(candidate);
            fork(candidates, target, forkedResult, i, result, curSum + candidate);
        }
    }
}
