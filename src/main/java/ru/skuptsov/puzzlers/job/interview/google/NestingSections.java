package ru.skuptsov.puzzlers.job.interview.google;

import com.google.common.primitives.Ints;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * <p>
 * Вам задано n отрезков на прямой таких, что никакие два конца никаких отрезков не совпадают.
 * Определите для каждого отрезка, сколько отрезков лежит внутри него.
 * <p>
 * Входные данные
 * В первой строке записано единственное целое число n (1 ≤ n ≤ 2·105) — количество отрезков на прямой.
 * <p>
 * В каждой из следующих n строк записаны два целых числа li и ri ( - 109 ≤ li < ri ≤ 109) —
 * координаты левой и правой границ i-го отрезка. Гарантируется, что никакие концы никаких отрезков не совпадают.
 * <p>
 * Выходные данные
 * Выведите n строк. j-ая строка должна содержать единственное целое число aj — количество отрезков, находящихся внутри j-ого отрезка.
 */
public class NestingSections {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
//                {4, toArray(of(1, 8, 2, 3, 4, 7, 5, 6)), toArray(of(3, 0, 1, 0))},
                {3, toArray(of(3, 4, 1, 5, 2, 6)), toArray(of(0, 1, 1))}
        };
    }

    // 3, 4
    // 1, 5
    // 2, 6

    @Test(dataProvider = "testData")
    public void test(int n, int[] segments, int[] result) {
        assertTrue(Arrays.equals(getIntersections(n, segments), result));
    }

    public int[] getIntersections(int n, int[] segments) {
        int[] res = new int[n];

        return res;
    }
}
