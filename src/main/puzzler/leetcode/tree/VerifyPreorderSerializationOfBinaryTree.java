package puzzler.leetcode.tree;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * One way to serialize a binary tree is to use pre-order traversal.
 * When we encounter a non-null node, we record the node's value.
 * If it is a null node, we record using a sentinel value such as #.
 */
public class VerifyPreorderSerializationOfBinaryTree {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {"1,#,2,#,#", true},
                {"9,3,4,#,#,1,#,#,2,#,6,#,#", true},
                {"1,#", false},
                {"9,#,#,1", false},
                {"1,2,#,#,#", true},
                {"#,#,3,5,#", false},
                {"#,7,6,9,#,#,#", false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(String preorder, boolean result) {
        assertEquals(isValidSerialization(preorder), result);
    }

    /**
     * Counting slots approach.
     * Every node is a number of additional required slots to fill - if # - then 0 -f number - 2 additional slots
     * Mem: O(N) - for keeping split array
     * Speed: O(N)
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder == null || preorder.length() == 0) {
            return false;
        }

        String[] preorders = preorder.split(",");
        //slot from start node
        int slots = 1;

        for (String next : preorders) {
            if (!check(next)) {
                return false;
            }

            // consume 1 slot
            --slots;

            if (slots < 0) return false;

            if (!next.equals("#")) {
                // minus current slot - plus 2 slots
                slots += 2;
            }
        }

        return slots == 0;
    }

    private boolean check(String order) {
        if (order.equals("#")) {
            return true;
        }
        try {
            Integer.valueOf(order);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }
}
