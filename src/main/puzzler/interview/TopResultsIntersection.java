package puzzler.interview;

import java.util.HashMap;
import java.util.Map;

import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class TopResultsIntersection {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(0, 1, 2, 3, 4), intA(4, 2, 0, 3, 1), intA(0, 0, 2, 3, 5)},
                {intA(0, 2), intA(2, 3), intA(0, 1)},
                {intA(0, 1, 1), intA(1, 2, 3), intA(0, 1, 1)},
                {intA(0, 1), intA(0, 1), intA(1, 2)},
                {intA(0, 1), intA(0, 2), intA(1, 1)}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, int[] b, int[] res) {
        AssertJUnit.assertArrayEquals(intersect(a, b), res);
    }

    public int[] intersect(int[] a, int[] b) {
        if (a == null || b == null) {
            return null;
        }

        int[] res = new int[a.length];

        Map<Integer, Integer> countsA = new HashMap<>();
        Map<Integer, Integer> countsB = new HashMap<>();

        // 1 2 3
        // 0 1 1

        // A: 1:1 2:1
        // B: 0:1 1:1

        // res: [0, 1, 1]
        for (int i = 0; i < res.length; i++) {
            int curr = i == 0 ? 0 : res[i - 1];

            if (a[i] == b[i]) {
                curr += 1;
            } else {
                if (countsA.containsKey(b[i])) {
                    if (countsA.get(b[i]) > countsB.getOrDefault(b[i], 0)) {
                        curr++;
                    }
                }

                if (countsB.containsKey(a[i])) {
                    if (countsB.get(a[i]) > countsA.getOrDefault(a[i], 0)) {
                        curr++;
                    }
                }
            }

            countsA.compute(a[i], (val, cnt) -> {
                if (cnt == null) return 1;
                else return cnt + 1;
            });

            countsB.compute(b[i], (val, cnt) -> {
                if (cnt == null) return 1;
                else return cnt + 1;
            });

            res[i] = curr;
        }

        return res;
    }
}
