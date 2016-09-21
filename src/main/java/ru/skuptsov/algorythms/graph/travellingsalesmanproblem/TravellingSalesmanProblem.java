package ru.skuptsov.algorythms.graph.travellingsalesmanproblem;

import ru.skuptsov.algorythms.structure.graph.WeightedEdge;

import java.util.List;

/**
 * @author Sergey Kuptsov
 * @since 01/05/2016
 */
public interface TravellingSalesmanProblem<P> {

    List<WeightedEdge<P>> buildShortestRoute();
}
