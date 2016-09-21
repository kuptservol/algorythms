package ru.skuptsov.algorythms.structure.graph;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public abstract class AbstractGraph<P, V extends Vertex<P>, E extends Edge<P>> implements Graph<P, V, E> {

    protected int vNum;
    protected int eNum;

    @Override
    public int vNum() {
        return vNum;
    }

    @Override
    public int eNum() {
        return eNum;
    }
}
