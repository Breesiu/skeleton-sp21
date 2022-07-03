package byow.Core;

import org.junit.Test;

import static byow.Core.MapGenerator.dis;

public class PrimTest {
    @Test
    public void disTest(){
        Position p1 = new Position(1,1);
        Position p2 = new Position(2,2);
        System.out.println(dis(p1, p2));
    }


}
