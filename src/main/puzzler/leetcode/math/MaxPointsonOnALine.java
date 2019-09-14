package puzzler.leetcode.math;

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
                {intA(intA(0, -1), intA(0, 3), intA(0, -4), intA(0, -2), intA(0, -4), intA(0, 0), intA(0, 0), intA(0, 1), intA(0, -2),
                        intA(0, 4)), 10},
                {intA(intA(0, 0), intA(0, 0)), 2},
                {intA(intA(1, 1), intA(3, 2), intA(5, 3), intA(4, 1), intA(2, 3), intA(1, 4)), 4},
                {intA(intA(1, 1)), 1},
                {intA(intA(1, 1), intA(0, 0)), 2},
                {intA(intA(1, 1), intA(0, 0), intA(1, 2)), 2},
                {intA(intA(1, 1), intA(2, 2), intA(3, 3)), 3},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] points, int res) {
        assertEquals(res, maxPoints(points));
    }

    /**
     * First attempt - is to find (k+b) after solving line equasion kx+b for every pair - then count max in map
     *
     * O(N^2)
     *
     * NOT PASSED YET
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
                Set<Params> params = sameLineCounts.computeIfAbsent(solve(points[i], points[j]), (v) -> new HashSet<>());
                params.add(new Params(points[i][0], points[i][1]));
                params.add(new Params(points[j][0], points[j][1]));
            }
        }

        Map<Params, Integer> sameLineCountsNum = new HashMap<>();
        // deduplicate duplicates
        for (Map.Entry<Params, Set<Params>> paramsSetEntry : sameLineCounts.entrySet()) {
            int[] ints = paramsSetEntry.getValue().stream().map(duplicates::get).mapToInt(v -> v).toArray();
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
        double k = p2[0] - p1[0] == 0 ? Double.MAX_VALUE : (p2[1] - p1[1] + 0.0) / (p2[0] - p1[0]);
        double b = p1[1] - k * p1[0];

        return new Params(k, b);
    }

    private final class Params {
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
