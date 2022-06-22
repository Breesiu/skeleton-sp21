package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.Utils.*;

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

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File STAGE_DIR = join(GITLET_DIR, "stage");
    public static final File SRC_DIR = join(CWD, "gitlet");
    public static final File COMMIT_DIR = join(GITLET_DIR, "commit");
    public static final File BLOB_DIR = join(GITLET_DIR, "blob");
    public static final File POINT_DIR = join(GITLET_DIR, "pointer");

    /* TODO: fill in the rest of this class. */
//    private StageArea stageArea = new StageArea();

    public static void init() {
        if (GITLET_DIR.exists()) {
            System.out.print("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        STAGE_DIR.mkdir();
        COMMIT_DIR.mkdir();
        BLOB_DIR.mkdir();
        POINT_DIR.mkdir();
        Commit initialCommit = new Commit("initial commit", new Date(0), "master");
        initialCommit.setHashCode();
        String master = initialCommit.getHashCode();
        Header header = new Header(initialCommit.getHashCode(), "master");
        header.save();
        initialCommit.save();
        StageArea stageArea = new StageArea();
        stageArea.save();
        writeContents(join(POINT_DIR, "master"), master);

    }

    //Need to devise the framework of add
    public static void add(String fileName) {
        if (!join(CWD, fileName).exists()) {
            System.out.print("File does not exist.");
            System.exit(0);
        }
        StageArea stageArea = StageArea.FromFile();
        stageArea.AddFile(fileName);
        stageArea.save();
    }

    public static void commit(String message) {
        StageArea stageArea = StageArea.FromFile();
        Header header = Header.fromFile();
        if (stageArea.isEmpty()) {
            System.out.print("No changes added to the commit.");
            System.exit(0);
        }
//        Commit preCommit = if I can pack these things to Class Commit?
        Commit preCommit = Commit.fromFile(Header.fromFile().getHashCode());
        Commit commit = new Commit(message, new Date(), header.getBranch());
        commit.addParent(preCommit.getHashCode());
        commit.inheritBlob(preCommit);
        commit.updateCommitWithStagedArea(stageArea);
        commit.setHashCode();
        //Consider the hashCode of Commit will change，so the change of Header is after stageArea.cleanStaged
        commit.save();
        //devise pointers
        header.setHashCode(commit.getHashCode());
        writeContents(join(POINT_DIR, header.getBranch()), commit.getHashCode());
        header.save();

        stageArea.cleanStaged();
        stageArea.save();
    }

    /**
     * Unstage the file if it is currently staged for addition. If the file is tracked in the current commit, stage
     * it for removal and remove the file from the working directory if the user has not already done so (do not
     * remove it unless it is tracked in the current commit).
     */
    //if the file both in the staged for addition and tracked in the current commit?
    public static void rm(String fileName) {
        StageArea stageArea = StageArea.FromFile();
        Commit commit = Commit.fromFile(Header.fromFile().getHashCode());
        if (!stageArea.ExistInStagedForAddition(fileName) && !commit.existBlob(fileName)) {
            System.out.print("No reason to remove the file.");
            System.exit(0);
        }
        if (stageArea.ExistInStagedForAddition(fileName)) {
            stageArea.unstageForAddition(fileName);
            stageArea.save();
            return;
        }
        // if the file is tracked in the current commit
        stageArea.stageStagedForRemoval(fileName);
        //delete  can alternatively use the restrictedDelete in gitlet.Utils
        File rmfile = join(CWD, fileName);
        if (rmfile.exists())
            rmfile.delete();
        stageArea.save();
    }
    //don't need save()
    public static void log() {
        Header header = Header.fromFile();
        Commit commit = Commit.fromFile(header.getHashCode());
        commit.print();
        do {
            commit = Commit.fromFile(commit.getParent().get(0));
            commit.print();
        } while(commit.getParent().size() != 0);
    }

    //don't need save()
    public static void global_log() {
        List<String> ComitHashCodeList = plainFilenamesIn(COMMIT_DIR);
        ArrayList<Commit> CommitList = new ArrayList<>();
        for(String hashCode: ComitHashCodeList){
            CommitList.add(Commit.fromFile(hashCode));
        }
        for(Commit commit : CommitList){
            commit.print();
        }
    }
    public static void find(String message){
        List<String> ComitHashCodeList = plainFilenamesIn(COMMIT_DIR);
        ArrayList<Commit> CommitList = new ArrayList<>();
        for(String hashCode: ComitHashCodeList){
            CommitList.add(Commit.fromFile(hashCode));
        }
        for(Commit commit : CommitList){
            commit.printFind(message);
        }
    }
    public static void status(){
        StageArea stageArea = StageArea.FromFile();
        Header header = Header.fromFile();
        Commit commit = Commit.fromFile(header.getHashCode());
        TreeMap<String, String > stagedForAddition = stageArea.getStagedForAddition();
        TreeSet<String> stagedForRemoval = stageArea.getStagedForRemoval();
        TreeMap<String, String> blobs = commit.getBlobs();
        //print Branch
        List<String> BranchList = plainFilenamesIn(POINT_DIR);
        BranchList.remove("header");

        Collections.sort(BranchList);
        System.out.println("=== Branches ===");
        for(String branch : BranchList){
            if(branch.equals(header.getBranch()))
                System.out.print('*');
            System.out.println(branch);
        }
        System.out.println();

        //print Staged Files
        System.out.println("=== Staged Files ===");
        for(Iterator<String > it = stagedForAddition.keySet().iterator(); it.hasNext();){
            System.out.println(it.next());
        }
        System.out.println();

        //print Removed Files
        System.out.println("=== Removed Files ===");
        for(Iterator<String > it = stagedForRemoval.iterator(); it.hasNext();){
            System.out.println(it.next());

        }
        System.out.println();

        /**
         * The last two sections (modifications not staged and untracked files) are extra credit, worth 32 points.
         * Feel free to leave them blank (leaving just the headers).
         */
        //Modifications Not Staged For Commit
        System.out.println("=== Modifications Not Staged For Commit ===");
        TreeSet<String> ModificationsNotStagedForCommit = new TreeSet<>();
        //case1
        for(Map.Entry<String,String> entry : blobs.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //sha1(readContent) can be packed to a function?
            if(join(CWD, key).exists() && !value.equals(sha1(readContents(join(CWD, key))))
                && !stagedForAddition.containsKey(key))
                ModificationsNotStagedForCommit.add(key + " (modified)");
        }
        //case2
        for(Map.Entry<String,String> entry : stagedForAddition.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(!value.equals(sha1(readContents(join(CWD, key)))))
                ModificationsNotStagedForCommit.add(key + " (modified)");
        }
        //case3
        for(Map.Entry<String,String> entry : stagedForAddition.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!join(CWD, key).exists())
                ModificationsNotStagedForCommit.add(key + " (deleted)");
        }
        //case4
        for(Map.Entry<String,String> entry : blobs.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!stagedForRemoval.contains(key) && !join(CWD, key).exists())
                ModificationsNotStagedForCommit.add(key + " (deleted)");
        }
        //ascended sorting?
        for(String item : ModificationsNotStagedForCommit){
            System.out.println(item);
        }
        System.out.println();

        /**
         *Untracked Files  "This includes files that have been staged for removal,
         *  but then re-created without Gitlet’s knowledge"  collides with operation of rm?
         *  CWD filter?
         */
        System.out.println("=== Untracked Files ===");
        List<String> cwdFileList = plainFilenamesIn(CWD);
        TreeSet<String> untrackedFiles = new TreeSet<>();
        for(String fileName : cwdFileList){
            //case1
            if(!stagedForAddition.containsKey(fileName) && !blobs.containsKey(fileName))
                untrackedFiles.add(fileName);
            //case2
            if(stagedForRemoval.contains(fileName))
                untrackedFiles.add(fileName);
        }
        for(String item : untrackedFiles){
            System.out.println(item);
        }
        System.out.println();

    }

}