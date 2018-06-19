package puzzler.interview.y;

import org.joda.time.DateTimeUtils;
import org.junit.Test;
import org.testng.Assert;

/**
 * @author Sergey Kuptsov
 */
public class RPSLimiter {

    private final static class Limiter {
        private final long[] requests;
        private int currPos = 0;

        public Limiter(int rps) {
            requests = new long[rps];
        }

        public boolean request(Object request) {
            long now = DateTimeUtils.currentTimeMillis();

            boolean result = false;
            if (requests[currPos] == 0) {
                requests[currPos] = now;
                result = true;
            } else {
                if (now - requests[currPos] > 1000) {
                    requests[currPos] = now;
                    result = true;
                }
            }

            currPos = currPos == requests.length - 1 ? 0 : currPos + 1;

            return result;
        }
    }

    @Test
    public void testRPSSucceed() {
        Object req = new Object();
        Limiter limiter = new Limiter(3);

        DateTimeUtils.setCurrentMillisFixed(1000);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(1100);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(1200);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(2001);

        Assert.assertTrue(limiter.request(req));
    }


    @Test
    public void testRPSNotSucceed() {
        Object req = new Object();
        Limiter limiter = new Limiter(3);

        DateTimeUtils.setCurrentMillisFixed(1000);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(1900);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(1950);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(2020);

        Assert.assertTrue(limiter.request(req));

        DateTimeUtils.setCurrentMillisFixed(2025);

        Assert.assertFalse(limiter.request(req));
    }
}
