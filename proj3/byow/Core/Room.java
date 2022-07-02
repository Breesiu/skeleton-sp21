package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;


public class Room {
    //contain the length of the wall
    private int width;
    private int height;
    private Position leftDown = null;
    // used to Prim
    private Position center = null;
    private static final Random RANDOM = new Random();

    //TODO static?
    public static class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Room(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        leftDown = new Position(x, y);
        center = new Position(x + width / 2, y + height / 2);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Position getLeftDown() {
        return leftDown;
    }

    public Position getCenter() {
        return center;
    }
    public Position RandomSelectInnerPos(){
        Position position = new Position(leftDown.x + RANDOM.nextInt(1,width - 1),
                                            leftDown.y + RANDOM.nextInt(1, height - 1));
        return position;
    }
    //when need to devise rooms of different shapes, then should hava a drawhelper to draw one line and shift
    public void draw(TETile[][] tiles) {
        //TODO drawline function
//        for(int x = leftDown.x; x < leftDown.x + width - 1; x++){}
//        for(int y = leftDown.y + 1; y < leftDown.y + height - 1; y++){
//            for(int x = leftDown.x + 1; x < leftDown.x + width - 1; x++){
//
//            }
//        }int x = leftDown.x; x < leftDown.x + width; x++
        for (int x = leftDown.x; x < leftDown.x + width; x++) {
            for (int y = leftDown.y; y < leftDown.y + height; y++) {
                if(y > leftDown.y && y < leftDown.y + height - 1
                    && x > leftDown.x && x < leftDown.x + width - 1)
                    tiles[x][y] = Tileset.FLOOR;
                else
                    tiles[x][y] = Tileset.WALL;
            }
        }

    }
}