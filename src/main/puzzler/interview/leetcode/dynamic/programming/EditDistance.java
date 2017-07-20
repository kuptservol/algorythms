package puzzler.interview.leetcode.dynamic.programming;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 29/11/2016
 * <p>
 * From Wiki:
 * <p>
 * In computer science,
 * edit distance is a way of quantifying how dissimilar two strings (e.g., words)
 * are to one another by counting the minimum number of operations required to transform one string into the other.
 * <p>
 * There are three operations permitted on a word: replace, delete, insert.
 * For example, the edit distance between "a" and "b" is 1,
 * the edit distance between "abc" and "def" is 3.
 * <p>
 * Solution1 : Расстояние Левинштейна
 */
public class EditDistance {

    private static int min(int a, int b, int c) {
        return a < b ? a < c ? a : c : b < c ? b : c;
    }

    private static int m(int a, int b) {
        return a == b ? 0 : 1;
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"abbbb", "bbd", 3},
                {"abc", "def", 3},
                {"a", "d", 1}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String s1, String s2, int simularity) {
        assertEquals(levinsteinDistance(s1, s2), simularity);
    }

    private int levinsteinDistance(String s1, String s2) {
        return distance(s1.length(), s2.length(), s1, s2);
    }

    private int distance(int i, int j, String s1, String s2) {
        if (i == 0 && j == 0) {
            return 0;
        } else if (j == 0 && i > 0) {
            return i;
        } else if (i == 0 && j > 0) {
            return j;
        } else {
            return min(
                    distance(i, j - 1, s1, s2) + 1,
                    distance(i - 1, j, s1, s2) + 1,
                    distance(i - 1, j - 1, s1, s2) + m(s1.toCharArray()[i - 1], s2.toCharArray()[j - 1])
            );
        }
    }
}
