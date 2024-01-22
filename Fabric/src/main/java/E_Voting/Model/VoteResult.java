package E_Voting.Model;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.HashMap;

@DataType
public class VoteResult {
    @Property()
    private String proposalId;

    @Property()
    private Integer totalVotes;

    @Property()
    private Integer validatedVotes;

    @Property()
    private HashMap<String,Integer> count;

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getValidatedVotes() {
        return validatedVotes;
    }

    public void setValidatedVotes(Integer validatedVotes) {
        this.validatedVotes = validatedVotes;
    }

    public HashMap<String, Integer> getCount() {
        return count;
    }

    public void setCount(HashMap<String, Integer> count) {
        this.count = count;
    }
}
