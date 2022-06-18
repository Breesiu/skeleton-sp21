package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void testThreeAddThreeRemove(){
        LinkedListDeque<Integer> linkedListDeque = new LinkedListDeque<>();
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        linkedListDeque.addLast(4);
        arrayDeque.addLast(4);
        linkedListDeque.addLast(5);
        arrayDeque.addLast(5);
        linkedListDeque.addLast(6);
        arrayDeque.addLast(6);

//        assertEquals(linkedListDeque.size(), arrayDeque.size());
        assertEquals(linkedListDeque.removeLast(), arrayDeque.removeLast());
        assertEquals(linkedListDeque.removeLast(), arrayDeque.removeLast());
        assertEquals(linkedListDeque.removeLast(), arrayDeque.removeLast());
    }

    @Test
    public void randomizedFunctionCalls(){
        LinkedListDeque<Integer> linkedListDeque = new LinkedListDeque<>();
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        int N = 5000000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                linkedListDeque.addLast(randVal);
                arrayDeque.addLast(randVal);
                assertEquals(linkedListDeque.removeLast(), arrayDeque.removeLast());
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
//                int size = linkedListDeque.size();
//                int sizeBuggy = arrayDeque.size();
//                assertEquals(size, sizeBuggy);
//                System.out.println("size: " + size);
            }else if (operationNumber == 2) {
                //getLast
                int randVal = StdRandom.uniform(0, 100);
                linkedListDeque.addFirst(randVal);
                arrayDeque.addFirst(randVal);
                assertEquals(linkedListDeque.removeFirst(),arrayDeque.removeFirst());
//                    System.out.println("getLast(" + linkedListDeque.getLast() + ")");

            } else if (operationNumber == 3) {
                if (linkedListDeque.size() > 0) {
                    assertEquals(linkedListDeque.removeLast(), arrayDeque.removeLast());
//                    System.out.println("removeLast(" + linkedListDeque.removeLast() + ")");
                }
            }else if (operationNumber == 4){
                if(linkedListDeque.size() > 0){
                    assertEquals(linkedListDeque.removeFirst(), arrayDeque.removeFirst());
                }
            }
        }
    }
}
