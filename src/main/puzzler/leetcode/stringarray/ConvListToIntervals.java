package puzzler.leetcode.stringarray;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.gradle.internal.impldep.org.junit.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class ConvListToIntervals {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(4, 1, 2), "1-2,4"},
                {intA(1, 4, 5, 2, 3, 9, 8, 11, 0), "0-5,8-9,11"},
                {intA(1, 4, 3, 2), "1-4"},
                {intA(1, 4), "1,4"},
                {intA(1), "1"},
                {intA(1, 1, 1), "1"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] a, String res) {
        assertEquals(conv(a), res);
    }

    public String conv(int[] a) {
        if (a == null || a.length == 0) {
            return "";
        }

        if (a.length == 1) {
            return a[0] + "";
        }
        Arrays.sort(a);
        StringBuilder result = new StringBuilder();
        result.append(a[0]);

        Integer lastInInterval = null;
        boolean currIntervalOpen = false;
        for (int i = 1; i < a.length; i++) {
            if (a[i] == a[i - 1] + 1) {
                if (!currIntervalOpen) {
                    result.append("-");
                    currIntervalOpen = true;
                }
                lastInInterval = a[i];
            } else if (a[i] != a[i - 1]) {
                if (lastInInterval != null) {
                    result.append(lastInInterval);
                }
                result.append(",").append(a[i]);
                currIntervalOpen = false;
            }
        }

        if (currIntervalOpen) {
            result.append(lastInInterval);
        }

        return result.toString();
    }
}
