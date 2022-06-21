package gitlet;

import java.io.Serializable;
import java.util.*;

import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class StageArea implements Serializable {

    private TreeMap<String, String> stagedForAddition;
    private TreeSet<String> stagedForRemoval;

    public StageArea() {
        stagedForAddition = new TreeMap<>();
        stagedForRemoval = new TreeSet<>();
    }
    public TreeMap<String, String> getStagedForAddition(){
        return stagedForAddition;
    }
    public TreeSet<String> getStagedForRemoval(){
        return stagedForRemoval;
    }
    public boolean isEmpty(){
        if(stagedForAddition.isEmpty() && stagedForRemoval.isEmpty())
            return true;
        return false;
    }
    public void AddFile(String name) {
        Commit commit = Commit.fromFile(Header.fromFile().getHashCode());
        String hashCode = sha1(readContents(join(CWD, name)));
        if (hashCode == commit.getBlobHashCode(name)
                || stagedForAddition.get(name) == null)
            stagedForAddition.remove(name);
        Blob blob = new Blob(hashCode, readContents(join(CWD, name)), name);
        blob.save();

        stagedForAddition.put(name, hashCode);

    }
    public boolean ExistInStagedForAddition(String fileName){
        return stagedForAddition.containsKey(fileName);
    }
    public void unstageForAddition(String fileName){
        stagedForAddition.remove(fileName);
    }
    public void stageStagedForRemoval(String fileName){
        stagedForRemoval.add(fileName);
    }
    public void cleanStaged(){
        stagedForAddition.clear();
        stagedForRemoval.clear();
    }
    public void save(){
        writeObject(join(STAGE_DIR, "stage"), this);
    }
    public static StageArea FromFile(){
        return readObject(join(STAGE_DIR, "stage"), StageArea.class);
    }
}
