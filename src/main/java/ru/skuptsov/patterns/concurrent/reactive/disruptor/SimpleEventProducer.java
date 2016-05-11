package ru.skuptsov.patterns.concurrent.reactive.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

/**
 * @author Sergey Kuptsov
 * @since 19/04/2016
 */
public class SimpleEventProducer {

    private static final EventTranslatorOneArg<SimpleEvent, Long> TRANSLATOR =
            (event, sequence, bb) -> event.setValue(bb);
    private final RingBuffer<SimpleEvent> ringBuffer;

    public SimpleEventProducer(RingBuffer<SimpleEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Long bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }

}
