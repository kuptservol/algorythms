package ru.skuptsov.puzzlers.job.interview.leetcode.bit.manipulation;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 01/12/2016
 * <p>
 * Given an array of integers, every element appears twice except for one.
 * Find that single one.
 */
public class SingleNumber {

    int[] numbers = {556, 3, 4, 4, 6, 6, 3};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {numbers, 556}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] numbers, int singleNumber) {
        assertEquals(findSingleNumber(numbers), singleNumber);
    }

    private int findSingleNumber(int[] numbers) {
        int singleNumber = 0;
        for (int nextNumber : numbers) {
            singleNumber ^= nextNumber;
        }

        return singleNumber;
    }
}
