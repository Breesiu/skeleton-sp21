package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) throws InterruptedException{
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        long seed = Long.parseLong(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) throws InterruptedException{
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String randomString = "";
        for(int i = 0; i < n; i++)
            randomString += CHARACTERS[rand.nextInt(0, CHARACTERS.length)];
        return randomString;
    }

    public void drawFrame(String s){
        //TODO: Take the string and display it in the center of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(width/2, height/2, s);
        StdDraw.show();
        //TODO: If game is not over, display relevant game information at the top of the screen
        if(gameOver == false) {
            font = new Font("Arial", Font.BOLD, 15);
            StdDraw.setFont(font);
            StdDraw.textLeft(0, height - 1, "Round: " + round);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1, "Type!");
            } else {
                StdDraw.text(width / 2, height - 1, "Watch!");
            }
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[rand.nextInt(0, ENCOURAGEMENT.length)]);
            StdDraw.line(0, height - 1.5, width - 1, height - 1.5);
            StdDraw.show();
        }
    }

    public void flashSequence(String letters) throws InterruptedException{
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            Thread.sleep(1000);
            StdDraw.clear(Color.black);
            // To keep the upLine, not be cleared
            drawFrame("");
            StdDraw.show();
            Thread.sleep(500);
        }
    }

    public String solicitNCharsInput(int n) throws InterruptedException {
        //TODO: Read n letters of player input
        String inputString = "";
        for(int i = 0; i < n; i++) {
            while (!StdDraw.hasNextKeyTyped());
            inputString += StdDraw.nextKeyTyped();
            drawFrame(inputString);
        }
        Thread.sleep(500);
        return inputString;
    }

    public void startGame() throws InterruptedException {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        String randomString = null;
//        playerTurn =
        //TODO: Establish Engine loop
        while(true){
            playerTurn = false;
            drawFrame("Round " + round);
            Thread.sleep(1000);
            randomString = generateRandomString(round);
            flashSequence(randomString);
            //How to block my keyboard?
            playerTurn = true;
            if(solicitNCharsInput(round).equals(randomString)){
                round ++;
            }else {
                gameOver = true;
                drawFrame("Game Over! You made it to round: " + round);
                break;
            }
        }
    }

}
