package ru.skuptsov.algorythms.paxos.message;

/**
 * Created by Сергей on 01.03.2016.
 */
public class Proposal {

    private long proposalId;
    private String value;

    public Proposal(int proposalId) {
        this.proposalId = proposalId;
    }

    public Proposal(String value, long proposalId) {
        this.value = value;
        this.proposalId = proposalId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getProposalId() {
        return proposalId;
    }

    public void setProposalId(int proposalId) {
        this.proposalId = proposalId;
    }
}
