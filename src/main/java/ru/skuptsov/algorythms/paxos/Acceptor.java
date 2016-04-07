package ru.skuptsov.algorythms.paxos;

import ru.skuptsov.algorythms.paxos.message.ProposalEvent;
import ru.skuptsov.algorythms.paxos.message.AcceptEvent;

/**
 * Created by Сергей on 01.03.2016.
 */
public interface Acceptor {

    void receivePrepare(ProposalEvent proposal);
    void accept(AcceptEvent acceptEvent);

}
