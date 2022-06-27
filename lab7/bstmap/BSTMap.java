package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    //extends
    private class BSTNode{
        private BSTNode left;
        private BSTNode right;
        private BSTNode father;
        private K key;
        private V value;
        public BSTNode(K key, V value){
            this.key = key;
            this.value = value;
            father = null;
            left = null;
            right = null;
        }
        public BSTNode(K key, V value, BSTNode father){
            this.key = key;
            this.value = value;
            this.father = father;
            left = null;
            right = null;
        }
        public BSTNode(){
            father = null;
            left = null;
            right = null;
        }
    }
    private BSTNode root;
    private int size;
    public BSTMap(){
        root = null;
        size = 0;
    }
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        BSTNode bstNode = root;
        while(bstNode != null) {
            if (bstNode.key.compareTo(key) == 0) {
                return true;
            } else if (bstNode.key.compareTo(key) > 0) {
                bstNode = bstNode.left;
            } else if (bstNode.key.compareTo(key) < 0) {
                bstNode = bstNode.right;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        BSTNode bstNode = root;
        while(bstNode != null) {
            if (bstNode.key.compareTo(key) == 0) {
                return bstNode.value;
            } else if (bstNode.key.compareTo(key) > 0) {
                bstNode = bstNode.left;
            } else if (bstNode.key.compareTo(key) < 0) {
                bstNode = bstNode.right;
            }
        }
        return null;
    }
    public BSTNode getNode(K key) {
        BSTNode bstNode = root;
        while(bstNode != null) {
            if (bstNode.key.compareTo(key) == 0) {
                return bstNode;
            } else if (bstNode.key.compareTo(key) > 0) {
                bstNode = bstNode.left;
            } else if (bstNode.key.compareTo(key) < 0) {
                bstNode = bstNode.right;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if(size() == 0){
            root = new BSTNode(key, value);
            size ++;
            return ;
        }
        BSTNode bstNode = root;
        BSTNode bstNodeFather = root.father;
        while(bstNode != null) {
            if (bstNode.key.compareTo(key) == 0) {
                return ;
            } else if (bstNode.key.compareTo(key) > 0) {
                bstNodeFather = bstNode;
                bstNode = bstNode.left;
            } else if (bstNode.key.compareTo(key) < 0) {
                bstNodeFather = bstNode;
                bstNode = bstNode.right;
            }
        }
        if(bstNodeFather.key.compareTo(key) > 0){
            bstNodeFather.left = new BSTNode(key, value, bstNodeFather);
        }else {
            bstNodeFather.right = new BSTNode(key, value, bstNodeFather);
        }
        size ++;

    }

    @Override
    //Todo keySet()
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        BSTNode bstNode;
        List<BSTNode> list = new ArrayList<>();
        if(root != null) {
            list.add(root);
            set.add(root.key);
        }
        while(!list.isEmpty()){
            bstNode = list.remove(0);
            if(bstNode.left != null) {
                list.add(bstNode.left);
                set.add(bstNode.left.key);
            }
            if(bstNode.right != null) {
                list.add(bstNode.right);
                set.add(bstNode.right.key);
            }
        }
        return set;
    }

    @Override
    public V remove(K key) {
        if(containsKey(key)){
            BSTNode removedNode = getNode(key);
            BSTNode bstNode;
            //case1 root
            if(key.equals(root.key)){
                if(size() == 1){
                    root = null;
                    size --;
                }else if(root.left != null && root.right == null){
                    root = root.left;
                    root.father = null;
                    size --;
                }else if(root.right != null && root.left == null){
                    root = root.right;
                    root.father = null;
                    size --;
                }else {
                    bstNode = removeLeftTreeMaxNode();
                    root.left.father = bstNode;
                    root.right.father = bstNode;
                    bstNode.left = root.left;
                    bstNode.right = root.right;
                    root = bstNode;
                    bstNode.father = null;
                }
            }
            //case2 child &&!root
            else if(removedNode.right == null && removedNode.left == null){
                if(removedNode.equals(removedNode.father.left))
                    removedNode.father.left = null;
                else{
                    removedNode.father.right = null;
                }
                size --;
            }
            //case3 has a child &&!root
            else if(removedNode.right == null){
                if(removedNode.equals(removedNode.father.left))
                    removedNode.father.left = removedNode.left;
                else{
                    removedNode.father.right = removedNode.left;
                }
                size --;
            }
            else if(removedNode.left == null){
                if(removedNode.equals(removedNode.father.right))
                    removedNode.father.right = removedNode.right;
                else{
                    removedNode.father.left = removedNode.right;
                }
                size --;
            }
            //case4 has two childs &&!root
            else{
                bstNode = removeLeftTreeMaxNode();
                bstNode.left = root.left;
                bstNode.right = root.right;
                bstNode.father = null;
                root = bstNode;
            }
            return removedNode.value;
        }
        return null;
    }
    public BSTNode removeLeftTreeMaxNode(){
        BSTNode bstNode = root.left;
        while(bstNode.right != null)
            bstNode = bstNode.right;
        remove(bstNode.key);
        return bstNode;
    }

    @Override
    public V remove(K key, V value) {
        if(containsKey(key) && get(key).equals(value))
            return remove(key);
        return null;
    }

    //Todo iterator()
    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
    //Todo how to fix it?
    public void printInOrder(){
        throw new UnsupportedOperationException();
//        printInOrder(root);
    }
    public void printInOder(BSTNode root){
        if(root.left != null)
            printInOder(root.left);
        System.out.println(root.key + " ");
        if(root.right != null)
            printInOder(root.right);
    }

}
