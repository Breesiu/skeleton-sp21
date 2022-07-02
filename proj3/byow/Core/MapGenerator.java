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
    private static final Random RANDOM = new Random();
    private Set<Room> existRoom = null;
    private int roomNum;

    //TODO need Gragh to use Prim  LinkedRoomArray UnLinkedRoomArray     public?
    //Can add a width to seperate the adjacent rooms
    MapGenerator(){
        existRoom = new HashSet<>();
        roomNum = RANDOM.nextInt(20, 25);

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
        //TODO the speed of rendering is too low
        for(int i = 0; i < roomNum; i++) {
            Room room = null;
            //TODO maybe will be too crowded!
            //control the the size of room, so it's not be confused in room.draw
            int width = RANDOM.nextInt(4,12);
            int length = RANDOM.nextInt(4,12);
            do {
                room = new Room(RANDOM.nextInt(0,100), RANDOM.nextInt(0, 50), width, length);
            } while (isRoomOverBound(room) || isRoomOverlap(room, tiles) ); //duanlu   this order can't be reversed
            addRoom(room, tiles);
        }
    }
    public void initializeHallways(TETile[][] tiles){

    }
    public boolean isRoomOverlap(Room room, TETile[][] tiles) {
//        //only consider if one room is be overlapped, not each other
//        for(Room eachRoom : existRoom){
//            if(isRoomOverlapHelper(room.getLeftDown(), eachRoom)
//                || isRoomOverlapHelper(new Room.Position(room.getLeftDown().x + room.getWidth() - 1,
//                    room.getLeftDown().y + room.getHeight() - 1), eachRoom)
//                || isRoomOverlapHelper(new Room.Position(room.getLeftDown().x + room.getWidth() - 1,
//                    room.getLeftDown().y ), eachRoom)
//                || isRoomOverlapHelper(new Room.Position(room.getLeftDown().x,
//                    room.getLeftDown().y + room.getHeight() - 1), eachRoom) )
//                return true;
//        }
//        return false;
        for (int x = room.getLeftDown().x; x < room.getLeftDown().x + room.getWidth(); x++) {
            for (int y = room.getLeftDown().y; y < room.getLeftDown().y + room.getHeight(); y++) {
                if (tiles[x][y] != Tileset.NOTHING)
                    return true;
            }
        }
        return false;
    }

    /**
     * If this position is in the room, then return true;
     * @param position
     * @param room
     * @return
     */
    private boolean isRoomOverlapHelper(Room.Position position, Room room){
        if(position.x >= room.getLeftDown().x && position.x < room.getLeftDown().x + room.getWidth()
            && position.y >= room.getLeftDown().y && position.y < room.getLeftDown().y + room.getHeight())
            return true;
        return false;
    }
    public boolean isRoomOverBound(Room room){
        if(0 > room.getLeftDown().x || WIDTH - 1 < room.getLeftDown().x + room.getWidth() - 1
                || 0 > room.getLeftDown().y || HEIGHT - 1 < room.getLeftDown().y + room.getHeight() - 1)
            return true;
        return false;
    }
    public void addRoom(Room room, TETile[][] tiles){
        room.draw(tiles);
        existRoom.add(room);
    }

}
