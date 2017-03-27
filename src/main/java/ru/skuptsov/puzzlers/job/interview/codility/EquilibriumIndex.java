package ru.skuptsov.puzzlers.job.interview.codility;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 */
public class EquilibriumIndex {

    int[] A1 = {-1, 3, -4, 5, 1, -6, 2, 1};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {A1, 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int b) {
        int solution = solution(a);

        System.out.println("Solution" + solution);
        assertEquals(solution, b);
    }

    public int solution(int[] A) {

        Map<Integer, Long> directIndex = new HashMap<>();
        Map<Integer, Long> reverseIndex = new HashMap<>();

        long directSum = 0;
        long reverseSum = 0;

        for (int i = 0; i < A.length; i++) {
            int nextDirVal = A[i];
            int reverseIndexPos = A.length - 1 - i;
            int nextReverseVal = A[reverseIndexPos];

            directSum += nextDirVal;
            reverseSum += nextReverseVal;

            directIndex.put(i, directSum);
            reverseIndex.put(reverseIndexPos, reverseSum);
        }

        for (int i = 0; i < A.length; i++) {

            Long directVal = i == 0 ? 0 : directIndex.get(i - 1);
            Long reverseVal = i == A.length - 1 ? 0 : reverseIndex.get(i + 1);

            if (directVal.equals(reverseVal)) {
                return i;
            }
        }

        return -1;
    }
}
