package ru.skuptsov.paxos;

import org.springframework.stereotype.Component;
import ru.skuptsov.paxos.message.AcceptEvent;
import ru.skuptsov.paxos.message.ProposalEvent;

/**
 * Created by Сергей on 01.03.2016.
 */
public interface Acceptor {

    void receivePrepare(ProposalEvent proposal);
    void accept(AcceptEvent acceptEvent);

}
