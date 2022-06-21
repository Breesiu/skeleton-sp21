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

                break;
            case "log":

                break;
            case "global-log":

                break;
            case "find":

                break;
            case "status":

                break;
            case "checkout":

                break;
            case "branch":

                break;
            case "rm-branch":

                break;
            case "reset":

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
