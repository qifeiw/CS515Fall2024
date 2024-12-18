package Set;

public class AVLSet {
    Elem _root;
    int _size;

    public AVLSet() {
        _root = null;
        _size = 0;
    }

    public AVLSet(AVLSet rhs) {
        _root = copyCode(rhs._root);
    }

    // single right rotation
    public Elem rotateRight(Elem node) {
        // TODO : Implement
        // Make sure you return the new root of the rotated subtree
        Elem newRoot = node.left;          // Left child becomes the new root
        node.left = newRoot.right;         // Right subtree of new root becomes left subtree of original root
        newRoot.right = node;              // Original root becomes right child of new root

        // Update heights
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;

        return newRoot;
    }

    // single left rotation
    public Elem rotateLeft(Elem node) {
        // TODO : Implement
        // Make sure you return the new root of the rotated subtree
        Elem newRoot = node.right;         // Right child becomes the new root
        node.right = newRoot.left;         // Left subtree of new root becomes right subtree of original root
        newRoot.left = node;               // Original root becomes left child of new root

        // Update heights
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), height(newRoot.right)) + 1;

        return newRoot;                    // Return the new root after rotation
    }

    // double right rotation
    public Elem doubleRotateRight(Elem node) {
        // TODO : Implement
        // Make sure you return the new root of the rotated subtree
        node.left = rotateLeft(node.left); // First perform a left rotation on the left child
        return rotateRight(node);          // Then perform a right rotation on the node itself
    }

    // double left rotation
    public Elem doubleRotateLeft(Elem node) {
        // TODO : Implement
        // Make sure you return the new root of the rotated subtree
        node.right = rotateRight(node.right); // First perform a right rotation on the right child
        return rotateLeft(node);              // Then perform a left rotation on the node itself
    }

    // insert an element
    public void insert(int x) {
        _root = insert(x, _root);
    }

    // helper method for recusively insertion
    public Elem insert(int x, Elem node) {
        if (node == null) {
            node = new Elem(x, null, null, 0);
            _size++;
        }
        // move to left
        if (x < node.info) {
            node.left = insert(x, node.left);
            if (height(node.left) - height(node.right) == 2)
                if (x < node.left.info)
                    node = rotateRight(node);
                else
                    node = doubleRotateRight(node);
        }
        // move to right
        else if (x > node.info) {
            node.right = insert(x, node.right);
            if (height(node.right) - height(node.left) == 2)
                if (x > node.right.info)
                    node = rotateLeft(node);
                else
                    node = doubleRotateLeft(node);
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1; // update height
        return node;
    }

    // remove an element
    public void erase(int x) {
        _root = erase(x, _root);
    }

    // helper method for recusive removal
    public Elem erase(int x, Elem node) {
        if (node == null) // item not found
            return null;
        else if (x < node.info) // move to left branch; erase recursively
            node.left = erase(x, node.left);
        else if (x > node.info) // move to right branch; erase recursively
            node.right = erase(x, node.right);
        else { // found and remove
            if (node.left != null && node.right != null) { // have two children
                node.info = findMin(node.right).info; // swap the info of the mininum node in the left subtree to
                                                      // current node
                node.right = erase(node.info, node.right); // then remove the mininum node in the right subtree
            } else { // one child or none delete right here
                Elem tmp = node;
                node = node.left != null ? node.left : node.right;
                _size--;
            }
        }

        // must check all node from the delete point to root and balance ALL are out of
        // balance
        if (node != null) { // node is not null
            if (height(node.left) - height(node.right) == 2) { // calculate load factor
                if (height(node.left.left) >= height(node.left.right)) // outside case
                    node = rotateRight(node);
                else // inside case
                    node = doubleRotateRight(node);
            }

            if (height(node.left) - height(node.right) == -2) { // calculate load factor
                if (height(node.right.right) >= height(node.right.left)) // outside case
                    node = rotateLeft(node);
                else // inside case
                    node = doubleRotateLeft(node);
            }
            node.height = Math.max(height(node.left), height(node.right)) + 1; // update height
        }
        return node;
    }

    // return the address of the minimum node in the tree
    public Elem findMin(Elem node) {
        if (node == null)
            return node;
        while (node.left != null) // move to the leftmost node
            node = node.left;
        return node;
    }

    // return the height of a node
    public int height(Elem node) {
        return node == null ? -1 : node.height;
    }

    // return size of the set elements
    public int size() {
        return _size;
    }

    // common copy code for copying a tree recursively
    public Elem copyCode(Elem node) {
        if (node != null) {
            return new Elem(node.info, copyCode(node.left), copyCode(node.right), node.height);
        }
        return null;
    }

    // print tree "laying down"
    public void printTree() {
        printTree(0, _root);
    }

    // output the structure of tree. The tree is output as "lying down"
    // in which root is the LEFT most node.
    public void printTree(int level, Elem node) {
        int i;
        if (node != null) {
            printTree(level + 1, node.right);
            for (i = 0; i < level; i++)
                System.out.print('\t');
            System.out.println(node.info + "(" + node.height + ")");
            printTree(level + 1, node.left);
        }
    }

    private class Elem {
        int info;
        Elem left;
        Elem right;
        int height;

        public Elem(int info, Elem left, Elem right, int height) {
            this.info = info;
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }
}
