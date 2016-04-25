package ru.skuptsov.patterns.concurrent.reactive.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author Sergey Kuptsov
 * @since 19/04/2016
 */
public class SimpleEventFactory implements EventFactory<SimpleEvent> {

    @Override
    public SimpleEvent newInstance() {
        return new SimpleEvent();
    }
}
