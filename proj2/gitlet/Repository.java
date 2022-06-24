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
        //print Branch          has error
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
        TreeSet<String> untrackedFiles = stageArea.getUntrackedFiles(blobs);
        for(String item : untrackedFiles){
            System.out.println(item);
        }
        System.out.println();
    }
    public static void checkoutFilename(String fileName){
        //Header header = Header.fromFile();
        //if the fileName has been staged to the stagedForAddition?
        Commit commit = Commit.fromFile(readContentsAsString(join(POINT_DIR, "header")));
        if(!commit.existBlob(fileName)){
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        //Todo In this way,  the content of this file in CWD is byte[] , need to devise it to String
        writeContents(join(CWD, fileName),
                Blob.fromFile(commit.getBlobs().get(fileName)).getItem());


    }

    /**
     * Unfortunately, using shortened ids might slow down the finding of objects if implemented naively (making the time
     * to find a file linear in the number of objects), so we won’t worry about timing for commands that use shortened
     * ids. We suggest, however, that you poke around in a .git directory (specifically, .git/objects) and see how it
     * manages to speed up its search. You will perhaps recognize a familiar data structure implemented with the file
     * system rather than pointers.
     * How can I use Constant time to search the specific commit?   use Hash?
     * @param commitId
     * @param fileName
     */
    public static void checkoutCommitidFilename(String commitId, String fileName){
        Commit commit;
        if((commit = Commit.getCommit(commitId)) == null) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        if(!commit.existBlob(fileName)){
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        writeContents(join(CWD, fileName),
                Blob.fromFile(commit.getBlobs().get(fileName)).getItem());

    }
    public static void checkoutBranch(String branch){
        List<String> BranchList = plainFilenamesIn(POINT_DIR);
        BranchList.remove("header");
        Header header = Header.fromFile();
        if(BranchList.indexOf(branch) == -1){
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if(header.getBranch() == branch){
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
        StageArea stageArea = StageArea.FromFile();
        TreeSet<String> untrackedFiles = null;
        if((untrackedFiles = stageArea.getUntrackedFiles(Commit.fromFile(header.getHashCode()).getBlobs())) == null){
            System.out.println("There is an untracked file in the way; " +
                    "delete it, or add and commit it first.");
            System.exit(0);
        }
        //if need to delete the file that are in the previous branch but not in the new branch
        stageArea.cleanStaged();
        header.setHashCode(readContentsAsString(join(POINT_DIR, branch)));
        header.setBranch(branch);
        Commit commit = Commit.fromFile(header.getHashCode());
        for(Map.Entry<String,String> entry : commit.getBlobs().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            //need to convert byte[] to String
            writeContents(join(CWD, key), readContents(join(BLOB_DIR, value)));
        }
        stageArea.save();
        header.save();;
    }

    /**
     * Make sure that the behavior of your branch, checkout, and commit match what we’ve described above.
     * This is pretty core functionality of Gitlet that many other commands will depend upon. If any of this core
     * functionality is broken, very many of our autograder tests won’t work!
     * ??Before you ever call branch, your code should be running with a default branch called “master”.
     * @param branch
     */
    public static void branch(String branch){
        List<String> BranchList = plainFilenamesIn(POINT_DIR);
        if(BranchList.contains(branch)){
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        writeContents(join(POINT_DIR, branch), Header.fromFile().getHashCode());
    }

}