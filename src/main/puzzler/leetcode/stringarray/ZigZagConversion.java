package puzzler.leetcode.stringarray;

import java.util.Arrays;
import java.util.Objects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * <p>
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this:
 * (you may want to display this pattern in a fixed font for better legibility)
 * <p>
 * P   A   H   N
 * A P L S I I G
 * Y   I   R
 * And then read line by line: "PAHNAPLSIIGYIR"
 * Write the code that will take a string and make this conversion given a number of rows:
 * <p>
 * string convert(string text, int nRows);
 * convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR".
 */
public class ZigZagConversion {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
//                {"PAYPALISHIRING", 3, "PAHNAPLSIIGYIR"},
//                {"PAYPALISHIRING", 1, "PAYPALISHIRING"},
//                {"", 1, ""},
                {"ABC", 2, "ACB"}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s, int numRows, String answer) {
        assertEquals(convert(s, numRows), answer);
    }

    public String convert(String s, int numRows) {
        if (s == null || s.length() == 0) {
            return "";
        }

        String[] lines = new String[numRows];

        char[] chars = s.toCharArray();
        int midLineLevel = numRows / 2;
        int pos = 0;
        boolean swap = false;
        for (int i = 0; i < chars.length; i++) {
            if (pos == numRows) {
                pos = midLineLevel;
                swap = true;
            }

            if (lines[pos] == null)
                lines[pos] = "";
            lines[pos] += chars[i];

            if (swap) {
                pos = 0;
                swap = false;
            } else {
                pos++;
            }
        }

        return Arrays.stream(lines).filter(Objects::nonNull).reduce((a, v) -> a + v).get();
    }
}
