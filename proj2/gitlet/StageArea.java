package gitlet;

import java.io.Serializable;
import java.util.TreeMap;
import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Repository.POINT_DIR;
import static gitlet.Utils.*;

public class StageArea implements Serializable {
    private TreeMap<String, String> stagedForAddition;
    private TreeMap<String, String> stagedForRemoval;

    public StageArea(){
        stagedForAddition = new TreeMap<>();
        stagedForRemoval = new TreeMap<>();
    }
    public static void AddFile(String name){
        Commit commit = readObject(join(COMMIT_DIR, readContentsAsString(join(POINT_DIR, name))), Commit.class);

    }
}
