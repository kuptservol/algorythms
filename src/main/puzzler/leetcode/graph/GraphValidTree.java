package puzzler.leetcode.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 *         Given n nodes labeled from 0 to n - 1 and a list of undirected edges (each edge is a pair of nodes),
 *         check if these edges form a valid tree.
 */
public class GraphValidTree {

    int[][] three = {
            {0, 1}, {0, 2}, {2, 3}, {2, 4}
    };

    int[][] four = {
            {0, 1}, {0, 2}, {1, 2}, {2, 3}, {2, 4}
    };

    int[][] five = {
            {0, 1}, {2, 3}
    };

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {5, three, true},
                {5, four, false},
                {4, five, false},
        };
    }

    @Test(dataProvider = "testData")
    public void test(int n, int[][] edges, boolean answer) {
        assertEquals(validTree(n, edges), answer);
    }

    public boolean validTree(int n, int[][] edges) {
        if (n == 0) {
            return false;
        }

        Map<Integer, Set<Integer>> adjacents = new HashMap<>();

        for (int i = 0; i < edges.length; i++) {
            adjacents.computeIfAbsent(edges[i][0], (a) -> new HashSet<>()).add(edges[i][1]);
            adjacents.computeIfAbsent(edges[i][1], (a) -> new HashSet<>()).add(edges[i][0]);
        }

        Set<Integer> visited = new HashSet<>();
        LinkedList<Integer> currStack = new LinkedList<>();

        if (!dfs(0, adjacents, currStack, visited)) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean dfs(int currNode, Map<Integer, Set<Integer>> adjacents, LinkedList<Integer> currStack, Set<Integer> visited) {
        visited.add(currNode);

        Optional<Integer> fromNode = currStack.size() > 0 ? Optional.of(currStack.getLast()) : Optional.empty();
        currStack.addLast(currNode);

        for (Integer nextNode : adjacents.getOrDefault(currNode, new HashSet<>())) {
            if (fromNode.isPresent() && fromNode.get().equals(nextNode)) {
                continue;
            }
            if (!visited.contains(nextNode)) {
                if (!dfs(nextNode, adjacents, currStack, visited)) {
                    return false;
                }
            } else {
                if (currStack.contains(nextNode)) {
                    return false;
                }
            }
        }

        currStack.removeLast();

        return true;
    }
}
