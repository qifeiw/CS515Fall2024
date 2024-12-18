import java.util.Iterator;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.NoSuchElementException;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Map<K extends Comparable<K>, V> implements Cloneable {

    private class Elem {
        K key;
        V value;
        Elem left;
        Elem right;
        boolean rightThread;
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
            if( o.rightThread) {
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

    public boolean insert(K k, V v) {
        //To Be Implemented
        Elem parent = _root;   // Start at the dummy root
        Elem current = _root.left;  // Start traversing from the actual root

        // Traverse the tree to find the correct insertion point
        while (current != null && current != _root) {  // Ensure `current` is not null or `_root`
            parent = current;  // Keep track of the parent

            if (k.compareTo(current.key) == 0) {
                return false;  // Duplicate keys are not allowed
            } else if (k.compareTo(current.key) < 0) {
                current = current.left;  // Move to the left child
            } else if (!current.rightThread) {
                current = current.right;  // Move to the right child if it's not a thread
            } else {
                break;  // We found a thread, stop traversing
            }
        }

        // Create a new element
        Elem newE = new Elem();
        newE.key = k;
        newE.value = v;
        newE.left = null;  // New element has no left child
        newE.rightThread = true;  // New element has a right thread

        // Insert the new element in the correct position
        if (parent == _root || k.compareTo(parent.key) < 0) {
            newE.right = parent;  // Insert as the left child, with right thread to parent
            parent.left = newE;
        } else {
            newE.right = parent.right;  // Insert as the right child
            parent.right = newE;  // Update parent's right to the new element
            parent.rightThread = false;  // Parent no longer has a thread
        }

        _size++;  // Increment the size of the tree
        return true;
    }

    public boolean erase(K k) {
        //TO BE IMPLEMENTED
        Elem parent = _root;
        Elem current = _root.left;

        // Find the node to erase
        while (current != _root) {
            if (k.compareTo(current.key) == 0) {
                break;
            }
            parent = current;
            if (k.compareTo(current.key) < 0) {
                current = current.left;
            } else if (!current.rightThread) {
                current = current.right;
            } else {
                return false;  // Key not found
            }
        }

        if (current == _root) {
            return false; // Key not found
        }

        // Case 1: Node has no children (leaf node)
        if (current.left == null && current.rightThread) {
            if (parent.left == current) {
                parent.left = null;
            } else {
                parent.rightThread = true;
                parent.right = current.right;
            }
        }
        // Case 2: Node has only a left child
        else if (current.rightThread) {
            if (parent.left == current) {
                parent.left = current.left;
            } else {
                parent.rightThread = true;
                parent.right = current.right;
            }
        }
        // Case 3: Node has only a right child (right thread is not set)
        else if (current.left == null) {
            if (parent.left == current) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        }
        // Case 4: Node has two children
        else {
            Elem successorParent = current;
            Elem successor = current.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            // Swap values
            current.key = successor.key;
            current.value = successor.value;

            if (successorParent != current) {
                successorParent.left = successor.rightThread ? null : successor.right;
            } else {
                current.right = successor.right;
            }
        }

        _size--;
        return true;
    }


	public void clear() {
        // TO BE IMPLEMENTED
        _root.left = _root;
        _size = 0;
    }

    boolean isEmpty() {
        // TO BE IMPLEMENTED
        return _size == 0;
    }

    public int size() {
        // TO BE IMPLEMENTED
        return _size;
    }

    @SuppressWarnings("unchecked")
    public V get(Object k) {
        // TO BE IMPLEMENTED
        Elem current = _root.left;
        while (current != null && current != _root) {
            if (((K) k).compareTo(current.key) == 0) {
                return current.value;
            } else if (((K) k).compareTo(current.key) < 0) {
                current = current.left;
            } else if (!current.rightThread) {
                current = current.right;
            } else {
                break;
            }
        }
        return null;
    }

    public V replace(K key, V val) {
        // TO BE IMPLEMENTED
        Elem current = _root.left;
        while (current != _root) {
            if (key.compareTo(current.key) == 0) {
                V oldV = current.value;
                current.value = val;
                return oldV;
            } else if (!current.rightThread) {
                current = current.right;
            } else {
                break;
            }
        }
        return null;
    }

    boolean containsKey(K k) {
        // TO BE IMPLEMENTED
        Elem current = _root.left;
        while (current != _root) {
            if (k.compareTo(current.key) == 0) {
                return true;
            } else if (k.compareTo(current.key) < 0) {
                current = current.left;
            } else if (!current.rightThread) {
                current = current.right;
            } else {
                break;
            }
        }
        return false;
    }

    V getorDefault(K key, V defaultValue) {
        // TO BE IMPLEMENTED
        V result = get(key);
        return (result != null)? result: defaultValue;
    }

    V insertIfAbsent(K key, V value) {
        // TO BE IMPLEMENTED
        Elem current = _root.left;
        Elem parent = _root;

        while (current != _root) {
            if(key.compareTo(current.key) == 0) {
                return current.value;
            } else if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else if (!current.rightThread) {
                current = current.right;
            } else {
                break;
            }
        }

        Elem newE = new Elem();
        newE.key = key;
        newE.value = value;
        newE.rightThread = true;

        if(parent == _root || key.compareTo(parent.key) < 0) {
            newE.right = parent;
            parent.left = newE;
        } else {
            newE.right = parent.right;
            parent.right = newE;
            parent.rightThread = false;
        }
        _size++;
        return null;
    }

    public void forEach(BiConsumer<? super K, ? super V> action) {
        //TODO: Implement
        forEachHelper(_root.left, action);
    }

    private void forEachHelper(Elem node, BiConsumer<? super K, ? super V> action) {
        if (node != null) {
            forEachHelper(node.left, action);
            action.accept(node.key, node.value);
            if (!node.rightThread) {
                forEachHelper(node.right, action);
            }
        }
    }

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        //TODO: Implement
        replaceAllHelper(_root.left, function);
    }

    private void replaceAllHelper(Elem node, BiFunction<? super K, ? super V, ? extends V> function) {
        if (node != null) {
            replaceAllHelper(node.left, function);
            node.value = function.apply(node.key, node.value);
            if (!node.rightThread) {
                replaceAllHelper(node.right, function);
            }
        }
    }

    @Override
    public Map<K, V> clone() {
        //TO BE IMPLEMENTED
        return new Map<>(this);
    }

    private void printTree(int level, Elem p, StringBuilder res) {
        int i;
        if (p != null ) {
            if (p.right != null  && !p.rightThread)
                printTree(level+1, p.right, res);
            for(i=0;i<level;i++) {
                res.append("\t");;
            }
            res.append(p.key).append(" ").append(p.value).append("\n");
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

    public Map<K,V>.KeyIterator keyIterator() {
        return new KeyIterator();
    }

    public class KeyIterator implements Iterator {

        private Elem ptr;

        private KeyIterator() {
            // TO BE IMPLEMENTED
            ptr = _root;

            //move to the left-most node
            while (ptr.left != null) {
                ptr = ptr.left;
            }
        }

        public K next() {
            // TO BE IMPLEMENTED
            if (! hasNext()) {
                throw new NoSuchElementException();
            }

            K key = ptr.key;

            if (ptr.rightThread) {
                ptr = ptr.right;
            } else {
                ptr = ptr.right;
                while (ptr.left != null) {
                    ptr = ptr.left;
                }
            }
            return key;
        }

        public boolean hasNext() {
            // TO BE IMPLEMENTED
            return ptr != _root && ptr.right != null;
        }
    }
}
