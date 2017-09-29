package java_.concurrent.jcstresstest;

import java.util.ArrayList;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult1;

/**
 * @author Sergey Kuptsov <kuptservol@yandex-team.ru>
 */
@JCStressTest
@Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "One update lost: atomicity failure.")
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Actors updated independently.")
@State
public class NonThreadSafeCollectionUnderSyncContextFailTest {

    ArrayList<Integer> list = new ArrayList<>();

    @Actor
    public void actor1() {
        list.add(1);
    }

    @Actor
    public void actor2() {
        list.add(1);
    }

    @Arbiter
    public void arbiter(IntResult1 r) {
        r.r1 = list.size();
    }
}
