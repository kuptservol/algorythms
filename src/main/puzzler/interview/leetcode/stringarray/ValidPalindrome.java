package puzzler.interview.leetcode.stringarray;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Character.isLetter;
import static java.lang.Character.toLowerCase;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 25/10/2016
 * <p>
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * <p>
 * For example, "Red rum, sir, is murder" is a palindrome, while "Programcreek is awesome" is not.
 * <p>
 * Note:
 * Have you consider that the string might be empty? This is a good question to ask during an interview.
 * <p>
 * For the purpose of this problem, we define empty string as valid palindrome.
 */
public class ValidPalindrome {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"Red rum, sir, is murder", true},
                {"", true},
                {"Programcreek is awesome", false},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String a, boolean res) {
        assertEquals(isPalindrome(a), res);
    }

    private boolean isPalindrome(String a) {
        if (a == null) {
            return false;
        }

        if (a.length() == 0) {
            return true;
        }

        char[] chars = a.toCharArray();
        int i = 0, j = a.length() - 1;

        while (i < j) {
            int nextI = chars[i];
            int nextJ = chars[j];
            if (!isLetter(nextI)) {
                i++;
                continue;
            }
            if (!isLetter(nextJ)) {
                j--;
                continue;
            }

            if (toLowerCase(nextI) != toLowerCase(nextJ)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }
}
