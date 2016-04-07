package ru.skuptsov.algorythms.paxos.message;

/**
 * Created by Сергей on 01.03.2016.
 */
public class ProposalEvent {

    private Proposal proposal;

    public ProposalEvent(Proposal proposal) {
        this.proposal = proposal;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }
}
