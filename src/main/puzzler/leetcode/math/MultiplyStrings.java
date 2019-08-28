package puzzler.leetcode.math;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Given two non-negative integers num1 and num2 represented as strings,
 * return the product of num1 and num2, also represented as a string.
 *
 * 1. The length of both num1 and num2 is < 110.
 * 2. Both num1 and num2 contain only digits 0-9.
 * 3. Both num1 and num2 do not contain any leading zero, except the number 0 itself.
 * 4. You must not use any built-in BigInteger library or convert the inputs to integer directly.
 *
 * @author Sergey Kuptsov
 */
public class MultiplyStrings {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"2", "3", "6"},
                {"99", "9", "891"},
                {"123", "456", "56088"},
                {"99", "99", "9801"},
                {"99230948", "324432", "32193694921536"},
                {"93333", "0", "0"},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String num1, String num2, String res) {
        assertEquals(multiply(num1, num2), res);
    }

    public String multiply(String num1, String num2) {
        String res = "";

        if (num1 == null || num1.isEmpty() || num2 == null || num2.isEmpty()) {
            return res;
        }

        if (num1.equals("1")) {
            return num2;
        }
        if (num1.equals("0")) {
            return "0";
        }

        if (num2.equals("1")) {
            return num1;
        }
        if (num2.equals("0")) {
            return "0";
        }

        StringBuilder num1Zeros = new StringBuilder();
        for (int i = num1.length() - 1; i >= 0; i--) {
            int num1CurrVal = num1.charAt(i) - '0';

            String resInner = "";
            StringBuilder num2Zeros = new StringBuilder();
            for (int j = num2.length() - 1; j >= 0; j--) {
                int num2CurrVal = num2.charAt(j) - '0';

                resInner = plus(resInner, num2CurrVal * num1CurrVal + num2Zeros.toString());

                num2Zeros.append("0");
            }

            res = plus(res, resInner + num1Zeros);
            num1Zeros.append("0");
        }

        return res;
    }

    private String plus(String num1, String num2) {
        StringBuilder res = new StringBuilder();

        if (num1.equals("")) {
            return num2;
        }

        if (num2.equals("")) {
            return num1;
        }

        boolean transferOne = false;

        int maxL = Math.max(num1.length(), num2.length());
        for (int i = 1; i <= maxL; i++) {
            int num1CurrVal = i > num1.length() ? 0 : num1.charAt(num1.length() - i) - '0';
            int num2CurrVal = i > num2.length() ? 0 : num2.charAt(num2.length() - i) - '0';

            int curr = num1CurrVal + num2CurrVal + (transferOne ? 1 : 0);
            if (curr < 10) {
                transferOne = false;
                res.append(curr);
            } else {
                transferOne = true;
                res.append(String.valueOf(curr).charAt(1));
            }
        }

        if (transferOne) {
            res.append(1);
        }

        return res.reverse().toString();
    }
}
