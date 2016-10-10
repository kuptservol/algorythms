package ru.skuptsov.puzzlers.job.interview;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 10/10/2016
 * <p>
 * Find subsequence which sum is maximum in sequence like
 * [1, 2, 0, -1, 10, 20, 20, -5, 1, 3]
 */
public class MaxSubSequence {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(1, 2, 0, -1, 10, 20, 20, -5, 1, 3), 50}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> sequence, int sum) {
        assertEquals(getMaxSubsequenceSum(sequence), sum);
    }

    private int getMaxSubsequenceSum(List<Integer> sequence) {
        int maxSum = 0;
        int currSum = 0;
        for (int i = 0; i < sequence.size(); i++) {
            Integer next = sequence.get(i);
            if (next < 0) {
                maxSum = Math.max(currSum, maxSum);
                currSum = 0;
            } else {
                currSum += next;
            }
        }

        return maxSum;
    }
}
