package puzzler.interview.codility.toptal;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.lang.Math.pow;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 28/03/2017
 * converting integers into bytes representation, or more simply a number written in base -2.
 * The formula is B[i]*(−2)i for i = [0..N−1] where B is array of bytes and N is length of this array.
 */
public class Question_2 {

    int[] A1 = {1, 0, 0, 1, 1};
    int[] AResult = {1, 1, 0, 1};

    int[] A2 = {1, 0, 0, 1, 1, 1};
    int[] A2Result = {1, 1, 0, 1, 0, 1, 1};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {A1, AResult},
                {A2, A2Result}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[] A, int[] result) {
        assertEquals(solution(A), result);
    }

    public int[] solution(int[] A) {
        long val = fromMinus2xto10x(A);

        return from10xto2x(-val);
    }

    private int[] from10xto2x(long val) {
        List<Byte> binaryList = new ArrayList<>();

        while (val != 0) {
            long reminder = val % -2;
            val = val / -2;
            if (reminder < 0) {
                val += 1;
            }

            binaryList.add(Math.pow(reminder, 2) == 1 ? (byte) 1 : 0);
        }

        return binaryList.stream().mapToInt(Byte::intValue).toArray();
    }

    private long fromMinus2xto10x(int[] A) {
        long result = 0;
        for (int i = 0; i < A.length; i++) {
            result += pow(-2, i) * A[i];
        }

        return result;
    }
}
