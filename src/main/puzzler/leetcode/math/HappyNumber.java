package puzzler.leetcode.math;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Write an algorithm to determine if a number is "happy".
 *         <p>
 *         A happy number is a number defined by the following process:
 *         Starting with any positive integer, replace the number by the sum of the squares
 *         of its digits, and repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
 *         Those numbers for which this process ends in 1 are happy numbers.
 */
public class HappyNumber {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {19, true}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, boolean happy) {
        assertEquals(isHappy(n), happy);
    }

    public boolean isHappy(int n) {
        Set<Integer> visited = new HashSet<>();

        while (!visited.contains(n)) {
            visited.add(n);

            n = getSquares(n);

            if (n == 1) {
                return true;
            }
        }

        return false;
    }

    private int getSquares(int n) {
        int squares = 0;
        while (n > 0) {
            squares += (n % 10) * (n % 10);
            n = n / 10;
        }

        return squares;
    }
}
