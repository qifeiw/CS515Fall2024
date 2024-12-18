import PQueue.PQueue;

public class HuffTree {
    HuffNode _root;

    public void buildTree(char[] chs, int[] freqs, int size){
        //TODO: Implement
        PQueue<HuffNode> pq = new PQueue<>(HuffNode.class, size);

        // Step 1: Insert all characters and their frequencies as HuffNode objects into the priority queue
        for (int i = 0; i < size; i++) {
            if (freqs[i] > 0) { // Only consider characters that actually appear in the input
                pq.insert(new HuffNode(null, null, freqs[i], chs[i]));
            }
        }

        // Step 2: Build the Huffman tree by combining the two least frequent nodes
        while (pq.size() > 1) {
            // Remove the two nodes with the smallest frequency
            HuffNode left = pq.deleteMin();
            HuffNode right = pq.deleteMin();

            // Create a new internal node with these two nodes as children and the combined frequency
            HuffNode parent = new HuffNode(left, right, left.freq + right.freq, '\0'); // '\0' represents an internal node (no character)

            // Insert the new parent node back into the priority queue
            pq.insert(parent);
        }

        // Step 3: The remaining node in the priority queue is the root of the Huffman tree
        _root = pq.deleteMin();  // Set the root of the Huffman tree
        assignCodes(_root, "");
    }
    private void assignCodes(HuffNode node, String code) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            node.code = code;  // Assign the binary code to the leaf node (character)
        }
        assignCodes(node.left, code + "0");
        assignCodes(node.right, code + "1");
    }


    public String getCode(char ch){
        //TODO: Implement
        return getCodeHelper(_root, ch);
    }

    private String getCodeHelper(HuffNode node, char ch) {
        if (node == null) {
            return null;
        }
        if (node.left == null && node.right == null && node.data == ch) {
            return node.code;
        }
        String leftcode = getCodeHelper(node.left, ch);
        if (leftcode != null) {
            return leftcode;
        }
        return getCodeHelper(node.right, ch);
    }
    private class HuffNode implements Comparable<HuffNode> {
        HuffNode left;
        HuffNode right;
        int freq;
        char data;
        String code;
        public HuffNode(HuffNode left, HuffNode right, int freq, char data) {
            this.left = left;
            this.right = right;
            this.freq = freq;
            this.data = data;
        }

        @Override
        public int compareTo(HuffNode o) {
            //TODO: Implement
            return Integer.compare(this.freq, o.freq);
        }

    }
}
