package puzzler.interview.leetcode.sorting;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableSet.of;
import static org.testng.Assert.assertEquals;

public class GraphCycleDetector {

    public static boolean isContainsCycle(Map<Integer, Set<Integer>> graph) {
        Set<Integer> callStack = new HashSet<>();

        LinkedList<Integer> dfs = new LinkedList<>();

        dfs.add(graph.keySet().iterator().next());

        Integer node;
        while ((node = dfs.pollLast()) != null) {
            if (callStack.contains(node)) {
                return true;
            }
            callStack.add(node);

            if (graph.get(node) != null) {
                dfs.addAll(graph.get(node));
            }
        }

        return false;
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ImmutableMap.of(0, of(5), 3, of(5), 5, of(4), 4, of(3)), true},
                {ImmutableMap.of(0, of(5), 5, of(4), 4, of(3)), false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(Map<Integer, Set<Integer>> graph, boolean contains) {
        assertEquals(isContainsCycle(graph), contains);
    }
}
