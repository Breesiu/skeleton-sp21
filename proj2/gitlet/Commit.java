package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.*;

import static gitlet.Repository.COMMIT_DIR;
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
    private String branch;
    public Commit(String message, Date date, String branch){
        this.message = message;
        this.date = date;
        this.branch = branch;
        parent = new ArrayList<>();
        blobs = new TreeMap<>();
    }
    public Commit(){

    }
    public void setHashCode(){
        hashCode = sha1(serialize(this));
    }
    public String getHashCode(){
        return hashCode;
    }
    public String getBlobHashCode(String name){
        return blobs.get(name);
    }
    public TreeMap<String, String > getBlobs(){
        return blobs;
    }
    public ArrayList<String> getParent(){
        return parent;
    }
    public String getMessage(){
        return message;
    }
    public void addBlob(){}
    public void inheritBlob(Commit parent){
        this.blobs = parent.blobs;
    }
    public void addParent(String parent){
        this.parent.add(parent);
    }
    public void save(){
        writeObject(join(COMMIT_DIR, getHashCode()), this);
    }
    //help the function rm
    public boolean existBlob(String fileName){
        return blobs.containsKey(fileName);
    }
    public static Commit fromFile(String hashCode){
        return readObject(join(COMMIT_DIR, hashCode), Commit.class);
    }
    public void updateCommitWithStagedArea(StageArea stageArea){
        //if the old fileName is same of the new, will replace it TreeMap  addtion
        Set<Map.Entry<String,String >> entrySet = stageArea.getStagedForAddition().entrySet();
        for(Map.Entry<String,String> entry : entrySet){
            blobs.put(entry.getKey(), entry.getValue());
        }
        //removal   if the Set == null?
        for(String entry: stageArea.getStagedForRemoval()){
            blobs.remove(entry);
        }
    }
    // if NUll, print noting
    public void print(){
        if(parent.size() == 2){
            System.out.println("===");
            System.out.println("Merge " + getParent().get(0).substring(0, 7) + ' ' + getParent().get(1).substring(0, 7));
            System.out.println("Date" + date.toString());
            System.out.println(getMessage());
            System.out.println("Merged" + getParent().get(1));
            System.out.println();
        }
        else { // include the initial commit
            System.out.println("===");
            System.out.println("commit " + getHashCode());
            System.out.println("Date" + date.toString());
            System.out.println(getMessage());
            System.out.println();
        }
    }
    public void printFind(String message){
        //don't jugde String = String
        if(this.message.equals(message))
            System.out.println(getHashCode());
    }
}
