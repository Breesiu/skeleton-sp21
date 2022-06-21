package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Shichengxin
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */

    private String message;
    /* TODO: fill in the rest of this class. */
    private Date date;
    private ArrayList<String> parent;
    private TreeMap<String, String> blobs;
    private String hashCode;

    public Commit(String message, Date date){
        this.message = message;
        this.date = date;
        parent = new ArrayList<>();
        blobs = new TreeMap<>();
    }
    public Commit(){

    }
    public void setHashCode(){
        hashCode = Utils.sha1(Utils.serialize(this));
    }
    public String getHashCode(){
        return hashCode;
    }
    public String getBlobHashCode(String name){
        return blobs.get(name);
    }
    public void addBlob(){}
    public void save(){
        writeObject(join(COMMIT_DIR, getHashCode()), this);
    }
    public static Commit fromFile(String hashCode){
        return readObject(join(COMMIT_DIR, hashCode), Commit.class);
    }
}
