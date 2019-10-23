package puzzler.interview;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 */
public class FindTwoEqualSubTrees {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {}
        };
    }

    @Test(dataProvider = "testData")
    public void test(Object a, Object b) {
        assertEquals(testMethod(a), b);
    }

    public Object testMethod(Object a) {
        return null;
    }
}
