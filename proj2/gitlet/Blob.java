package gitlet;

import java.io.Serializable;

import static gitlet.Repository.BLOB_DIR;
import static gitlet.Utils.join;
import static gitlet.Utils.writeObject;

public class Blob implements Serializable {
    private String hashCode;
    private byte[] item;

    public Blob(String hashCode, byte[] item){
        this.hashCode = hashCode;
        this.item = item;
    }
    public String getHashCode(){
        return hashCode;
    }
    public byte[] getItem(){
        return item;
    }
    public void save(){
        writeObject(join(BLOB_DIR, getHashCode()), this);
    }

}
