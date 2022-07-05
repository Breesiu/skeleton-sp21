package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;
import static java.lang.Math.abs;

public class Chaser{
    private static final int hpIni = 5;
    private int hp;
    private Position curPos;
    private Random rand;
    Chaser(int seed, TETile[][] world){
        hp = hpIni;
        rand = new Random(seed);
        do{
            this.curPos = getRandomFloorPos(world);
        }while (world[curPos.x][curPos.y] == Tileset.AVATAR);
        world[curPos.x][curPos.y] = Tileset.CHASER;
        System.out.println("sd");
    }
    public void toUp(TETile[][] world){
        Position nextPos = new Position(curPos.x, curPos.y + 1);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.CHASER;

    }
    public void toDown(TETile[][] world){
        Position nextPos = new Position(curPos.x, curPos.y - 1);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.CHASER;

    }
    public void toLeft(TETile[][] world){
        Position nextPos = new Position(curPos.x - 1, curPos.y);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.CHASER;

    }
    public void toRight(TETile[][] world){
        Position nextPos = new Position(curPos.x + 1, curPos.y);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.CHASER;

    }
    public boolean isCollidedWithWall(Position nextPos, TETile[][] world){
        if(world[nextPos.x][nextPos.y] == Tileset.WALL)
            return true;
        return false;
    }

    public Position getCurPos() {
        return curPos;
    }
    public int getHp(){
        return hp;
    }
    public Position getRandomFloorPos(TETile[][] world){
        Position randomFloorPos = new Position();
        do{
            randomFloorPos.x = rand.nextInt(0, WIDTH);
            randomFloorPos.y = rand.nextInt(0, HEIGHT);
        }while (world[randomFloorPos.x][randomFloorPos.y] != Tileset.FLOOR && world[randomFloorPos.x][randomFloorPos.y] != Tileset.AVATAR);
        return randomFloorPos;
    }
    public void chaseAvatarA_Star(Position avatarPos, TETile[][] world) {
        int[] direction = new int[]{-1, 0, 1, 0, -1};
        A_StarGraghNode[][] Gragh = new A_StarGraghNode[WIDTH][HEIGHT];
//        List<A_StarGraghNode> closeList = new ArrayList<>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                Gragh[x][y] = new A_StarGraghNode();
                Gragh[x][y].setPosition(new Position(x, y));
            }
        }
        PriorityQueue<A_StarGraghNode> openList = new PriorityQueue<>(new Comparator<A_StarGraghNode>() {
            @Override//minHeap？
            public int compare(A_StarGraghNode o1, A_StarGraghNode o2) {
                return Double.compare(o1.getF(), o2.getF());
            }
        });
        A_StarGraghNode a_starGraghNode = null;
        //it can save the time
        Gragh[curPos.x][curPos.y].setVisit(true);
        openList.add(Gragh[curPos.x][curPos.y]);
        if(curPos.equals(avatarPos))
            return;
        look: while (!openList.isEmpty()) {
//            System.out.println(openList.size());
            a_starGraghNode = openList.remove();
            if(a_starGraghNode.getIsInCloseList())
                continue;
//            a_starGraghNode.setIsInCloseList(true);
            Position curPos = new Position(a_starGraghNode.getPosition().x, a_starGraghNode.getPosition().y);
            Gragh[curPos.x][curPos.y].setIsInCloseList(true);
            for (int i = 0; i < 4; i++) {
                Position nextPos = new Position(curPos.x + direction[i], curPos.y + direction[i + 1]);
                //if catch
                if (world[nextPos.x][nextPos.y] == Tileset.AVATAR) {
                    Gragh[nextPos.x][nextPos.y].setFather(Gragh[curPos.x][curPos.y]);
                    break look;
                }
                if (world[nextPos.x][nextPos.y] != Tileset.WALL) {
                    if (Gragh[nextPos.x][nextPos.y].getVisit() == false) {
                        Gragh[nextPos.x][nextPos.y].setVisit(true);
                        Gragh[nextPos.x][nextPos.y].setFather(Gragh[curPos.x][curPos.y]);
                        Gragh[nextPos.x][nextPos.y].setG(Gragh[curPos.x][curPos.y].getG() + 1);
                        Gragh[nextPos.x][nextPos.y].setF(computeH(nextPos, avatarPos));
                        Gragh[nextPos.x][nextPos.y].setF(Gragh[nextPos.x][nextPos.y].getF() + Gragh[nextPos.x][nextPos.y].getH());
                        openList.add(Gragh[nextPos.x][nextPos.y]);
                        //note priorityQueue
                    } else {
                        if(Gragh[curPos.x][curPos.y].getG() + 1 + computeH(nextPos, avatarPos) < Gragh[nextPos.x][nextPos.y].getF()){
                            Gragh[nextPos.x][nextPos.y].setFather(Gragh[curPos.x][curPos.y]);
                            Gragh[nextPos.x][nextPos.y].setG(Gragh[curPos.x][curPos.y].getG() + 1);
                            Gragh[nextPos.x][nextPos.y].setF(computeH(nextPos, avatarPos));
                            Gragh[nextPos.x][nextPos.y].setF(Gragh[nextPos.x][nextPos.y].getF() + Gragh[nextPos.x][nextPos.y].getH());
                            openList.add(Gragh[nextPos.x][nextPos.y]);
                        }
                    }
                }
            }
        }
