package ru.skuptsov.puzzlers.job.interview.codility.toptal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 28/03/2017
 * Your function takes an array of integers (arr),
 * and an integer (x). You need to find the position in arr that splits the array in two,
 * where one side has as many occurrences of x as the other side has occurrences of any number but x
 * <p>
 * So, given an array like this: [5, 5, 2, 3, 5, 1, 6] and x being "5",
 * the function should return "4" (Position 4, holding the number "3" above is the point where you have 2 5's
 * on the one side, and two "not fives" on the other.
 */
public class Question_1 {

    int[] A = {5, 5, 2, 3, 5, 1, 6};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {A, 5, 4}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int val, int index) {
        assertEquals(slice(a, val), index);
    }

    public int slice(int[] a, int x) {
        int[] xCountIndex = new int[a.length];
        int[] notXIndex = new int[a.length];

        int xCount = 0;
        int notXCount = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == x) {
                xCount += 1;
            }

            xCountIndex[i] = xCount;

            if (a[a.length - 1 - i] != x) {
                notXCount += 1;
            }

            notXIndex[a.length - 1 - i] = notXCount;
        }

        for (int i = 0; i < a.length; i++) {

            int xCountVal = i == 0 ? 0 : xCountIndex[i - 1];
            int notXCountVal = i == a.length - 1 ? 0 : notXIndex[i + 1];

            if (xCountVal == notXCountVal) {
                return i + 1;
            }
        }

        return -1;
    }
}
