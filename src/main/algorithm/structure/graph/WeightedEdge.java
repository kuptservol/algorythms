package algorithm.structure.graph;

import com.google.common.base.MoreObjects;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public class WeightedEdge<P> extends Edge<P> {

    private final Double weight;

    public WeightedEdge(Vertex<P> fromV, Vertex<P> toV, Double weight) {
        super(fromV, toV);
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fromV", fromV)
                .add("toV", toV)
                .add("weight", weight)
                .toString();
    }
}
