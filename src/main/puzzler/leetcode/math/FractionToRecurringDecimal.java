package puzzler.leetcode.math;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given two integers representing the numerator and denominator of a fraction,
 *         return the fraction in string format.
 *         <p>
 *         If the fractional part is repeating, enclose the repeating part in parentheses.
 *         <p>
 *         For example,
 *         <p>
 *         Given numerator = 1, denominator = 2, return "0.5".
 *         Given numerator = 2, denominator = 1, return "2".
 *         Given numerator = 2, denominator = 3, return "0.(6)".
 */
public class FractionToRecurringDecimal {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
//                {1, 2, "0.5"},
//                {2, 1, "2"},
//                {3, 2, "1.5"},
//                {20000, 4, "5000"},
//                {4, 2000, "0.002"},
//                {2, 3, "0.(6)"},
//                {12, 999, "0.(012)"},
//                {56, 999, "0.(56)"},
//                {1, 7, "0.(56)"},
                {-1, -2147483648, "0.(56)"},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int a, int b, String c) {
        assertEquals(fractionToDecimal(a, b), c);
    }

    public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0) {
            return "0";
        }

        if (denominator == 0) {
            return "";
        }

        String result = "";
        boolean minus = (numerator < 0) ^ (denominator < 0);
        if (minus) {
            result += "-";
        }

        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);

        result += num / den;
        long reminder = (num % den) * 10;
        if (reminder == 0) {
            return result;
        }

        result += ".";

        Map<Long, Integer> reminders = new HashMap<>();
        while (reminder != 0) {
            if (reminders.containsKey(reminder)) {
                String part1 = result.substring(0, reminders.get(reminder));
                String part2 = result.substring(reminders.get(reminder), result.length());
                return part1 + "(" + part2 + ")";
            }

            reminders.put(reminder, result.length());
            result += reminder / den;
            reminder = (reminder % den) * 10;
        }

        return result;
    }
}
