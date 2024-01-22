package E_Voting.Model;

import org.hyperledger.fabric.contract.annotation.DataType;

@DataType
public class PublicParam {

    private String g;
    private String p;
    private String q;

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
