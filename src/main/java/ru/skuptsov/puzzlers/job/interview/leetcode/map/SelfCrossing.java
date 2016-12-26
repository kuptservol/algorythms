package ru.skuptsov.puzzlers.job.interview.leetcode.map;

import com.google.common.base.Objects;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * <p>
 * You are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to the north,
 * then x[1] metres to the west, x[2] metres to the south, x[3] metres to the east and so on.
 * In other words, after each move your direction changes counter-clockwise.
 * <p>
 * Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.
 */
public class SelfCrossing {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(2, 1, 1, 2).toArray(), true},
                {of(1, 2, 3, 4).toArray(), false},
                {of(1, 1, 1, 1).toArray(), true}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] x, boolean check) {
        assertEquals(isSelfCrossing(x), check);
    }

    /**
     * With memory 0(N)
     */
    private boolean isSelfCrossing(int[] x) {
        Set<Point> points = new HashSet<>();
        Point currPoint = Point.p(0, 0);

        points.add(currPoint);

        for (int i = 0; i < x.length; i++) {
            Integer nextLength = x[i];

            if (i % 4 == 0) {
                for (int j = 1; j <= nextLength; j++) {
                    Point newPoint = Point.p(currPoint.x, currPoint.y + 1);

                    if (!points.add(newPoint)) return true;
                    currPoint = newPoint;
                }
            } else if (i % 4 == 1) {
                for (int j = 1; j <= nextLength; j++) {
                    Point newPoint = Point.p(currPoint.x - 1, currPoint.y);
                    if (!points.add(newPoint)) return true;

                    currPoint = newPoint;
                }
            } else if (i % 4 == 2) {
                for (int j = 1; j <= nextLength; j++) {
                    Point newPoint = Point.p(currPoint.x, currPoint.y - 1);
                    if (!points.add(newPoint)) return true;

                    currPoint = newPoint;
                }
            } else {
                for (int j = 1; j <= nextLength; j++) {
                    Point newPoint = Point.p(currPoint.x + 1, currPoint.y);
                    if (!points.add(newPoint)) return true;

                    currPoint = newPoint;
                }
            }
        }

        return false;
    }

    private final static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        static Point p(int x, int y) {
            return new Point(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }
    }
}
