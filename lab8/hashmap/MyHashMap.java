package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int itemsNumber;
    private int bucketsNumber;
    private double loadFactor;
    /** Constructors */
    public MyHashMap() {
        this(16,0.75);

    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        itemsNumber = 0;
        bucketsNumber = initialSize;
        loadFactor = maxLoad;
        buckets = createTable(bucketsNumber);
        for(int i = 0; i < initialSize; i++)
            buckets[i] = createBucket();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    public int getIndex(K key){
        return Math.floorMod(key.hashCode(), bucketsNumber);
    }
    @Override
    public void clear() {
        //TODO   weird wrong!!!! How to fix it
//        for(Collection<Node> bucket : buckets)
//            bucket = createBucket();
        for(int i = 0; i < bucketsNumber; i++)
            buckets[i] = createBucket();
        itemsNumber = 0;
    }

    @Override
    public boolean containsKey(K key) {
        for(Node item : buckets[getIndex(key)]){
            if(item.key.equals(key))
                return true;
        }
        return false;
    }

    @Override
    public V get(K key) {
        for(Node item : buckets[getIndex(key)]){
            if(item.key.equals(key))
                return item.value;
        }
        return null;
    }

    @Override
    public int size() {
        return itemsNumber;
    }

    @Override
    public void put(K key, V value) {
        if(key == null) ;
        else if(containsKey(key)){
            for(Node item : buckets[getIndex(key)]) {
                if (item.key.equals(key)) {
                    item.value = value;
                }
            }
        }else {
            itemsNumber++;
            if((double)(itemsNumber)/bucketsNumber > loadFactor){
                sizeUp();
            }
            buckets[getIndex(key)].add(new Node(key, value));
        }
    }
    public void sizeUp(){
        Collection<Node>[] preBuckets = buckets;

        bucketsNumber *= 2;
        buckets = createTable(bucketsNumber);
        itemsNumber = 1;
        for(int i = 0; i < bucketsNumber; i++)
            buckets[i] = createBucket();

        for(Collection<Node> bucket : preBuckets){
            for(Node item : bucket){
                put(item.key, item.value);
            }
        }

    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for(Collection<Node> bucket : buckets) {
            for (Node item : bucket) {
                set.add(item.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if(containsKey(key)){
            for(Node item : buckets[getIndex(key)]){
                if(item.key.equals(key)) {
                    buckets[getIndex(key)].remove(item);
                    return item.value;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        if(containsKey(key)){
            for(Node item : buckets[getIndex(key)]){
                if(item.key.equals(key) && item.value.equals(value)) {
                    buckets[getIndex(key)].remove(item);
                    return item.value;
                }
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
