package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private DoubleNode sentinel;


    public class DoubleNode {
        public T item;
        public DoubleNode prev;
        public DoubleNode next;

        public DoubleNode(T item) {
            this.item = item;
            prev = null;
            next = null;
        }

        public DoubleNode() {
        }
    }

    @Override
    public void addFirst(T item) {
        if (size == 0) {
            sentinel.next = new DoubleNode(item);
            sentinel.prev = sentinel.next;
            sentinel.next.prev = sentinel;
            sentinel.next.next = sentinel;
        } else {
            DoubleNode doubleNode = new DoubleNode(item);
            sentinel.next.prev = doubleNode;
            doubleNode.next = sentinel.next;
            sentinel.next = doubleNode;
            doubleNode.prev = sentinel;
        }
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == 0) {
            DoubleNode doubleNode = new DoubleNode(item);
            sentinel.next = doubleNode;
            sentinel.prev = doubleNode;
            doubleNode.next = sentinel;
            doubleNode.prev = sentinel;
        } else {
            DoubleNode doubleNode = new DoubleNode(item);
            doubleNode.next = sentinel;
            sentinel.prev.next = doubleNode;
            doubleNode.prev = sentinel.prev;
            sentinel.prev = doubleNode;
        }
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int tmp = size();
        DoubleNode doubleNode = sentinel.next;
        while ((tmp-- != 0)) {
            System.out.print(doubleNode.item);
            System.out.print(' ');
            doubleNode = doubleNode.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size() == 0)
            return null;
        DoubleNode doubleNode = new DoubleNode(sentinel.next.item);
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return doubleNode.item;

    }

    @Override
    public T removeLast() {
        if (size() == 0)
            return null;
        DoubleNode doubleNode = new DoubleNode(sentinel.prev.item);
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return doubleNode.item;
    }

    @Override
    public T get(int index) {
        if (index >= size() || index < 0)
            return null;
        DoubleNode doubleNode = sentinel.next;
        while (index-- != 0) {
            doubleNode = doubleNode.next;
        }
        return doubleNode.item;
    }

    public LinkedListDeque() {
        size = 0;
        sentinel = new DoubleNode();
//        DoubleNode doubleNode = new DoubleNode();
//        doubleNode.next = doubleNode;
//        doubleNode.prev = doubleNode;
        sentinel.next = sentinel;
        sentinel.prev = sentinel;

    }

    public T getRecursive(int index) {
        if (index >= size() || index < 0)
            return null;
        if (index == 0) return sentinel.next.item;
        return getRecursive(index - 1);
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private DoubleNode wizPos;

        public LinkedListDequeIterator() {
            wizPos = sentinel.next;
        }
        public boolean hasNext() {
            return wizPos != sentinel;
        }
        public T next() {
            T returnItem = wizPos.item;
            wizPos = wizPos.next;
            return returnItem;
        }

    }

    //Iterator
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof LinkedListDeque))
            return false;
        LinkedListDeque<T> other = (LinkedListDeque<T>) o;
        if(other.size() != size()) return false;
 //        if(int i = 0; i < )
        for(int i = 0; i < size(); i++) {
            if(other.get(i) != get(i))
                return false;
        }
        return true;
    }
}
