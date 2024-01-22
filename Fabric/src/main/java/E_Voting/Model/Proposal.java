package E_Voting.Model;

import org.hyperledger.fabric.contract.annotation.DataType;

import java.util.LinkedHashMap;
import java.util.List;

@DataType
public class Proposal {
    private String proposalId;
    private String deadline;
    private String type;
    private String communityId;
    private String managerId;
    private String topic;
    private String desc;
    private List<LinkedHashMap> selections;
    private String docType;
    private Boolean hasResult;

    public Boolean getHasResult() {
        return hasResult;
    }

    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<LinkedHashMap> getSelections() {
        return selections;
    }

    public void setSelections(List<LinkedHashMap> selections) {
        this.selections = selections;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}
