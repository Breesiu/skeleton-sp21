package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length == 0) {
            System.out.print("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                validateNum(args.length, 1);
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                validateNum(args.length, 2);
                Repository.add(args[1]);
                break;
            // TODO: FILL THE REST IN
            case "commit":
                if(args.length == 1){
                    System.out.print("Please enter a commit message.");
                    System.exit(0);
                }
                validateNum(args.length, 2);
                Repository.commit(args[1]);
                break;
            case "rm":
                validateNum(args.length, 2);
                Repository.rm(args[1]);
                break;
            case "log":
                validateNum(args.length, 1);
                Repository.log();
                break;
            case "global-log":
                validateNum(args.length, 1);
                Repository.global_log();
                break;
            case "find":
                validateNum(args.length, 2);
                Repository.find(args[1]);
                break;
            case "status":
                validateNum(args.length, 1);
                Repository.status();
                break;
            case "checkout":
                //need to match the sign of "--"
                if(args.length == 2)
                    Repository.checkoutBranch(args[1]);
                else if(args.length == 3)
                    Repository.checkoutFilename(args[2]);
                else if(args.length ==4)
                    Repository.checkoutCommitidFilename(args[1], args[3]);
                break;
            case "branch":
                validateNum(args.length, 2);
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                validateNum(args.length, 2);
                Repository.rm_branch(args[1]);
                break;
            case "reset":
                validateNum(args.length, 2);
                Repository.reset(args[1]);
                break;
            case "merge":

                break;

            default:
                System.out.print("No command with that name exists.");
                System.exit(0);
        }
    }
    public static void validateNum(int Num, int expectNum){
        if(Num != expectNum){
            System.out.print("Incorrect operands.");
            System.exit(0);
        }
    }
}
