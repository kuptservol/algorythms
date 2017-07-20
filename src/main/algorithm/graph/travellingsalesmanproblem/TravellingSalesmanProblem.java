package algorithm.graph.travellingsalesmanproblem;

import java.util.List;

import algorithm.structure.graph.WeightedEdge;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public interface TravellingSalesmanProblem<P> {

    List<WeightedEdge<P>> buildShortestRoute();
}
