package puzzler.leetcode.permutation;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Numbers can be regarded as product of its factors. For example,
 * <p>
 * 8 = 2 x 2 x 2;
 * = 2 x 4.
 * Write a function that takes an integer n and return all possible combinations of its factors.
 * <p>
 * Note:
 * You may assume that n is always positive.
 * Factors should be greater than 1 and less than n.
 */
public class FactorCombinations {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {1, of()},
                {2, of()},
                {37, of()},
                {4, of(of(2, 2))},
                {14, of(of(2, 7))},
                {12, of(of(2, 6), of(2, 2, 3), of(3, 4))},
                {32, of(of(2, 16), of(2, 2, 8), of(2, 2, 2, 4), of(2, 2, 2, 2, 2), of(2, 4, 4), of(4, 8))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, List<List<Integer>> factors) {
        assertEquals(getFactors(n), factors);
    }

    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> allFactors = new ArrayList<>();

        fillProducts(n, new ArrayList<>(), allFactors);

        return allFactors;
    }

    private void fillProducts(
            int n,
            List<Integer> currFactors,
            List<List<Integer>> allFactors)
    {
        int from = currFactors.size() == 0 ? 2 : currFactors.get(currFactors.size() - 1);
        for (int i = from; i <= n / 2; i++) {
            if (n % i == 0) {
                List<Integer> nextCurrFactors = new ArrayList<>(currFactors);
                nextCurrFactors.add(i);
                int nextF = n / i;

                if (nextF < i) {
                    break;
                }
                List<Integer> nextFinalCurrFactors = new ArrayList<>(nextCurrFactors);
                nextFinalCurrFactors.add(nextF);

                allFactors.add(nextFinalCurrFactors);

                fillProducts(nextF, nextCurrFactors, allFactors);
            }
        }
    }
}
