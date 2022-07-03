package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;

public class Hallway {

    public static void addHallwayVerticalSlice(Room.Position position, TETile[][] tiles){
//        if(tiles[position.x][position.y] == Tileset.FLOOR);
//        else if(tiles[position.x][position.y] == Tileset.WALL){
//            tiles[position.x][position.y] = Tileset.FLOOR;
//        }else if(tiles[position.x][position.y] == Tileset.NOTHING) {
            tiles[position.x][position.y] = Tileset.FLOOR;
//            tiles[position.x + 1][position.y] = Tileset.WALL;
//            tiles[position.x - 1][position.y] = Tileset.WALL;
//        }
    }
    public static void addHallwayHorizontalSlice(Room.Position position, TETile[][] tiles){
//        if(tiles[position.x][position.y] == Tileset.FLOOR);
//        else if(tiles[position.x][position.y] == Tileset.WALL){
//            tiles[position.x][position.y] = Tileset.FLOOR;
//        }else if(tiles[position.x][position.y] == Tileset.NOTHING) {
        tiles[position.x][position.y] = Tileset.FLOOR;
//            tiles[position.x][position.y + 1] = Tileset.WALL;
//            tiles[position.x][position.y - 1] = Tileset.WALL;
//        }
    }
    public static void addHallwayCorner(Room.Position position, TETile[][] tiles){
//        for(int dx = -1; dx <= 1; dx++){
//            for(int dy = -1; dy <= 1; dy++)
//                if(dx == 0 && dy == 0)
        tiles[position.x][position.y] = Tileset.FLOOR;
//                else if(tiles[position.x + dx][position.y + dy] == Tileset.NOTHING)
//                    tiles[position.x + dx][position.y + dy] = Tileset.WALL;
//        }
    }
    public static void fillCorner(TETile[][] tiles){
        for(int x = 1; x < WIDTH - 1; x++){
            for(int y = 1; y < HEIGHT - 1; y++){
                if(tiles[x][y] == Tileset.FLOOR){
                    for(int dx = -1; dx <= 1; dx++){
                        for(int dy = -1; dy <= 1; dy++){
                            if(tiles[x+dx][y+dy] == Tileset.NOTHING)
                                tiles[x+dx][y+dy] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }
}
