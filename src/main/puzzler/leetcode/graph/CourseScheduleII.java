package puzzler.leetcode.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableList.of;
import static com.google.common.primitives.Ints.toArray;
import static org.testng.Assert.assertEquals;

/**
 * There are a total of n vCourses you have to take, labeled from 0 to n - 1.
 * <p>
 * Some vCourses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 * <p>
 * Given the total number of vCourses and a list of prerequisite pairs, return the ordering of vCourses you should take to finish all vCourses.
 * <p>
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all vCourses, return an empty array.
 * <p>
 * There are a total of 2 vCourses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1]
 */
public class CourseScheduleII {

    int[][] tOne = {{1, 0}};
    int[][] tTwo = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
    int[][] tThree = {{1, 0}, {0, 1}};

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {2, tOne, toArray(of(0, 1))},
                {4, tTwo, toArray(of(0, 2, 1, 3))},
                {2, tThree, toArray(of())},
        };
    }

    @Test(dataProvider = "testData")
    public void findOrder(int numCourses, int[][] prerequisites, int[] answer) {
        int[] order = findOrder(numCourses, prerequisites);
        System.out.println("Answer: " + Arrays.toString(order));
        assertEquals(order, answer);
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if (numCourses == 0) {
            return null;
        }

        Map<Integer, Set<Integer>> adjSet = new HashMap<>();

        for (int i = 0; i < prerequisites.length; i++) {
            int[] listOfEdges = prerequisites[i];
            adjSet.computeIfAbsent(listOfEdges[1], (v) -> new HashSet<>()).add(listOfEdges[0]);
        }

        LinkedList<Integer> courseIternary = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> onCurrStack = new HashSet<>();
        for (Integer next : adjSet.keySet()) {
            if (!visited.contains(next))
                if (!dfs(next, adjSet, courseIternary, visited, onCurrStack)) {
                    return new int[0];
                }
        }

        for (int i = 0; i < numCourses; i++) {
            if (!courseIternary.contains(i)) {
                courseIternary.addFirst(i);
            }
        }

        return courseIternary.stream().mapToInt(Integer::intValue).toArray();
    }

    private boolean dfs(Integer nextCourse, Map<Integer, Set<Integer>> adjSet,
            LinkedList<Integer> courseIternary, Set<Integer> visited, Set<Integer> onCurrStack)
    {
        visited.add(nextCourse);
        onCurrStack.add(nextCourse);

        for (Integer next : adjSet.getOrDefault(nextCourse, new HashSet<>())) {
            if (!visited.contains(next)) {
                if (!dfs(next, adjSet, courseIternary, visited, onCurrStack)) {
                    return false;
                }
            } else {
                if (onCurrStack.contains(next)) {
                    return false;
                }
            }
        }

        courseIternary.addFirst(nextCourse);

        onCurrStack.remove(nextCourse);

        return true;
    }
    //  0 -> 1, 2
    //  1 -> 3
    //  2 -> 3
}
