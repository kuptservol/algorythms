package ru.skuptsov.puzzlers.job.interview.leetcode.graph;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;
import static ru.skuptsov.puzzlers.job.interview.leetcode.PuzzlerUtils.putIfAbsent;

/**
 * @author Sergey Kuptsov
 * @since 11/10/2016
 * Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order.
 * All of the tickets belong to a man who departs from JFK. Thus, the itinerary must begin with JFK.
 */
public class CorrectItinerary {

    private static final String START_AIRPORT = "JFK";
    String[][] tC0 = {{"JFK", "TIA"}, {"JFK", "ANU"}, {"ANU", "JFK"}};
    String[][] tC1 = {{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}};
    String[][] tC2 = {{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"}, {"ATL", "JFK"}, {"ATL", "SFO"}};
    String[][] tC3 = {{"EZE", "AXA"}, {"TIA", "ANU"}, {"ANU", "JFK"}, {"JFK", "ANU"}, {"ANU", "EZE"}, {"TIA", "ANU"}, {"AXA", "TIA"}, {"TIA", "JFK"}, {"ANU", "TIA"}, {"JFK", "TIA"}};
    String[][] tC4 = {{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}};
    String[][] tC5 = {{"EZE", "AXA"}, {"TIA", "ANU"}, {"ANU", "JFK"}, {"JFK", "ANU"}, {"ANU", "EZE"}, {"TIA", "ANU"}, {"AXA", "TIA"}, {"TIA", "JFK"}, {"ANU", "TIA"}, {"JFK", "TIA"}};
    List<List<String>> correctItineraries = new ArrayList<>();
    int ticketsNum;
    /**
     * Hierholzer’s algorithm to find a Eulerian path.
     */
    HashMap<String, PriorityQueue<String>> map = new HashMap<>();
    LinkedList<String> result = new LinkedList<>();

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {tC0, of("JFK", "TIA", "JFK", "ANU")},
                {tC1, of("JFK", "MUC", "LHR", "SFO", "SJC")},
                {tC2, of("JFK", "ATL", "JFK", "SFO", "ATL", "SFO")},
                {tC5, of("JFK", "ANU", "EZE", "AXA", "TIA", "ANU", "JFK", "TIA", "ANU", "TIA", "JFK")},
        };
    }

    @Test(dataProvider = "testData")
    public void test(String[][] a, List<String> b) {
        assertEquals(findItinerary(a), b);
    }

    public List<String> findItinerary(String[][] tickets) {
        for (String[] ticket : tickets) {
            if (!map.containsKey(ticket[0])) {
                PriorityQueue<String> q = new PriorityQueue<>();
                map.put(ticket[0], q);
            }
            map.get(ticket[0]).offer(ticket[1]);
        }

        dfs("JFK");
        return result;
    }

    public void dfs(String s) {
        PriorityQueue<String> q = map.get(s);

        while (q != null && !q.isEmpty()) {
            dfs(q.poll());
        }

        result.addFirst(s);
    }

    /**
     * DFS
     */
    public List<String> findItineraryV2(String[][] tickets) {
        Map<String, List<String>> ticketsGraph = new HashMap<>();
        for (String[] fromTo : tickets) {
            List<String> destinations = putIfAbsent(ticketsGraph, fromTo[0], new ArrayList<>());
            destinations.add(fromTo[1]);
        }
        ticketsNum = tickets.length + 1;

        travel(START_AIRPORT, ticketsGraph, new ArrayList<>());

        return correctItineraries.stream()
                .sorted((l1, l2) -> {
                    int compareLenghthes = Integer.compare(l2.size(), l1.size());
                    if (compareLenghthes != 0) {
                        return compareLenghthes;
                    }

                    for (int i = 0; i < l1.size(); i++) {
                        String s1 = l1.get(i);
                        String s2 = l2.get(i);
                        int compareTo = s1.compareTo(s2);
                        if (compareTo != 0) {
                            return compareTo;
                        }
                    }

                    return 0;
                })
                .findFirst()
                .orElse(new ArrayList<>());
    }

    private void travel(String airportFrom, Map<String, List<String>> leftTickets, List<String> currentPath) {
        currentPath.add(airportFrom);
        List<String> airportsTo = leftTickets.get(airportFrom);
        if (airportsTo == null || airportsTo.size() == 0) {
            if (currentPath.size() == ticketsNum)
                correctItineraries.add(currentPath);

            return;
        }

        for (String airportTo : airportsTo) {
            Map<String, List<String>> leftTicketsCopy = cloneTickets(leftTickets);
            leftTicketsCopy.get(airportFrom).remove(airportTo);
            travel(airportTo, leftTicketsCopy, new ArrayList<>(currentPath));
        }

    }

    private Map<String, List<String>> cloneTickets(Map<String, List<String>> leftTickets) {
        return leftTickets.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> new ArrayList<>(v.getValue())));
    }
}
