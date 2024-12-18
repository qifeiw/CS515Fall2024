public class Set {

    private final int ALPHABET_SIZE = 26;

    class TrieNode {

        TrieNode[] child;
        boolean isWord;

        public TrieNode() {
            isWord = false;
            child = new TrieNode[ALPHABET_SIZE]; // very space inefficient
        }
    }

    TrieNode _root;
    int _size;

    public Set() {
        // TO IMPLEMENT
        _root = new TrieNode();
        _size = 0;
    }

    // insert a key into the set
    // return false if key already exists; otherwise insert new key and return true
    public boolean insert(String key) {
        // TO IMPLEMENT
        TrieNode curr = _root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) -'a';
            if (curr.child[index] == null) {
                curr.child[index] = new TrieNode();
            }
            curr = curr.child[index];
        }

        if (curr.isWord) {
            return false;
        }
        curr.isWord = true;
        _size++;
        return true;
    }

    // remove a key from the set
    // return false if the key doesn't exist; otherwise remove key and return true
    public boolean erase(String key) {
        // TO IMPLEMENT
        TrieNode curr = _root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (curr.child[index] == null) {
                return false;
            }
            curr = curr.child[index];
        }
        if (!curr.isWord) {
            return false;
        }
        curr.isWord = false;
        _size--;
        return true;
    }

    // search for a key
    // return true if an element is found; false otherwise
    public boolean find(String key) {
        // TO IMPLEMENT
        TrieNode curr = _root;
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (curr.child[index] == null) {
                return false;
            }
            curr = curr.child[index];
        }
        return curr.isWord;
    }

     // return size of the set
    public int size() {
        // TO IMPLEMENT
        return _size;
    }

}
