package ru.skuptsov.puzzlers.job.interview;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/02/2017
 */
public class FillWithWaterBetweenTowers {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {toArray(of(1, 4, 2, 3, 2, 1)), 1},
                {toArray(of(1, 4, 1, 2, 1, 3, 1, 2)), 6},
                {toArray(of(1, 0, 1)), 1},
                {toArray(of(1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 2)), 1},
                {toArray(of(4, 3, 2, 2, 1, 1, 0, 1)), 1},
                {toArray(of(3, 0, 0, 2, 0, 4)), 10}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] shape, int liters) {
//        assertEquals(fillWithWater(shape), liters);
        assertEquals(fillWithWaterWithIndex(shape), liters);
    }

    /*
    O(N^2)
    On each step we can add as mach water as min height to the left and to the right
    On each step calculate left max, right max
     */
    public int fillWithWater(int[] shape) {
        int water = 0;

        for (int i = 0; i < shape.length; i++) {
            water += Math.max(Math.min(findRightMax(i, shape), findLeftMax(i, shape)) - shape[i], 0);
        }

        return water;
    }

    private int findLeftMax(int i, int[] shape) {
        int leftMax = -1;
        for (int j = i; j >= 0; j--) {
            leftMax = Math.max(leftMax, shape[j]);
        }
        return leftMax;
    }

    private int findRightMax(int i, int[] shape) {
        int rightMax = -1;
        for (int j = i + 1; j <= shape.length - 1; j++) {
            rightMax = Math.max(rightMax, shape[j]);
        }
        return rightMax;
    }


    /*
       O(N)
       Memory O(N)
       Same idea as above but precalculate left, right max heights
    */
    public int fillWithWaterWithIndex(int[] shape) {
        int water = 0;

        int[] leftHighest = new int[shape.length];
        int[] rightHighest = new int[shape.length];

        leftHighest[0] = shape[0];
        for (int i = 1; i < shape.length; i++) {
            leftHighest[i] = Math.max(leftHighest[i - 1], shape[i]);
        }

        rightHighest[shape.length - 1] = shape[shape.length - 1];
        for (int i = shape.length - 2; i >= 0; i--) {
            rightHighest[i] = Math.max(rightHighest[i + 1], shape[i]);
        }

        for (int i = 0; i < shape.length; i++) {
            water += Math.max(Math.min(leftHighest[i], rightHighest[i]) - shape[i], 0);
        }

        return water;
    }
}
