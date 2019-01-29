package puzzler.leetcode.math;

import java.util.LinkedList;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 */
public class PlusOne {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(1, 2, 3), intA(1, 2, 4)},
                {intA(1, 2, 9), intA(1, 3, 0)},
                {intA(0), intA(1)},
                {intA(9, 9, 9, 9), intA(1, 0, 0, 0, 0)},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] in, int[] res) {
        assertEquals(plusOne(in), res);
    }

    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return new int[1];
        }

        LinkedList<Integer> digitsL = new LinkedList<>();

        boolean transfer = false;
        for (int i = digits.length - 1; i >= 0; i--) {
            int next = digits[i];

            if (i == digits.length - 1) {
                if (next == 9) {
                    next = 0;
                    transfer = true;
                } else {
                    next++;
                }
            } else {
                if (transfer) {
                    if (next == 9) {
                        next = 0;
                        transfer = true;
                    } else {
                        next++;
                        transfer = false;
                    }
                }
            }

            digitsL.addFirst(next);
        }

        if (transfer) {
            digitsL.addFirst(1);
        }

        int[] result = new int[digitsL.size()];

        for (int i = 0; i < digitsL.size(); i++) {
            result[i] = digitsL.get(i);
        }

        return result;
    }
}
