package ru.skuptsov.algorythms.graph.presentation;

/**
 * @author Sergey Kuptsov
 * @since 17/04/2016
 */
public abstract class AbstractGraph<V extends Vertex, E extends Edge> implements Graph<V, E> {

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
