package puzzler.leetcode.stringarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import static com.google.common.collect.ImmutableList.of;

/**
 * @author Sergey Kuptsov
 * @since 09/09/2016
 * <p>
 * Given two words (start and end), and a dictionary,
 * find the length of shortest transformation sequence from start to end, such that only one letter can be changed
 * at a time and each intermediate word must exist in the dictionary.
 * For example, given:
 * <p>
 * start = "hit"
 * end = "cog"
 * dict = ["hot","dot","dog","lot","log"]
 * jm * One shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog", the program should return its length 5.
 */
public class WordLadder {

    static String[] dict = {"hot", "dot", "dog", "lot", "log"};
    static String start = "hit";
    static String end = "cog";

    /**
     * Solution - use BFS
     *
     * @param args
     */
    public static void main(String[] args) {
        Graph graph = new Graph();

        Set<String> v = new HashSet<>();
        v.addAll(Arrays.asList(dict));
        v.add(start);
        v.add(end);

        for (String node : v) {
            graph.adjacencySet.put(node, new HashSet<>());
            for (String node2 : v) {
                if (isInOneTransform(node, node2)) {
                    graph.adjacencySet.get(node).add(node2);
                }
            }
        }

        List<String> pathToEnd = bfs(graph);
        System.out.println(pathToEnd.size() + 1);
    }

    protected static List<String> bfs(Graph graph) {
        Queue<String> bfsQ = new LinkedList<>();
        Map<String, List<String>> paths = new HashMap<>();
        paths.put(start, new ArrayList<>());
        bfsQ.add(start);

        String nextV;
        while ((nextV = bfsQ.poll()) != null) {
            for (String adjVertex : graph.adjacencySet.get(nextV)) {
                bfsQ.add(adjVertex);
                List<String> pathToNextV = paths.get(nextV);
                List<String> pathToAdjVertex = new ArrayList<>();
                pathToAdjVertex.addAll(pathToNextV);
                pathToAdjVertex.add(nextV);

                paths.put(adjVertex, pathToAdjVertex);

                if (adjVertex.equals(end)) {
                    return paths.get(end);
                }
            }
        }

        return of();
    }

    static boolean isInOneTransform(String a, String b) {
        int diff = 0;

        char[] charsA = a.toCharArray();
        char[] charsB = b.toCharArray();

        for (int i = 0; i < charsA.length; i++) {
            if (charsB[i] != charsA[i]) {
                diff++;
            }
        }

        if (diff == 1) {
            return true;
        } else {
            return false;
        }
    }

    private static class Graph {
        Map<String, Set<String>> adjacencySet = new HashMap<>();

    }
}
