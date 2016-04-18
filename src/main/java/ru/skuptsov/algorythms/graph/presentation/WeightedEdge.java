package ru.skuptsov.algorythms.graph.presentation;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class WeightedEdge extends Edge {

    private final Double weight;

    public WeightedEdge(Vertex fromV, Vertex toV, Double weight) {
        super(fromV, toV);
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
