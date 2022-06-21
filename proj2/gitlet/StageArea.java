package gitlet;

import java.io.Serializable;
import java.util.TreeMap;

import static gitlet.Lazy.LoadCommit;
import static gitlet.Repository.*;
import static gitlet.Utils.*;

public class StageArea implements Serializable {
    private TreeMap<String, String> stagedForAddition;
    private TreeMap<String, String> stagedForRemoval;

    public StageArea(){
        stagedForAddition = new TreeMap<>();
        stagedForRemoval = new TreeMap<>();
    }
    public void AddFile(String name) {
        Commit commit = LoadCommit(readContentsAsString(join(POINT_DIR, "header")));
        String hashCode = sha1(readContents(join(SRC_DIR, name)));
        if (hashCode == commit.getBlobHashCode(name)
                || stagedForAddition.get(name) == null)
            stagedForAddition.remove(name);
        Blob blob = new Blob(hashCode, readContents(join(SRC_DIR, name)));
        blob.save();
        stagedForAddition.put(name, hashCode);

    }
    public void save(){
        writeObject(join(STAGE_DIR, "stage"), this);
    }
}
