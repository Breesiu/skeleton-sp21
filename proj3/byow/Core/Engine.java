package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 50;
    private Random rand;
    private int seed;
    private boolean gameOver;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        seed = 0;
        rand = new Random(seed);
        gameOver = false;
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.randomGenerate(world, ter);
        ter.renderFrame(world);
        KeyboardInputSource keyboardInputSource = new KeyboardInputSource();
        char c;
        Avatar avatar = new Avatar(seed, world);
//        ter.renderFrame(world);
        // the Pos of chaser is not diministic?
        Chaser chaser = new Chaser(seed, world);
//        chaser.run();
        do {
//            c = keyboardInputSource.getNextKey();
            // through this way, IO is not be blocked
            if(StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                c = Character.toUpperCase(c);
                System.out.println("s");
                switch (c) {
                    case 'W':
//                    System.out.println("w");
//                    if(avatar.getExit())
                        avatar.toUp(world);
                        break;
                    case 'S':
                        avatar.toDown(world);
                        break;
                    case 'A':
                        avatar.toLeft(world);
                        break;
                    case 'D':
                        avatar.toRight(world);
                        break;
                    default:
                        break;

                }
                System.out.println("s");

                chaser.chaseAvatarA_Star(avatar.getCurPos(), world);
                //object equals
                if(chaser.getCurPos().equals(avatar.getCurPos())){
                    gameOver = true;
                }
                System.out.println("s");

                if (world[avatar.getCurPos().x][avatar.getCurPos().y] == Tileset.UNLOCKED_DOOR)
                    gameOver = true;
            }
//            chaser.chaseAvatarA_Star(avatar.getCurPos(), world);
            ter.renderFrame(world);
        } while (!gameOver);
//        StdDraw.clear();
        ter.initialize(WIDTH, HEIGHT);
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
