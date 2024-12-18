package SkipList;

import java.util.Random;

public class SkipList {
    SkipNode _head;
    SkipNode _tail; // Sentinel head and tail
    int _curHeight;
    int _maxHeight;
    double _probability;
    Random randNum;

    // SkipList constructor: construct an empty skiplist of max height m and and
    // probability p with default value 0.5
    // current height initialize to 1
    public SkipList(int m, double p) {
        randNum = new Random(8675309); // DO NOT CHANGE THIS :)
        _curHeight = 1;
        _maxHeight = m;
        _probability = p;
        // Create head with intMin and tail with intMax;
        int intMin = Integer.MIN_VALUE; // minimum int value
        int intMax = Integer.MAX_VALUE; // maximum int value
        _head = new SkipNode(intMin, _maxHeight);
        _tail = new SkipNode(intMax, _maxHeight);
        // attach them to form an empty list
        for (int x = 1; x <= _maxHeight; x++) {
            _head.nextNodes[x] = _tail;
        }
    }

    // copy constructor
    public SkipList(SkipList v) {
        randNum = new Random(8675309); // DO NOT CHANGE THIS :)
        _curHeight = v._curHeight;
        _maxHeight = v._maxHeight;
        _probability = v._probability;
        copyCode(v);
    }

    // common copy code
    public void copyCode(SkipList v) {
        int intMin = Integer.MIN_VALUE; // minimum int value
        int intMax = Integer.MAX_VALUE; // maximum int value
        _head = new SkipNode(intMin, _maxHeight);
        _tail = new SkipNode(intMax, _maxHeight);

        for (int x = 1; x <= _maxHeight; x++) {
            _head.nextNodes[x] = _tail;
        }

        SkipNode[] lookBack = new SkipNode[_maxHeight + 1];
        for (int x = 1; x <= _maxHeight; x++) {
            lookBack[x] = _head;
        }

        // copy elements
        SkipNode cur = v._head.nextNodes[1];
        while (cur != v._tail) {
            // create a new node
            SkipNode add = new SkipNode(cur.info, cur.height);
            for (int x = 1; x <= cur.height; x++) {

                // set add's nextnodes to lookback's next
                // TODO: Implement

                // set lookback's nextnodes to add
                // TODO: Implement

                // update lookback
                // TODO: Implement
                add.nextNodes[x] = lookBack[x].nextNodes[x];
                lookBack[x].nextNodes[x] = add;
                lookBack[x] = add;

            }
            cur = cur.nextNodes[1];
        }
    }


    // insert a node key into the skiplist, return false if key already exists
    // otherwise insert key and return true.
    public boolean insert(int info) {
        // pointer array to keep track of the next nodes at the insertion point
        SkipNode[] lookBack = new SkipNode[_maxHeight + 1];
        SkipNode cur = _head;
        int curInfo;
        // find where new node goes
        for (int h = _curHeight; h >= 1; h--) {
            curInfo = cur.nextNodes[h].info;
            while (curInfo < info) {
                cur = cur.nextNodes[h];
                curInfo = cur.nextNodes[h].info;
            }
            lookBack[h] = cur; // keep track of previous node
        }
        cur = cur.nextNodes[1]; // move to next node at level 1
        curInfo = cur.info;

        int lvl = 0;
        // If dup, return false
        if (curInfo == info) {
            return false;
        } else {

            // TODO: Implement
            // FILL IN HERE (using comments to help guide you)
            // get the random height for the new node (call randomLevel)

            // prepare insertion point
            // (check if node has higher level than _curHeight, fix lookBack & _curHeight as needed)

            // Insert new element

            // Generate a random level for the new node
            int newLevel = randomLevel();

            // Update _curHeight if necessary
            if (newLevel > _curHeight) {
                for (int h = _curHeight + 1; h <= newLevel; h++) {
                    lookBack[h] = _head;
                }
                _curHeight = newLevel;
            }

            // Create the new node
            SkipNode newNode = new SkipNode(info, newLevel);

            // Insert the new node by updating pointers
            for (int h = 1; h <= newLevel; h++) {
                newNode.nextNodes[h] = lookBack[h].nextNodes[h];
                lookBack[h].nextNodes[h] = newNode;
            }

            return true;
        }
    }

    // erase a node key from the skiplist, return false if key doesnot exists
    // otherwise erase key and return true.
    public boolean erase(int info) {
        // TODO: Implement
        // Track nodes that point to where the new node will be inserted
        SkipNode[] lookBack = new SkipNode[_maxHeight + 1];
        SkipNode cur = _head;

        // Locate the node to erase by updating lookBack
        for (int h = _curHeight; h >= 1; h--) {
            while (cur.nextNodes[h].info < info) {
                cur = cur.nextNodes[h];
            }
            lookBack[h] = cur; // Keep track of nodes at each level before the target node
        }
        cur = cur.nextNodes[1]; // Move to the node at the bottom level

        // If the node to erase does not exist, return false
        if (cur.info != info) {
            return false;
        }

        // Update pointers to skip over the node being erased
        for (int h = 1; h <= cur.height; h++) {
            if (lookBack[h].nextNodes[h] != cur) break;
            lookBack[h].nextNodes[h] = cur.nextNodes[h];
        }

        // Adjust _curHeight if necessary (if the topmost levels are empty after deletion)
        while (_curHeight > 1 && _head.nextNodes[_curHeight] == _tail) {
            _curHeight--;
        }

        return true;
    }

    // Returns a random height between 1 and maximum node height.
    private int randomLevel() {
        int level = 1;
        while (((double) randNum.nextFloat() < _probability) && (level < _maxHeight))
            level++;
        return level;
    }

    // return ture if the key is found, otherwise false
    public boolean find(int info) {
        int curInfo = 0;
        SkipNode cur = _head;
        // find starts at top level
        for (int h = _curHeight; h >= 1; h--) {
            curInfo = cur.nextNodes[h].info;
            while (curInfo < info) { // remain the current level before move down a level
                cur = cur.nextNodes[h];
                curInfo = cur.nextNodes[h].info;
            }
        }

        return (curInfo == info);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SkipNode tmp = _head.nextNodes[1];
        while (tmp != _tail) {
            for (int h = 1; h <= tmp.height; h++)
                sb.append(tmp.info + "    ");
            sb.append('\n');
            tmp = tmp.nextNodes[1];
        }
        return sb.toString();
    }

    private class SkipNode {
        SkipNode[] nextNodes;
        int height;
        int info;

        // construct a new skipnode with given key and height
        public SkipNode(int in, int h) {
            info = in;
            height = h;
            nextNodes = new SkipNode[h + 1]; // height 0 un-used
            for (int x = 1; x <= height; x++) {
                nextNodes[x] = null;
            }
        }
    }
}
