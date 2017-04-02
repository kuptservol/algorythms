package ru.skuptsov.java.concurrent.test;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult1;

/**
 * @author Sergey Kuptsov
 * @since 02/04/2017
 */
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE_INTERESTING, desc = "One update lost: atomicity failure.")
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Actors updated independently.")
@State
public class NonVolatileSynchronizedIncrementTest {

    int v;

    @Actor
    public void actor1() {
        synchronized (this) {
            v++;
        }
    }

    @Actor
    public void actor2() {
        synchronized (this) {
            v++;
        }
    }

    @Arbiter
    public void arbiter(IntResult1 r) {
        r.r1 = v;
    }
}

