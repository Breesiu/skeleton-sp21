package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

import static byow.Core.Engine.HEIGHT;
import static byow.Core.Engine.WIDTH;

public class Avatar {
    private static final int hpIni = 5;
    private int hp;
    private Position curPos;
    private Random rand;
    Avatar(int seed, TETile[][] world){
        hp = hpIni;
        rand = new Random(seed);
        this.curPos = getRandomFloorPos(world);
        world[curPos.x][curPos.y] = Tileset.AVATAR;
        System.out.println("sd");
    }
    public void toUp(TETile[][] world){
        Position nextPos = new Position(curPos.x, curPos.y + 1);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.AVATAR;

    }
    public void toDown(TETile[][] world){
        Position nextPos = new Position(curPos.x, curPos.y - 1);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.AVATAR;

    }
    public void toLeft(TETile[][] world){
        Position nextPos = new Position(curPos.x - 1, curPos.y);
        if(isCollidedWithWall(nextPos, world))
            return;
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.AVATAR;

    }
    public void toRight(TETile[][] world){
        Position nextPos = new Position(curPos.x + 1, curPos.y);
        if(isCollidedWithWall(nextPos, world))
            return;
        if(getExit(nextPos, world)){
            world[curPos.x][curPos.y] = Tileset.FLOOR;
            curPos = nextPos;
            return;
        }
        world[curPos.x][curPos.y] = Tileset.FLOOR;
        curPos = nextPos;
        world[curPos.x][curPos.y] = Tileset.AVATAR;

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
        }while (world[randomFloorPos.x][randomFloorPos.y] != Tileset.FLOOR);
        return randomFloorPos;
    }
    public boolean getExit(Position nextPos, TETile[][] world){
        if(world[nextPos.x][nextPos.y] == Tileset.UNLOCKED_DOOR)
            return true;
        return false;
    }
}