//        System.out.println(curPos.x);
        a_starGraghNode = Gragh[avatarPos.x][avatarPos.y];
//        while(a_starGraghNode.getFather() != Gragh[curPos.x][curPos.y])
        while(a_starGraghNode.getFather().getPosition().x != curPos.x || a_starGraghNode.getFather().getPosition().y != curPos.y) {
            a_starGraghNode = a_starGraghNode.getFather();
//            System.out.println(curPos.x + curPos.y);
//            System.out.println(a_starGraghNode.getPosition().x + "  " + a_starGraghNode.getPosition().y);
        }
        Position nextPos = new Position(a_starGraghNode.getPosition().x, a_starGraghNode.getPosition().y);
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        world[nextPos.x][nextPos.y] = Tileset.CHASER;
        curPos = nextPos;

//    @Override
//    public void run(){
//        while (true){
//            chaseAvatarA_Star(avatar.Pos);
//        }
//    }
//    public void chaseAvatarA_Star(){
//
//    }
    }
    public int computeH(Position nextPos, Position avatarPos){
        return abs(nextPos.x - avatarPos.x) + abs(nextPos.y - avatarPos.y);
    }
}

class A_StarGraghNode{
    private boolean visit;
    private boolean isInCloseList;
    private A_StarGraghNode father;
    private Position position;
    private int F; //sum
    private int G; // has
    private int H;   // will
    A_StarGraghNode(){
//        visit = false;
        isInCloseList = false;
        father = null;
        F = 0;
        G = 0;
        H = 0;
    }
    A_StarGraghNode(A_StarGraghNode father, int G, int H){
        visit = true;
        isInCloseList = false;
        this.father = father;
        this.G = G;
        this.H = H;
    }
    public A_StarGraghNode getFather(){
        return father;
    }
    public boolean getVisit(){
        return visit;
    }
    public boolean getIsInCloseList(){
        return isInCloseList;
    }
    public int getF() {
        return F;
    }

    public int getG() {
        return G;
    }

    public int getH() {
        return H;
    }
    public Position getPosition(){
        return position;
    }
    public void setFather(A_StarGraghNode father){
        this.father = father;
    }
    public void setVisit(boolean visit){
        this.visit = visit;
    }
    public void setF(int f) {
        F = f;
    }
    public void setG(int g){
        G = g;
    }

    public void setH(int h) {
        H = h;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    public void setIsInCloseList(boolean isInCloseList){
        this.isInCloseList = isInCloseList;
    }
}