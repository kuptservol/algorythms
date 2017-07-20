package algorithm.random;

import org.joda.time.DateTime;

/**
 * @author Sergey Kuptsov
 * @since 21/09/2016
 */
public class FisherYatesOriginalShuffle {

    public static void main(String[] args) {
        long[] seq = {1, 2, 3, 4, 5, 6, 7, 8};
        LongShuffle shuffle = new LongShuffle(seq);

        while (shuffle.hasNext()) {
            System.out.print(" " + shuffle.next());
        }
    }

    private static class LongShuffle {
        private long[] sequence;
        private int range;

        public LongShuffle(long[] sequence) {
            this.sequence = sequence;
            this.range = sequence.length;
        }

        public boolean hasNext() {
            return range > 0;
        }

        public long next() {
            Long rollPos = DateTime.now().getMillis() % range;
            long roll = sequence[rollPos.intValue()];
            sequence[rollPos.intValue()] = sequence[range - 1];
            sequence[range - 1] = roll;
            range -= 1;

            return roll;
        }
    }
}
