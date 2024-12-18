
public class Map implements Cloneable {

    private class Elem {
        String key;
        String value;
        Elem left;
        Elem right;
    }

    private Elem _root;
    private int _size;

    public Map() {
        _root = new Elem();
        _root.left = _root;
        _root.right = null;
        _size = 0;
    }

    public Map(Map v) {

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
            _size = v._size;
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
            newRoot.right = copyTree(origRoot.right);
            return newRoot;
        }
    }

    public boolean insert(String k, String v) {
        //TODO: Implement
        if (_root.left == _root) {
            Elem newElement = new Elem();
            newElement.key = k;
            newElement.value = v;
            newElement.left = null;
            newElement.right = null;
            _root.left = newElement;
            _size++;
            return true;
        } else {
            return insertHelper(_root.left, k, v);
        }
    }
    private boolean insertHelper(Elem current, String k, String v) {
        if (current == null) {
            return false;
        }
        if (k.compareTo(current.key) < 0) {
            if (current.left == null) {
                Elem newElement = new Elem();
                newElement.key = k;
                newElement.value = v;
                newElement.left = null;
                newElement.right = null;
                current.left = newElement;
                _size++;
                return true;
            } else {
                return insertHelper(current.left, k, v);
            }
        } else if (k.compareTo(current.key) > 0) {
                if (current.right == null) {
                    Elem newElement = new Elem();
                    newElement.key = k;
                    newElement.value = v;
                    newElement.left = null;
                    newElement.right = null;
                    current.right = newElement;
                    _size++;
                    return true;
                } else {
                    return insertHelper(current.right, k, v);
            }
        } else {
            return false;
        }
    }


    public boolean erase(String k) {
        //TODO: Implement
        if (_root.left == _root) {
            return false;
        }
        return eraseHelper(null, _root.left, k);
    }

    private boolean eraseHelper(Elem parent, Elem current, String k) {
        if (current == null) {
            return false;
        }
        if (k.compareTo(current.key) < 0) {
            return eraseHelper(current, current.left, k);
        } else if (k.compareTo(current.key) > 0) {
            return eraseHelper(current, current.right, k);
        } else {  // Found the node to delete
            if (current.left != null && current.right != null) {  // Two children
                Elem successor = findMin(current.right);
                current.key = successor.key;
                current.value = successor.value;
                return eraseHelper(current, current.right, successor.key);
            } else if (parent == null) {  // Deleting the root node
                _root.left = (current.left != null) ? current.left : current.right;
            } else if (parent.left == current) {
                parent.left = (current.left != null) ? current.left : current.right;
            } else {
                parent.right = (current.left != null) ? current.left : current.right;
            }
            _size--;
            return true;
        }
    }

    private Elem findMin(Elem node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void clear() {
        //TODO: Implement
        _root.left = _root;
        _size = 0;
    }

    public boolean isEmpty() {
        //TODO: Implement
        return _size == 0;
    }

    public int size() {
        //TODO: Implement
        return _size;
    }

    @SuppressWarnings("unchecked")
    public String get(Object k) {
        //TODO: Implement
        if (_root.left == _root) {
            return null;
        } else {
            return getHelper(_root.left, (String) k );
        }
    }

    private String getHelper(Elem current, String k){
        if (current == null) {
            return null;
        }
        if (k.compareTo(current.key) < 0) {
            return getHelper(current.left, k);
        } else if (k.compareTo(current.key) > 0) {
            return getHelper(current.right, k);
        } else {
            return current.value;
        }
    }

    public String replace(String key, String val) {
        //TODO: Implement
        if (_root.left == _root) {
            return null;
        }
        return replaceHelper(_root.left, key, val);
    }
    private String replaceHelper(Elem Current, String key, String val)
    {
        if (Current == null) {
            return null;
        }
        if (key.compareTo(Current.key) < 0 ) {
            return replaceHelper(Current.left, key,val);
        } else if (key.compareTo(Current.key) > 0) {
            return replaceHelper(Current.right, key, val);
        } else {
            String oldValue = Current.value;
            Current.value = val;
            return oldValue;
        }
    }

    boolean containsKey(String k) {
        //TODO: Implement
        return get(k) != null;
    }

    String getorDefault(String key, String defaultValue) {
        //TODO: Implement
        String value = get(key);
        return value == null? defaultValue: value;
    }

    String insertIfAbsent(String key, String value) {
        //TODO: Implement
        String currValue = get(key);
        if (currValue == null) {
            insert(key, value);
            return null;
        }
        return currValue;
    }

    @Override
    public Object clone() {
        //TODO: Implement
        Map newMap = new Map();
        if (this._root.left != this._root) {
            newMap._root = new Elem();
            newMap._root.left = _root.left;
            newMap._root.left = copyTree(this._root.left);
            newMap._size = this._size;
        } else {
            newMap._root = new Elem();
            newMap._root.left = newMap._root;
            newMap._size = 0;
        }
        return newMap;
    }

    private void printTree(int level, Elem p, StringBuilder res) {
        int i;
        if (p != null ) {
            printTree(level+1, p.right, res);
            for(i=0;i<level;i++) {
                res.append("\t");;
            }
            res.append(p.key).append(" ").append(p.value).append("\n");
            printTree(level+1, p.left, res);
        }
    }

    @Override
    public String toString() {
        if (_root == _root.left) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        printTree(0, _root.left, res);
        return res.toString();
    }
}
