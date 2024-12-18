import java.util.Iterator;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.NoSuchElementException;
import java.util.AbstractMap;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/** 	CS515 Assignment X
 Name: Qifei Wang
 Section: 2
 Date: 12/9/2024
 Collaboration Declaration:
 Collaboration: None
 */

public class Map<K extends Comparable<K>, V> implements Cloneable {

    private class Elem implements Entry<K,V> {
        K key;
        V value;
        Elem left;
        Elem right;
        int height;
        boolean rightThread;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V v) {
            V old = value;
            value = v;
            return old;
        }

        public String toString() {
            return String.format("%s %s(%d)", key, value, height);
        }
    }

    private Elem _root;
    private int _size;

    public Map() {
        _root = new Elem();
        _root.left = _root;
        _root.right = null;
        _root.rightThread = false;
        _size = 0;
    }

    // Copy constructor
    public Map(Map<K, V> v) {
        if (v._root == v._root.left) {
            _root = new Elem();
            _root.left = _root;
            _root.right = null;
            _size = 0;
        } else {
            _root = new Elem();
            _root.left = _root;
            _root.right = null;
            _root.left = copyTree(v._root.left);
            copyThread(_root, v._root);
            _size = v._size;
        }
    }

    // Height utility method
    private int height(Elem node) {
        return node == null ? -1 : node.height;
    }

    // Balance factor utility method
    private int balanceFactor(Elem node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Update height of a node
    private void updateHeight(Elem node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // Right rotation
    private Elem rotateRight(Elem y) {
        Elem x = y.left;
        Elem T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Left rotation
    private Elem rotateLeft(Elem x) {
        Elem y = x.right;
        Elem T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Rebalance the tree
    private Elem rebalance(Elem node) {
        if (node == null) return null;

        // Update height
        updateHeight(node);

        // Get balance factor
        int balance = balanceFactor(node);

        // Left heavy
        if (balance > 1) {
            if (balanceFactor(node.left) < 0) {
                // Left-Right case
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        // Right heavy
        if (balance < -1) {
            if (balanceFactor(node.right) > 0) {
                // Right-Left case
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    // Insert method
    public boolean insert(K k, V v) {
        if (k == null) return false;

        // Special case for empty tree
        if (_root.left == _root) {
            Elem newNode = new Elem();
            newNode.key = k;
            newNode.value = v;
            newNode.height = 0;
            _root.left = newNode;
            newNode.right = _root;
            newNode.rightThread = true;
            _size++;
            return true;
        }

        // Recursive insert with path tracking
        InsertResult result = insertRecursive(_root.left, k, v);
        if (!result.inserted) return false;

        _root.left = result.node;
        _size++;
        return true;
    }

    // Helper class for insert recursive result
    private class InsertResult {
        Elem node;
        boolean inserted;

        InsertResult(Elem node, boolean inserted) {
            this.node = node;
            this.inserted = inserted;
        }
    }

    // Recursive insert with AVL balancing
    private InsertResult insertRecursive(Elem node, K k, V v) {
        // Base case: if node is null, create new node
        if (node == null || node == _root) {
            Elem newNode = new Elem();
            newNode.key = k;
            newNode.value = v;
            newNode.height = 0;
            return new InsertResult(newNode, true);
        }

        // Compare keys
        int cmp = k.compareTo(node.key);

        // Key already exists
        if (cmp == 0) return new InsertResult(node, false);

        if (cmp < 0) {
            // Insert in left subtree
            if (node.left == null) {
                InsertResult leftResult = insertRecursive(null, k, v);
                node.left = leftResult.node;

                // Threading for left subtree
                leftResult.node.right = node;
                leftResult.node.rightThread = true;
            } else {
                InsertResult leftResult = insertRecursive(node.left, k, v);
                if (!leftResult.inserted) return leftResult;
                node.left = leftResult.node;
            }
        } else {
            // Insert in right subtree
            if (node.rightThread) {
                InsertResult rightResult = insertRecursive(null, k, v);
                node.right = rightResult.node;
                rightResult.node.right = _root;
                node.rightThread = false;
            } else {
                InsertResult rightResult = insertRecursive(node.right, k, v);
                if (!rightResult.inserted) return rightResult;
                node.right = rightResult.node;
            }
        }

        // Rebalance the node
        return new InsertResult(rebalance(node), true);
    }

    // Clear the map
    public void clear() {
        _root.left = _root;
        _root.right = null;
        _root.rightThread = false;
        _size = 0;
    }

    // Check if map is empty
    boolean isEmpty() {
        return _size == 0;
    }

    // Get size
    public int size() {
        return _size;
    }

    // Get value for a key
    @SuppressWarnings("unchecked")
    public V get(Object k) {
        if (k == null) return null;
        Elem node = findNode(_root.left, (K)k);
        return node == null ? null : node.value;
    }

    // Find node for a key
    private Elem findNode(Elem node, K k) {
        while (node != null && node != _root) {
            int cmp = k.compareTo(node.key);
            if (cmp == 0) return node;
            if (cmp < 0) {
                node = node.left;
            } else {
                if (node.rightThread) break;
                node = node.right;
            }
        }
        return null;
    }

    // Replace value for a key
    public V replace(K key, V val) {
        Elem node = findNode(_root.left, key);
        if (node == null) return null;

        V oldVal = node.value;
        node.value = val;
        return oldVal;
    }

    // Check if key exists
    boolean containsKey(K k) {
        return findNode(_root.left, k) != null;
    }

    // Get value or default
    V getorDefault(K key, V defaultValue) {
        Elem node = findNode(_root.left, key);
        return node == null ? defaultValue : node.value;
    }

    // Insert if key is absent
    V insertIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            insert(key, value);
            return null;
        }
        return get(key);
    }

    // Recursive forEach implementation
    public void forEach(BiConsumer<? super K, ? super V> action) {
        forEachRecursive(_root.left, action);
    }

    // Helper method for forEach
    private void forEachRecursive(Elem node, BiConsumer<? super K, ? super V> action) {
        if (node == null || node == _root) return;

        forEachRecursive(node.left, action);
        action.accept(node.key, node.value);

        if (!node.rightThread) {
            forEachRecursive(node.right, action);
        }
    }

    // Recursive replaceAll implementation
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        replaceAllRecursive(_root.left, function);
    }

    // Helper method for replaceAll
    private void replaceAllRecursive(Elem node, BiFunction<? super K, ? super V, ? extends V> function) {
        if (node == null || node == _root) return;

        replaceAllRecursive(node.left, function);
        node.value = function.apply(node.key, node.value);

        if (!node.rightThread) {
            replaceAllRecursive(node.right, function);
        }
    }

    // Clone method
    @Override
    public Object clone() {
        return new Map<>(this);
    }

    // Helper methods for copy constructor
    private void addToMap(Elem root, TreeMap<K, Elem> keyElemMap) {
        if(root != null) {
            keyElemMap.put(root.key, root);
            addToMap(root.left, keyElemMap);
            if(!root.rightThread) {
                addToMap(root.right, keyElemMap);
            }
        }
    }

    private void copyThread(Elem newRoot, Elem origRoot) {
        TreeMap<K, Elem> newKeyElemMap = new TreeMap<>();
        TreeMap<K, Elem> origKeyElemMap = new TreeMap<>();

        addToMap(newRoot.left, newKeyElemMap);
        addToMap(origRoot.left, origKeyElemMap);

        NavigableMap<K, Elem> revMap = origKeyElemMap.descendingMap();

        Iterator<NavigableMap.Entry<K,Elem>> it = revMap.entrySet().iterator();
        Entry<K, Elem> e = it.next();
        Elem temp = newKeyElemMap.get(e.getKey());
        temp.rightThread = true;
        temp.right = newRoot;

        while(it.hasNext()) {
            e = it.next();
            Elem o = origKeyElemMap.get(e.getKey());
            if(o.rightThread) {
                temp = newKeyElemMap.get(e.getKey());
                temp.rightThread = true;
                temp.right = newKeyElemMap.get(origKeyElemMap.get(e.getKey()).right.key);
            }
        }
    }

    private Elem copyTree(Elem origRoot) {
        if (origRoot == null) {
            return null;
        } else {
            Elem newRoot = new Elem();
            newRoot.key = origRoot.key;
            newRoot.value = origRoot.value;
            newRoot.left = copyTree(origRoot.left);
            if (!origRoot.rightThread)
                newRoot.right = copyTree(origRoot.right);
            return newRoot;
        }
    }

    // Printing method
    private void printTree(int level, Elem p, StringBuilder res) {
        int i;
        if (p != null ) {
            if (p.right != null  && !p.rightThread)
                printTree(level+1, p.right, res);
            for(i=0;i<level;i++) {
                res.append("\t");;
            }
            res.append(p.toString()).append("\n");

            printTree(level+1, p.left, res);
        }
    }

    public String toString() {
        if (_root == _root.left) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        printTree(0, _root.left, res);
        return res.toString();
    }

    // Iterator implementation
    public Iterator<Entry<K,V>> iterator() {
        return new EntryIterator();
    }

    public class EntryIterator implements Iterator<Entry<K,V>> {
        private Elem ptr;

        private EntryIterator() {
            // Find leftmost node
            ptr = _root.left;
            while (ptr.left != null) {
                ptr = ptr.left;
            }
        }

        public Entry<K,V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Entry<K,V> result = new SimpleImmutableEntry<>(ptr.key, ptr.value);

            // Find next node
            if (ptr.rightThread) {
                ptr = ptr.right;
            } else {
                // Go to right subtree's leftmost node
                ptr = ptr.right;
                while (ptr.left != null) {
                    ptr = ptr.left;
                }
            }

            return result;
        }

        public boolean hasNext() {
            return ptr != _root;
        }
    }
}