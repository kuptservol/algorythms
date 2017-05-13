package ru.skuptsov.puzzlers.job.interview.leetcode.sorting;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static com.google.common.collect.ImmutableSet.of;
import static java.util.Optional.ofNullable;

public class TopologicalSort {

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {ImmutableMap.of(0, of(5, 6), 3, of(5), 5, of(4))},
                {ImmutableMap.of(0, of(2, 1), 2, of(1))}
        };
    }

    @Test(dataProvider = "testData")
    public void test(Map<Integer, Set<Integer>> graph) {
        System.out.println(topoligicalSort(graph));
    }

    public static List<Integer> topoligicalSort(Map<Integer, Set<Integer>> graph) {
        LinkedList<Integer> traverse = new LinkedList<>();

        Set<Integer> marked = new HashSet<>();

        for (Integer next : graph.keySet()) {
            if (!marked.contains(next)) {
                dfs(next, marked, graph, traverse);
            }
        }

        return traverse;
    }

    public static void dfs(Integer next, Set<Integer> marked, Map<Integer, Set<Integer>> graph, LinkedList<Integer> traverse) {

        marked.add(next);
        for (Integer connected : ofNullable(graph.get(next)).orElse(ImmutableSet.of())) {
            if (!marked.contains(connected)) {
                dfs(connected, marked, graph, traverse);
            }
        }

        traverse.addFirst(next);
    }
}
