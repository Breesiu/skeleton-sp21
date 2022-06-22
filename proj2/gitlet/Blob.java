package gitlet;

import java.io.Serializable;

import static gitlet.Repository.BLOB_DIR;
import static gitlet.Utils.*;

public class Blob implements Serializable {
    private String hashCode;
    private byte[] item;
    private String fileName;
    public Blob(String hashCode, byte[] item, String fileName){
        this.hashCode = hashCode;
        this.item = item;
        this.fileName = fileName;
    }
    public String getHashCode(){
        return hashCode;
    }
    public byte[] getItem(){
        return item;
    }
    public String fileName(){
        return fileName;
    }
    public static Blob fromFile(String hashCode){
        return readObject(join(BLOB_DIR, hashCode), Blob.class);
    }
    public void save(){
        writeObject(join(BLOB_DIR, getHashCode()), this);
    }

}
