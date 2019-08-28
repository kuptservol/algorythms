package puzzler.adventofcode;

/**
 * @author Sergey Kuptsov
 */
public class Pair<T, E> {

    private final T t;
    private final E e;

    public Pair(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public T getKey() {
        return t;
    }

    public E getValue() {
        return e;
    }
}
