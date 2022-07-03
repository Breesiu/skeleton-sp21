package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class MainDemo {


//    private static final long SEED = 2873123;
//    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        //put tilesNew in the MapGenerator?
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        MapGenerator mapGenerator = new MapGenerator();
        mapGenerator.randomGenerate(world,ter);

        // draws the world to the screen
        ter.renderFrame(world);
    }


}
