package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    //枚举类？
    private void resize(int size) {
        T[] tmp = (T[]) new Object[size];
        if (nextLast < nextFirst) {
            System.arraycopy(items, nextFirst + 1, tmp, 0,  items.length - 1 - nextFirst);
            System.arraycopy(items, 0, tmp, items.length - 1 - nextFirst, nextLast - 1);
            nextLast = (items.length - 1 - nextFirst) + (nextLast - 1);
        } else {
            System.arraycopy(items, nextFirst + 1, tmp, 0, size());
        }
        items = tmp;
        nextFirst = size - 1;
        this.size = size;
    }
    private boolean needExpand(){
        if(nextFirst == nextLast)
            return true;
        return false;
    }
    private boolean needContract(){
        if(items.length <= 16) return false;
        if(size() <= items.length/4) return true;
        return false;
    }
    @Override
    public void addFirst(T item) {
        if(needExpand()){
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        if(nextFirst == 0)
            nextFirst = items.length - 1;
        else
            nextFirst -= 1;

    }

    @Override
    public void addLast(T item) {
        if(needExpand()){
            resize(items.length * 2);
        }
        items[nextLast] = item;

        nextLast = (nextLast + 1) % items.length;
    }

    @Override
    //How to just use one with linkedlist
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for(int i = (nextFirst + 1) % items.length; i != nextLast; i = (i+1) % items.length) {
            System.out.print(items[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if(isEmpty()) return null;
        if(needContract()){
            resize(size() / 2);
        }
        T tmp;
        if(nextFirst == (size() - 1)) {
            tmp = items[0];
            items[0] = null;
            nextFirst = 0;
        }
        else {
            tmp = items[nextFirst + 1];
            items[nextFirst + 1] =null;
            nextFirst++;
        }
        return tmp;
    }

    @Override
    public T removeLast() {
        if(isEmpty()) return null;
        if(needContract()){
            resize(items.length / 2);
        }
        T tmp;
        if(nextLast == 0) {
            tmp = items[items.length - 1];
            items[items.length - 1] = null;
            nextLast = items.length - 1;
        }
        else {
            tmp = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast--;
        }
        return tmp;
    }

    @Override
    //最多size() - 1 个元素
    public T get(int index) {
        if(index < 0 || index > items.length) return null;
        T tmp = null;
        if(nextLast < nextFirst) {
            if ((nextFirst + 1 + index) % size() < nextLast || (nextFirst + 1 + index) % size() > nextFirst)
                tmp = items[(nextFirst + 1 + index) % size()];
        }else{
            if (nextFirst + 1 + index < nextLast)
                tmp = items[nextFirst + 1 + index];
        }
        tmp = items[]
        return tmp;

    }
    public ArrayDeque(){
        nextFirst = 0;
        nextLast = 1;
        size = 8;
        items = (T[])new Object[size];
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private  int wizPos;
        public ArrayDequeIterator() {
            wizPos = (nextFirst + 1) % items.length;
        }
        public boolean hasNext() {
            return wizPos < nextLast;
        }
        public T next() {
            T returnItem = items[wizPos];
            wizPos = (wizPos + 1) % items.length;
            return returnItem;
        }
    }
    public Iterator<T> iterator(){
        return new ArrayDequeIterator();
    }
    //迭代器iterator
    public boolean equals(Object o){
        if(o == null) return false;
        if(this == o) return true;
//        getclass?
        if(!(o instanceof ArrayDeque))
            return false;
        ArrayDeque<T> other = (ArrayDeque<T>)o;
        if(other.size() != size()) return false;
        for(int i = 0; i < size(); i++){
            if(this.get(i) != other.get(i))
                return false;
        }
//        for(T i : other)
        return true;
    }
}
