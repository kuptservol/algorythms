package puzzler.leetcode.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 */
public class MinimumHeightTrees {

    int[][] one = {{1, 0}, {1, 2}, {1, 3}};
    int[][] two = {{0, 3}, {1, 3}, {2, 3}, {4, 3}, {5, 4}};
    int[][] three = {{0, 1}, {0, 2}, {0, 3}, {3, 4}, {4, 5}};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(1), 4, one},
                {of(3, 4), 6, two},
                {of(3), 6, three}
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<Integer> answer, int n, int[][] edges) {
        assertEquals(answer, findMinHeightTrees(n, edges));
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> minHeightTreeRoots = new ArrayList<>();
        if (edges.length == 0) {
            minHeightTreeRoots.add(0);
            return minHeightTreeRoots;
        }

        Map<Integer, Set<Integer>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < edges.length; i++) {
            graph.get(edges[i][0]).add(edges[i][1]);
            graph.get(edges[i][1]).add(edges[i][0]);
        }

        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 1) {
                leaves.add(i);
            }
        }

        while (n > 2) {
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (Integer leave : leaves) {
                Integer neigbour = graph.get(leave).iterator().next();
                graph.get(neigbour).remove(leave);
                if (graph.get(neigbour).size() == 1) {
                    newLeaves.add(neigbour);
                }
            }

            leaves = newLeaves;
        }

        return leaves;
    }
}
