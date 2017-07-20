package algorithm.paxos.message;

/**
 * Created by Сергей on 01.03.2016.
 */
public class PromiseEvent {

    private Proposal proposal;

    public PromiseEvent(Proposal proposal) {
        this.proposal = proposal;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }
}
