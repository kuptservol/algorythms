package puzzler.leetcode.math;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.gradle.internal.impldep.org.junit.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 */
public class MaxPointsonOnALine {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(intA(0, 0), intA(94911151, 94911150), intA(94911152, 94911151)), 2},
                {intA(intA(0, -1), intA(0, 3), intA(0, -4), intA(0, -2), intA(0, -4), intA(0, 0), intA(0, 0), intA(0, 1), intA(0, -2),
                        intA(0, 4)), 10},
                {intA(intA(0, 0), intA(0, 0)), 2},
                {intA(intA(0, 0), intA(0, 0), intA(1, 1)), 3},
                {intA(intA(1, 1), intA(3, 2), intA(5, 3), intA(4, 1), intA(2, 3), intA(1, 4)), 4},
                {intA(intA(1, 1)), 1},
                {intA(intA(1, 1), intA(0, 0)), 2},
                {intA(intA(1, 1), intA(0, 0), intA(1, 2)), 2},
                {intA(intA(40, -23), intA(9, 138), intA(429, 115), intA(50, -17), intA(-3, 80), intA(-10, 33), intA(5, -21), intA(-3, 80)
                        , intA(-6, -65),
                        intA(-18, 26), intA(-6, -65), intA(5, 72), intA(0, 77), intA(-9, 86), intA(10, -2), intA(-8, 85), intA(21, 130),
                        intA(18, -6),
                        intA(-18, 26), intA(-1, -15), intA(10, -2), intA(8, 69), intA(-4, 63), intA(0, 3), intA(-4, 40), intA(-7, 84),
                        intA(-8, 7), intA(30, 154), intA(16, -5), intA(6, 90), intA(18, -6), intA(5, 77), intA(-4, 77), intA(7, -13),
                        intA(-1, -45), intA(16, -5), intA(-9, 86), intA(-16, 11), intA(-7, 84), intA(1, 76), intA(3, 77), intA(10, 67),
                        intA(1, -37), intA(-10, -81), intA(4, -11), intA(-20, 13), intA(-10, 77), intA(6, -17), intA(-27, 2), intA(-10,
                                -81), intA(10, -1), intA(-9, 1), intA(-8, 43), intA(2, 2), intA(2, -21), intA(3, 82), intA(8, -1),
                        intA(10, -1), intA(-9, 1), intA(-12, 42), intA(16, -5), intA(-5, -61), intA(20, -7), intA(9, -35), intA(10, 6),
                        intA(12, 106), intA(5, -21), intA(-5, 82), intA(6, 71), intA(-15, 34), intA(-10, 87), intA(-14, -12), intA(12,
                                106), intA(-5, 82), intA(-46, -45), intA(-4, 63), intA(16, -5), intA(4, 1), intA(-3, -53), intA(0, -17),
                        intA(9, 98), intA(-18, 26), intA(-9, 86), intA(2, 77), intA(-2, -49), intA(1, 76), intA(-3, -38), intA(-8, 7),
                        intA(-17, -37), intA(5, 72), intA(10, -37), intA(-4, -57), intA(-3, -53), intA(3, 74), intA(-3, -11), intA(-8, 7)
                        , intA(1, 88), intA(-12, 42), intA(1, -37), intA(2, 77), intA(-6, 77), intA(5, 72), intA(-4, -57), intA(-18, -33)
                        , intA(-12, 42), intA(-9, 86), intA(2, 77), intA(-8, 77), intA(-3, 77), intA(9, -42), intA(16, 41), intA(-29,
                                -37), intA(0, -41), intA(-21, 18), intA(-27, -34), intA(0, 77), intA(3, 74), intA(-7, -69), intA(-21, 18)
                        , intA(27, 146), intA(-20, 13), intA(21, 130), intA(-6, -65), intA(14, -4), intA(0, 3), intA(9, -5), intA(6, -29)
                        , intA(-2, 73), intA(-1, -15), intA(1, 76), intA(-4, 77), intA(6, -29)), 25},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] points, int res) {
        assertEquals(res, maxPoints(points));
    }

    /**
     * First attempt - is to find (k;b) after solving line equation kx+b for every pair - then count max in map
     *
     * O(N^2)
     *
     * PASSED
     */
    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        if (points.length == 1) {
            return 1;
        }

        // count duplicates
        Map<Params, Integer> duplicates = new HashMap<>();
        for (int i = 0; i < points.length; i++) {
            duplicates.compute(new Params(points[i][0], points[i][1]), (params, integer) -> {
                if (integer == null) {
                    return 1;
                } else {
                    return integer + 1;
                }
            });
        }

        Map<Params, Set<Params>> sameLineCounts = new HashMap<>();
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j])
                    continue;
                Set<Params> params = sameLineCounts.computeIfAbsent(solve(points[i], points[j]), (v) -> new HashSet<>());
                params.add(new Params(points[i][0], points[i][1]));
                params.add(new Params(points[j][0], points[j][1]));
            }
        }

        Map<Params, Integer> sameLineCountsNum = new HashMap<>();
        // deduplicate duplicates
        for (Map.Entry<Params, Set<Params>> paramsSetEntry : sameLineCounts.entrySet()) {
            sameLineCountsNum.put(paramsSetEntry.getKey(),
                    paramsSetEntry.getValue().stream().map(duplicates::get).mapToInt(v -> v).sum());
        }

        int max = 0;
        for (int val : sameLineCountsNum.values()) {
            max = Math.max(val, max);
        }

        return max;
    }

    private Params solve(int[] p1, int[] p2) {
        if (p1[1] == p2[1]) {
            // y is the same - horizontal line
            return new Params(0, p1[1]);
        } else if (p1[0] == p2[0]) {
            // x is the same - vertical line
            return new Params(p1[0], Double.MAX_VALUE);
        } else {
            double k = (p2[1] - p1[1] + 0.0) / (p2[0] - p1[0]);
            double b = p1[1] - k * p1[0];
            return new Params(k, b);
        }

    }

    private static final class Params {
        double k;
        double b;

        public Params(double k, double b) {
            this.k = k;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Params)) return false;
            Params params = (Params) o;
            return k == params.k &&
                    b == params.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(k, b);
        }
    }
}
