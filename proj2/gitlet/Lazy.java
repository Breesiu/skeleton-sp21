package gitlet;

import static gitlet.Repository.COMMIT_DIR;
import static gitlet.Repository.STAGE_DIR;
import static gitlet.Utils.*;
public class Lazy {

    public static Commit LoadCommit(String hashcode){
        return readObject(join(COMMIT_DIR, hashcode), Commit.class);
    }
    public static StageArea LoadStageArea(){
        return readObject(join(STAGE_DIR, "stage"), StageArea.class);
    }
}
