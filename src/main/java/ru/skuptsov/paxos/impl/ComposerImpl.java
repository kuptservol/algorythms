package ru.skuptsov.paxos.impl;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AtomicLongMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skuptsov.paxos.Composer;
import ru.skuptsov.paxos.message.PromiseEvent;
import ru.skuptsov.paxos.message.Proposal;
import ru.skuptsov.paxos.message.ProposalEvent;

import javax.annotation.PostConstruct;

import static org.joda.time.DateTime.now;
import static org.joda.time.DateTimeZone.UTC;

/**
 * Created by Сергей on 01.03.2016.
 */
@Component
public class ComposerImpl implements Composer {

    private static final AtomicLongMap<Integer> received = AtomicLongMap.create();

    @Autowired
    private EventBus eventBus;

    @Override
    public void prepare(String value) {

        eventBus.post(new ProposalEvent(new Proposal(value, now(UTC).getMillis())));
    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void receivePromise(PromiseEvent promiseEvent) {

    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }
}
