package flik;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilkTest {

    @Test
    public void Test128ab(){
        int a = 128;
        int b = 128;
        assertEquals(a, b);
    }
    @Test
    public void TestFor_iterative_ab(){
        for(int i = 0, j = 0; i < 500 && j < 500; i++, j++)
            assertEquals(i, j);
    }
    @Test
    public void TestFor_iterative_filk(){
//        Filk filk = new Filk();
        for(Integer i = 0, j = 0; i < 129 && j < 129; i++, j++)
            assertTrue("i = " + i +" with "+  "j = " + j, Flik.isSameNumber(i, j));
    }
    @Test
    public void TestFor_iterative_filk2(){
    int i = 128;
    int j = 128;
    assertTrue(i == j);
    }

}
