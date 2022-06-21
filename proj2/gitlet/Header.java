package gitlet;

import java.io.Serializable;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class Header implements Serializable {
    private String hashCode;
    private String branch;

    public Header(String hashCode, String branch){
        this.hashCode = hashCode;
        this.branch = branch;
    }
    public void save(){
        writeObject(join(POINT_DIR, "header"), this);
    }
    public static Header fromFile(){
        return readObject(join(POINT_DIR, "header"), Header.class);
    }
    public String getHashCode(){
        return hashCode;
    }
    public String getBranch(){
        return branch;
    }
    public void setHashCode(String hashCode){
        this.hashCode = hashCode;
    }
    public void setBranch(String branch) {
        this.branch = branch;
    }
}
