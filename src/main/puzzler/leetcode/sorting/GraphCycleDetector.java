package puzzler.leetcode.sorting;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.ImmutableMap;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableSet.of;
import static org.testng.Assert.assertEquals;

public class GraphCycleDetector {

    public static boolean isContainsCycle(Map<Integer, Set<Integer>> graph) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> currentStack = new Stack<>();

        for (Integer node : graph.keySet()) {
            if (!visited.contains(node)) {
                if (dfsCycleCheck(node, graph, visited, currentStack)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean dfsCycleCheck(Integer currNode, Map<Integer, Set<Integer>> graph, Set<Integer> visited, Stack<Integer> currentStack) {
        currentStack.push(currNode);
        visited.add(currNode);

        Optional<Integer> fromNode = currentStack.size() > 0 ? Optional.of(currentStack.peek()) : Optional.empty();
        for (Integer nextNode : graph.getOrDefault(currNode, new HashSet<>())) {
            if (fromNode.isPresent() && fromNode.get().equals(nextNode)) {
                continue;
            }
            if (visited.contains(nextNode)) {
                if (currentStack.contains(currNode)) {
                    return true;
                }
            } else {
                return dfsCycleCheck(nextNode, graph, visited, currentStack);
            }
        }

        currentStack.pop();

        return false;
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ImmutableMap.of(0, of(5), 3, of(5), 5, of(4), 4, of(3)), true},
                {ImmutableMap.of(0, of(0), 3, of(5), 5, of(4), 4, of(3)), true},
                {ImmutableMap.of(0, of(5), 5, of(4), 4, of(3)), false},
                {ImmutableMap.of(0, of(0), 5, of(4), 4, of(3)), false}
        };
    }

    @Test(dataProvider = "testData")
    public void test(Map<Integer, Set<Integer>> graph, boolean contains) {
        assertEquals(isContainsCycle(graph), contains);
    }
}
