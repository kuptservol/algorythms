package puzzler.leetcode.permutation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableSet.of;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * <p>
 * Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.
 * <p>
 * For example, if n = 4 and k = 2, a solution is:
 * <p>
 * [
 * [2,4],
 * [3,4],
 * [2,3],
 * [1,2],
 * [1,3],
 * [1,4],
 * ]
 */
public class IntRangeCombinations {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {4, 2, of(of(2, 4), of(3, 4), of(2, 3), of(1, 2), of(1, 3), of(1, 4))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, int k, Set<Set<Integer>> expected) {
        List<List<Integer>> combined = combine(n, k);

        Set<Set<Integer>> combinedSets = combined.stream()
                .map(HashSet::new)
                .collect(Collectors.toSet());

        assertTrue(combinedSets.size() == expected.size());
        for (Set<Integer> c : expected) {
            assertTrue(combinedSets.contains(c));
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();

        combine_(0, n, k, new ArrayList<>(), result);

        return result;
    }

    public void combine_(int n, int N, int k, List<Integer> currList, List<List<Integer>> result) {
        if (k == 0) {
            result.add(currList);
        }

        for (int i = n + 1; i <= N; i++) {
            List<Integer> digitListNext = new ArrayList<>(currList);
            digitListNext.add(i);

            combine_(i, N, k - 1, digitListNext, result);
        }
    }
}
