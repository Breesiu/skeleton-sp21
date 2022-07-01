package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    public static void fillWithNotingTiles(TETile[][] tiles) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void addHexagon(TETile[][] tiles, int x, int y, int size) {
        //TODO if exceed the right, up bound of tiles
//        if()

        Hexagon hexagon = new Hexagon(x, y, size);
        for (int i = 0; i < 3 * size - 2; i += 1) {
            for (int j = 0; j < 2 * size; j += 1) {
                tiles[x + i][y + j] = hexagon.getHexagonTile()[i][j];
            }
        }
    }
        public static void main (String[]args){
            // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
            TERenderer ter = new TERenderer();
            ter.initialize(WIDTH, HEIGHT);

            // initialize tiles
            TETile[][] hexWorldTiles = new TETile[WIDTH][HEIGHT];
            fillWithNotingTiles(hexWorldTiles);
            //Test it
            addHexagon(hexWorldTiles, 14,16, 4);

            // draws the world to the screen
            ter.renderFrame(hexWorldTiles);
        }


}
