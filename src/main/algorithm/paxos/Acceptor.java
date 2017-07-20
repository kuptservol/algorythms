package algorithm.paxos;

import algorithm.paxos.message.AcceptEvent;
import algorithm.paxos.message.ProposalEvent;

/**
 * Created by Сергей on 01.03.2016.
 */
public interface Acceptor {

    void receivePrepare(ProposalEvent proposal);

    void accept(AcceptEvent acceptEvent);

}
