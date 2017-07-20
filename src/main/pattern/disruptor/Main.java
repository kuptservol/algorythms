package pattern.disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * @author Sergey Kuptsov
 * @since 19/04/2016
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Executor executor = Executors.newCachedThreadPool();

        SimpleEventFactory simpleEventFactory = new SimpleEventFactory();

        int bufferSize = 1024;

        Disruptor<SimpleEvent> disruptor = new Disruptor<>(simpleEventFactory, bufferSize, executor);

        disruptor.handleEventsWith(new SimpleEventHandler());

        RingBuffer<SimpleEvent> ringBuffer = disruptor.start();

        SimpleEventProducer producer = new SimpleEventProducer(ringBuffer);

        for (int i = 0; i < 100; i++) {
            producer.onData((long) i);
            Thread.sleep(1000);

        }

    }
}
