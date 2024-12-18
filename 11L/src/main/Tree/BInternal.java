package Tree;

import org.javatuples.Pair;

public class BInternal implements BTreeNode {
    int[] keys = new int[MAX + 1];
    BTreeNode[] child = new BTreeNode[MAX + 2];
    int size;

    public BInternal() {

        size = 0;
//        for (int i = 0; i <= MAX + 1; i++) {
//            child[i] = new BLeaf();
//        }
    }

    public Pair insert(int newKey, String item) {
        // TODO : Implement

        // find the child pointer for further insertion

        // recursively insert into child node

        // if returns a non-zero value, it indicates child node was split

        // include new added-key and child reference to current node

        // If no split occurs, return null

        // check if this node overflows. If so, split and return a pointer
        // to the new node (remember to update the size)

        // Return a pair (tuple) of the sibling and newkey

        // Step (a): Find the correct child to delegate the insertion
        int pos = 0;
        while (pos < size - 1 && newKey > keys[pos + 1]) {
            pos++;
        }

        // Step (b): Delegate the insert to the child
        Pair result = child[pos].insert(newKey, item);

        // Step (c): Handle the returned Pair if the child was split
        if (result != null) {
            BTreeNode splitNode = (BTreeNode) result.getValue0();
            int addedKey = (int) result.getValue1();

            // Shift keys and children to make room for the new key and child
            for (int i = size; i > pos + 1; i--) {
                keys[i] = keys[i - 1];
                child[i + 1] = child[i];
            }

            // Insert the new key and child at the correct position
            keys[pos + 1] = addedKey;
            child[pos + 1] = splitNode;
            size++;

            // Step (d): Check for overflow and split the node if necessary
            if (size > MAX) {
                int midIndex = size / 2;
                BInternal sibling = new BInternal();

                // Transfer the upper half of keys and children to the sibling node
                for (int i = midIndex + 1, j = 0; i < size; i++, j++) {
                    sibling.keys[j + 1] = keys[i];
                    sibling.child[j] = child[i];
                    child[i] = null; // Clear reference from the original node
                }
                sibling.child[size - midIndex - 1] = child[size];
                sibling.size = size - midIndex - 1;
                size = midIndex;

                // The middle key is promoted up, so it won't stay in either node
                int middleKey = keys[midIndex];
                keys[midIndex] = 0;  // Clear the promoted key

                // Return the new sibling and the middle key to insert into the parent
                return new Pair<>(sibling, middleKey);
            }
        }

        // If no split occurs, return null
        return null;
    }

    public void dump(int depth) {
        indent(depth);
        System.out.print("[");
        int i;
        for (i = 1; i < size - 1; i++) { // Loop to print keys, skipping the dummy key
            System.out.print(keys[i] + ",");
        }
        System.out.println(keys[size - 1] + "]");

        // call dump on all children
        for (i = 0; i < size; i++)
            if (child[i] != null) {
                child[i].dump(depth + 1);
            }
    }

    // helper method for tree display
    public void indent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print('\t');
        }
    }
}
