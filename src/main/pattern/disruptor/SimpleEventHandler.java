package pattern.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author Sergey Kuptsov
 * @since 19/04/2016
 */
public class SimpleEventHandler implements EventHandler<SimpleEvent> {

    @Override
    public void onEvent(SimpleEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + event + " sequnce:" + sequence + " endOfBatch:" + endOfBatch);
    }
}
