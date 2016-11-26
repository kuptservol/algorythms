package ru.skuptsov.puzzlers.job.interview.programcreek.graph;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

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
 * Solution : detect graph cycles
 */
public class CourseSchedule {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {of(of(0, 1, 2), of(1, 3, 4), of(2, 5, 6)), true},
                {of(of(0, 1, 2), of(1, 3, 4), of(2, 5, 6), of(3, 6)), false},
                {of(of(2), of(1, 0)), true},
                {of(of(0, 1), of(1, 0)), false},
                {of(of(0, 5, 4, 3, 2, 1), of(5, 0)), false},
        };
    }

    @Test(dataProvider = "testData")
    public void test(List<List<Integer>> courses, boolean isPossible) {
        assertEquals(hasCycle(buildGraph(courses)), isPossible);
    }

    private boolean hasCycle(Graph graph) {
        Queue<Integer> traversalQueue = new LinkedBlockingQueue<>();

        Integer startV = graph.adjList.keySet().toArray(new Integer[0])[0];
        Set<Integer> visitedNodes = new HashSet<>();

        traversalQueue.add(startV);

        while (!traversalQueue.isEmpty()) {
            Integer nextV = traversalQueue.poll();
            if (visitedNodes.contains(nextV)) {
                return false;
            } else {
                visitedNodes.add(nextV);
            }

            System.out.println(nextV);
            for (Integer linkedV : graph.adjList.get(nextV)) {
                traversalQueue.add(linkedV);
            }
        }

        return true;
    }

    private Graph buildGraph(List<List<Integer>> courses) {
        Graph graph = new Graph();

        for (List<Integer> targetCourses : courses) {
            Integer v = targetCourses.get(0);
            graph.adjList.putIfAbsent(v, new HashSet<>());

            for (int i = 1; i < targetCourses.size(); i++) {
                Integer linkedV = targetCourses.get((i));
                graph.adjList.putIfAbsent(linkedV, new HashSet<>());
                graph.adjList.get(v).add(linkedV);
            }
        }

        return graph;
    }

    private static class Graph {
        Map<Integer, Set<Integer>> adjList = new HashMap<>();
    }
}
