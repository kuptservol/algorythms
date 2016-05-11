package ru.skuptsov.algorythms.graph.travellingsalesmanproblem;

import com.google.common.collect.ImmutableList;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.skuptsov.algorythms.graph.presentation.Graph;
import ru.skuptsov.algorythms.graph.presentation.Vertex;
import ru.skuptsov.algorythms.graph.presentation.WeightedEdge;
import ru.skuptsov.algorythms.graph.presentation.adjacency.list.AdjacencyListDirectedGraph;

import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public class TCPTest {

    @DataProvider
    public Object[][] edges() {

        Vertex<String> london = new Vertex<>("London");
        Vertex<String> belfast = new Vertex<>("Belfast");
        Vertex<String> dublin = new Vertex<>("Dublin");
        ImmutableList<WeightedEdge<String>> weightedEdges1 = of(
                new WeightedEdge<>(dublin, london, 464d),
                new WeightedEdge<>(belfast, london, 518d),
                new WeightedEdge<>(belfast, dublin, 141d));

        Vertex<String> faerun = new Vertex<>("Faerun");
        Vertex<String> tristram = new Vertex<>("Tristram");
        Vertex<String> arbre = new Vertex<>("Arbre");
        Vertex<String> straighlight = new Vertex<>("Straighlight");
        ImmutableList<WeightedEdge<String>> weightedEdges2 = of(
                new WeightedEdge<>(faerun, tristram, 65d),
                new WeightedEdge<>(faerun, straighlight, 137d),
                new WeightedEdge<>(straighlight, arbre, 14d),
                new WeightedEdge<>(arbre, tristram, 14d),
                new WeightedEdge<>(faerun, arbre, 149d),
                new WeightedEdge<>(straighlight, tristram, 125d));

        return new Object[][]{
                {weightedEdges1, 605d},
                //{weightedEdges2, 93d}
        };
    }

    @Test(dataProvider = "edges")
    public void testTCP(List<WeightedEdge<String>> weightedEdges, Double result) {
        Graph<String, Vertex<String>, WeightedEdge<String>> graph = buildGraph(weightedEdges);

        TravellingSalesmanProblem<String> tcp = new BruteForceTCP<>(graph);
        List<WeightedEdge<String>> shortestPath = tcp.buildShortestRoute();

        shortestPath.forEach(e -> System.out.println(e.getFromV().getValue() + " -> " + e.getToV().getValue() + " " + e.getWeight()));
        Double shortestPathValue = shortestPath.parallelStream().map(WeightedEdge::getWeight).reduce((a, b) -> a + b).get();
        System.out.println("Path length : " + shortestPathValue);
        System.out.println("----------------------");
        assertEquals(shortestPathValue, result);

    }

    private Graph<String, Vertex<String>, WeightedEdge<String>> buildGraph(List<WeightedEdge<String>> weightedEdges) {
        AdjacencyListDirectedGraph<WeightedEdge<String>, String> graph = new AdjacencyListDirectedGraph<>();

        for (WeightedEdge<String> weightedEdge : weightedEdges) {
            graph.addEdge(weightedEdge);
            graph.addEdge(new WeightedEdge<>(weightedEdge.getToV(), weightedEdge.getFromV(), weightedEdge.getWeight()));
        }

        return graph;
    }
}
