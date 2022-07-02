package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

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
        //pickup room
        List<Room> roomList = new ArrayList<>(existRoom);
        List<Boolean> vis = new ArrayList<>();
        double[][] disGragh = new double[roomList.size()][roomList.size()];
        PriorityQueue<Edge> minHeap = new PriorityQueue<Edge>(new Comparator<Edge>() {
            @Override//minHeap？
            public int compare(Edge o1, Edge o2) {
                // TODO Auto-generated method stub
                return Double.compare(o1.getDis(), o2.getDis());
            }
        });
        //prim
//        minHeap.add();
        //yinyongchuandi and zhichuandi
        initializePrim(roomList, vis, disGragh, minHeap);
        for(int i = 0; i < existRoom.size() - 1; i++){
            ToBeLinkedRoom toBeLinkedRoom = Prim(roomList, vis, disGragh, minHeap);
            addHallway(toBeLinkedRoom.getSrc(), toBeLinkedRoom.getDst());
        }

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
    public static double dis(Room.Position p1, Room.Position p2){
        return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }
    public void initializePrim(List<Room> roomList, List<Boolean> vis, double[][] disGragh, PriorityQueue<Edge> minHeap){
        for(int i = 0; i < roomList.size(); i++){
            vis.add(false);
            for(int j = 0; j < roomList.size(); j ++)
                disGragh[i][j] = dis(roomList.get(i).getCenter(), roomList.get(j).getCenter());
        }
    }
    public ToBeLinkedRoom Prim(List<Room> roomList, List<Boolean> vis, double[][] disGragh, PriorityQueue<Edge> minHeap){

    }

    /**
     * assume that psrc is in the left of pdst    but need to cope with the up and down
     * @param src
     * @param dst
     */
    public void addHallway(Room src, Room dst){
        Room.Position psrc = src.selectInnerPos();
        Room.Position pdst = src.selectInnerPos();
        //note edge case
        //first vertical then horizon
        if(RANDOM.nextBoolean()){
            if(pdst.y > psrc.y){
                while(psrc.y++ != pdst.y )
                    addHallwayVerticalSlice(psrc);
            }else {
                while (psrc.y-- != pdst.y)
                    addHallwayVerticalSlice(psrc);
            }
            if(pdst.x != psrc.x)
                addHallwayCorner(psrc);
            while(psrc.x++ != pdst.y) {
                addHallwayHorizontal(psrc);
            }
        }else {
            while(psrc.x++ != pdst.y) {
                addHallwayHorizontal(psrc);
            }
            if(pdst.x != psrc.x)
                addHallwayCorner(psrc);
            if(pdst.y > psrc.y){
                while(psrc.y++ != pdst.y ) {
                    addHallwayVerticalSlice(psrc);
                }
            }else {
                while (psrc.y-- != pdst.y) {
                    addHallwayVerticalSlice(psrc);
                }
            }
        }
    }
}
class Edge{
    private int src;
    private int dst;
    private double dis;
    Edge(int src, int dst, double dis){
        this.src = src;
        this.dst = dst;
        this.dis = dis;
    }
    public Room.Position getSrc(){
        return src;
    }
    public Room.Position getDst(){
        return dst;
    }
    public double getDis(){
        return dis;
    }
}
class ToBeLinkedRoom{
    private Room src;
    private Room dst;
    ToBeLinkedRoom(Room src, Room dst){
        this.src = src;
        this.dst = dst;
    }
    public Room getSrc(){
        return src;
    }
    public Room getDst(){
        return dst;
    }
}