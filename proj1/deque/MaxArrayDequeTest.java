package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    @Test
    public void ThreeIntegers() {
        MaxArrayDeque<Integer> maxArrayDeque = new MaxArrayDeque<>(new IntComparator());
        maxArrayDeque.addFirst(1);
        maxArrayDeque.addFirst(2);
        maxArrayDeque.addFirst(3);
        assertEquals(maxArrayDeque.max(), (Integer) 3);
    }

    public static class IntComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 > o2)
                return 1;
            else if(o1.equals(o2))
                return 0;
            else
                return -1;

        }
    }
}