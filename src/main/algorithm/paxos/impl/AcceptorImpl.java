package algorithm.paxos.impl;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import algorithm.paxos.Acceptor;
import algorithm.paxos.message.AcceptEvent;
import algorithm.paxos.message.ProposalEvent;

/**
 * Created by Сергей on 01.03.2016.
 */
@Component
public class AcceptorImpl implements Acceptor {

    @Autowired
    private EventBus eventBus;

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void receivePrepare(ProposalEvent proposal) {

    }

    @Override
    @Subscribe
    @AllowConcurrentEvents
    public void accept(AcceptEvent acceptEvent) {

    }

    @PostConstruct
    public void init() {
        eventBus.register(this);
    }
}
