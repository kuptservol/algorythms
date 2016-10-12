package ru.skuptsov.puzzlers.job.interview.programcreek.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 13/10/2016
 * <p>
 * Given an array of integers, find two numbers such that they add up to a specific target number.
 * <p>
 * The function twoSum should return indices of the two numbers such that they add up to the target,
 * where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are not zero-based.
 * <p>
 * For example:
 * <p>
 * Input: numbers={2, 7, 11, 15}, target=9
 * Output: index1=0, index2=1
 */
public class TwoSum {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(15, 2, 11, 7), 9, of(1, 3)},
                {of(15, 2, 11, 7, 3, 46, 22, 6, 8, 9, 0, 12, 34, 56), 7, of(3, 10)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> seq, Integer target, List<Integer> answer) {
        assertEquals(getPositionToTargetIndexingMask(seq, target), answer);
    }

    /**
     * O(N)
     * Memory O(target)
     */
    private List<Integer> getPositionToTargetIndexingMask(List<Integer> seq, Integer target) {
        int[] indexedSeq = new int[target + 1];
        int index0 = 0, index1 = 0;
        for (int i = 0; i < seq.size(); i++) {
            Integer next = seq.get(i);

            if (next <= target) {
                if (indexedSeq[next] != 0) {
                    index0 = indexedSeq[next];
                    index1 = i;
                    break;
                } else {
                    indexedSeq[target - next] = i;
                }
            }
        }

        return index0 > index1 ? of(index1, index0) : of(index0, index1);
    }
}
