package puzzler.leetcode.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static puzzler.leetcode.PuzzlerUtils.intA;

/**
 * @author Sergey Kuptsov
 * @since 23/11/2016
 * <p>
 * Issue :
 * There are a total of n courses you have to take, labeled from 0 to n - 1.
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1,
 * which is expressed as a pair: [0,1]. Given the total number of courses and a list of prerequisite pairs,
 * is it possible for you to finish all courses?
 * <p>
 * For example, given 2 and [[1,0]], there are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 * <p>
 * For another example, given 2 and [[1,0],[0,1]], there are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1.
 * So it is impossible.
 * <p>
 * <p>
 */
public class CourseSchedule {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {intA(intA(0, 1)), 2, true},
                {intA(intA(0, 1), intA(1, 0)), 2, false},
                {intA(intA(0, 1), intA(0, 2), intA(1, 2)), 3, true}
        };
    }

    @Test(dataProvider = "testData")
    public void test(int[][] courses, int numCourses, boolean isPossible) {
        assertEquals(canFinish(numCourses, courses), isPossible);
    }

    /**
     * idea os to check if graph contains cycle
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        boolean result = true;

        if (numCourses == 0 || prerequisites.length == 0) {
            return true;
        }

        //build graph
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        for (int i = 0; i < prerequisites.length; i++) {
            graph.computeIfAbsent(prerequisites[i][1], v -> new HashSet<>()).add(prerequisites[i][0]);
        }

        Set<Integer> visited = new HashSet<>();
        Set<Integer> onCurrStack = new HashSet<>();
        for (Integer startNode : graph.keySet()) {
            if (!visited.contains(startNode)) {
                if (!dfs(graph, visited, startNode, onCurrStack)) {
                    return false;
                }
            }
        }

        return result;
    }

    private boolean dfs(Map<Integer, Set<Integer>> graph, Set<Integer> visited, Integer node, Set<Integer> onCurrStack) {
        onCurrStack.add(node);
        visited.add(node);

        for (Integer adj : graph.getOrDefault(node, new HashSet<>())) {
            if (!visited.contains(adj)) {
                if (!dfs(graph, visited, adj, onCurrStack)) return false;
            } else {
                if (onCurrStack.contains(adj)) {
                    return false;
                }
            }
        }

        onCurrStack.remove(node);

        return true;
    }
}
