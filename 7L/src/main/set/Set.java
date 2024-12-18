package set;

import java.util.LinkedList;
import java.util.function.Consumer;

public class Set<T extends Comparable<T>> {
    private Elem _root;
    private int _size; 

    public Set() {
        _size = 0;
        // create a dummy root node
        _root = new Elem();
        _root.left = _root;  // empty tree
        _root.right = null;
        _root.rightThread = false;
    }

    public boolean insert(T v) {
        if (_root.left == _root) {
            _root.left = createLeaf(v, null, _root);
            _size++;
            return true;
        }
        if (insert(_root.left, v, _root)){
            _size++;
            return true;
        }
        return false;
    }

    private boolean insert(Elem cur, T value, Elem lastLeft) {
        if (value.equals(cur.info)){
            return false;
        }
        // insert at left
        if (value.compareTo(cur.info) < 0) {
            if (cur.left == null) {
                cur.left = createLeaf(value, null, cur);
                return true;
            }
            // Else continue down the tree
            return insert(cur.left, value, cur);
        }
        // insert at right
        if (!cur.rightThread){
            // Continue down the tree
            return insert(cur.right, value, lastLeft);
        } else {  // current's right is a thread; add a new node
            cur.rightThread = false;
            cur.right = createLeaf(value, null, lastLeft);
            return true;
        }
    }

    private Elem createLeaf(T value, Elem left, Elem right) {
        Elem cur = new Elem();
        cur.info = value;
        cur.left = left;
        cur.right = right;
        cur.rightThread = true;
        return cur;
    }

    public String breadthFirst() {

        StringBuilder result = new StringBuilder();
        LinkedList<Elem> queue = new LinkedList<>();

        if(_root.left != _root) {
            queue.add(_root.left);
        }

        while (!queue.isEmpty()) {
            Elem current = queue.poll();
            result.append(current.info.toString()).append(" ");
             if (current.left != null) {
                 queue.add(current.left);
             }

             if (!current.rightThread && current.right != null) {
                 queue.add(current.right);
             }
        }
        return result.toString().trim();
    }

    public String depthFirstInOrder() {
        StringBuilder result = new StringBuilder();
        Elem current = _root.left;

        while (current.left != null) {
            current= current.left;
        }
        while  (current != _root) {
            result.append(current.info.toString()).append(" ");
            if (current.rightThread) {
                current = current.right;
            } else {
                current = current.right;
                if (current != null) {
                    while (current.left != null) {
                        current = current.left;
                    }
                }
            }
        }
        return result.toString().trim();

    }

    public void forEach(Consumer<? super T> action) {
        // TODO : implement
        Elem current = _root.left;
        while (current.left != null) {
            current = current.left;
        }

        while (current != _root) {
            action.accept(current.info);
            if (current.rightThread) {
                current = current.right;
            } else {
                current = current.right;
                while (current.left != null) {
                    current = current.left;
                }
            }
        }
    }

    public int size() {
        return this._size;
    }

    public int height() {
        // TODO : implement
        return height(_root.left);
    }

    // Post order traversal height of the tree
    public int height(Elem p) {
        // TODO : implement
        if (p == null) {
            return 0;
        }
        int leftHeight = height(p.left);
        int rightHeight = p.rightThread? 0 : height(p.right);
        return 1 + Math.max(leftHeight, rightHeight);
    }


    // output the structure of tree. The tree is output as "lying down"
    // in which _root is the LEFT most Elem.
    public void printTree(int level, Elem p){
        int i;
        if (p != null) {
            if (p.right != null && !p.rightThread) {
                printTree(level+1, p.right);
            }
            for(i=0;i<level;i++) {
                System.out.print("\t");
            }
            System.out.println(p.info);
            printTree(level+1, p.left);
        }
    }

    // outputs information in tree in in order traversal order
    public void dump() {
        if (this._size != 0) { // tree non-empty
            printTree(0, _root.left);   // print tree structure
        }
    }

    private class Elem {
        T info;
        Elem left;
        Elem right;
        boolean rightThread;
        public Elem() {
            left = null;
            right = null;
            rightThread = false;
        }
    }
}
