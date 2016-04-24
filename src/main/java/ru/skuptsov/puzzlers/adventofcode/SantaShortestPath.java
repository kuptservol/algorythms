package ru.skuptsov.puzzlers.adventofcode;

import ru.skuptsov.algorythms.graph.presentation.Graph;
import ru.skuptsov.algorythms.graph.presentation.Vertex;
import ru.skuptsov.algorythms.graph.presentation.WeightedEdge;
import ru.skuptsov.algorythms.graph.presentation.adjacency.list.AdjacencyListDirectedGraph;
import ru.skuptsov.algorythms.graph.travellingsalesmanproblem.NearestNeighbourTCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 18/04/2016
 */
public class SantaShortestPath {

    public static void main(String[] args) throws IOException {

        BufferedReader sc = new BufferedReader(new InputStreamReader(SantaShortestPath.class.getResourceAsStream("/santaCities")));

        Graph<String, Vertex<String>, WeightedEdge<String>> graph = new AdjacencyListDirectedGraph<>();

        String line;
        while ((line = sc.readLine()) != null) {

            String[] params = line.split(" ");
            Vertex<String> first = new Vertex<>(params[0]);
            Vertex<String> second = new Vertex<>(params[2]);

            graph.addEdge(new WeightedEdge<>(first, second, new Double(params[4])));
            graph.addEdge(new WeightedEdge<>(second, first, new Double(params[4])));
        }

        NearestNeighbourTCP<String> tcp = new NearestNeighbourTCP<>(graph);

        List<WeightedEdge<String>> path = tcp.tcp();
        path.forEach(e -> System.out.println(e.getFromV().getValue() + " -> " + e.getToV().getValue()));

        System.out.println(path.stream().map(WeightedEdge::getWeight).reduce((a, b) -> a + b).get());

    }
}
