package byow.lab12;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Hexagon {
    private int size;
    private int x;
    private int y;
    private TETile randomShape;
    private TETile[][] HexagonTile;
    private static final Random RANDOM = new Random();

    public Hexagon(int x, int y, int size){
        this.size = size;
        this.x = x;
        this.y = y;
        //size bound
        initialized();
    }

    private void initialized() {
        HexagonTile = new TETile[3 * size - 2][2 * size];
        randomShape = setRandomShape();
        //Can be optimized by recursive
        for (int x = 0; x < 3 * size - 2; x += 1) {
            for (int y = 0; y < 2 * size; y += 1) {
                if ((x + y) < (size - 1) || (x + y) > (4 * size - 3) || (y - x) > size
                        || (x - y) > (2 * size - 2))
                    HexagonTile[x][y] = Tileset.NOTHING;
                else
                    HexagonTile[x][y] = randomShape;
            }
        }
//        x = 3, y = 2
//                size =3
    }
    public TETile setRandomShape() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0:
                return Tileset.TREE;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.GRASS;
            default: return Tileset.NOTHING;
        }
    }
    public TETile[][] getHexagonTile(){
        return HexagonTile;
    }
}
