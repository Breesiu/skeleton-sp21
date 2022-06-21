package gitlet;

import java.io.File;
import java.util.Date;

import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Shichengxin
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");
    public static final File SRC_DIR = join(CWD, "gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    public static final File BLOB_DIR = join(GITLET_DIR, "blob");
    public static final File POINT_DIR = join(GITLET_DIR, "pointer");

    /* TODO: fill in the rest of this class. */
//    private StageArea stageArea = new StageArea();

    public static void init(){
        if(GITLET_DIR.exists()) {
            System.out.print("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        STAGE_DIR.mkdir();
        COMMIT_DIR.mkdir();
        BLOB_DIR.mkdir();
        POINT_DIR.mkdir();
        Commit initialCommit = new Commit("initial commit", new Date(0), null, null);
        initialCommit.setHashCode();
        String master = initialCommit.getHashCode();
        String header = initialCommit.getHashCode();

        writeContents(join(POINT_DIR, "master"), master);
        writeContents(join(POINT_DIR, "header"), header);

    }
    public static void add(String name){
        if(!join(SRC_DIR, name).exists()) {
            System.out.print("File does not exist.");
            System.exit(0);
        }
        StageArea.AddFile(name);
    }
}
