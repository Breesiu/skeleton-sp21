package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;

public class MapGenerator {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    private Set<Room> existRoom = null;
    private int roomNum;

    //TODO need Gragh to use Prim  LinkedRoomArray UnLinkedRoomArray
    public MapGenerator(){
        existRoom = new HashSet<>();
        roomNum = RANDOM.nextInt(10, 15);

    }
    public void initializeNothing(TETile[][] tiles){
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }
    public void randomGenerate(TETile[][] tiles){
        initializeNothing(tiles);
        initializeRooms(tiles);
        initializeHallways(tiles);

    }
    public void initializeRooms(TETile[][] tiles){
        for(int i = 0; i < roomNum; i++) {
            Room room = null;
            do {
                room = new Room();
            } while (isRoomOverlap(room));
            addRoom(room);
        }
    }
    public void initializeHallways(TETile[][] tiles){

    }
    public boolean isRoomOverlap(Room room){

    }
    public void addRoom(Room room){

    }

}
