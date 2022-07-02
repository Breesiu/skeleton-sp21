package byow.Core;

import org.junit.Test;

import static byow.Core.MapGenerator.dis;

public class PrimTest {
    @Test
    public void disTest(){
        Room.Position p1 = new Room.Position(1,1);
        Room.Position p2 = new Room.Position(2,2);
        System.out.println(dis(p1, p2));
    }


}
