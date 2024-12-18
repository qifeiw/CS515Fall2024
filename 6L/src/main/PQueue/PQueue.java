package PQueue;

import java.lang.reflect.Array;

public class PQueue<T extends Comparable<T>> {
    int _size;
    T[] _array;

    // construct an empty binary heap.
    @SuppressWarnings("unchecked")
    public PQueue(Class<T> classRef, int capacity) { 
        _array = (T[]) Array.newInstance(classRef, capacity + 1);
        //TODO : Implement
        _size = 0;
    }

    // construct a binary heap from an array of element
    // assume the array size is smaller than capacity
    @SuppressWarnings("unchecked")
    public PQueue(Class<T> classRef, int capacity, T[] items){
        _array = (T[]) Array.newInstance(classRef, capacity + 1);
        //TODO: Implement
        _size = items.length;
//        for (int i = 0; i < _size; i++) {
//            _array[i+1] = items[i];
//        }
        System.arraycopy(items, 0, _array, 1, items.length);
        buildHeap();
    }


    // insert item into the priority queue; duplicates are allowed.
    public void insert(T x){
        //TODO: Implement
        if (_size >= _array.length - 1) {
            throw new IllegalArgumentException();
        }

        _array[++_size] = x;
        moveUp();
    }

    // return the smallest item in the priority queue.
    public T findMin(){
        if (_size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return _array[1]; //TODO: Implement
    }

    // remove the smallest item from the priority queue
    // Would ideally throw UnderflowException if empty,
    // but not necessary for this lab.
    public void deleteMin(){
        //TODO: Implement
        if (_size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        T minValue = _array[1];
        _array[1] = _array[_size--];
        moveDown(1);
    }

    // return queue size
    public int size() {
        return _size; //TODO: Implement
    }

    // test if the priority queue is logically empty.
    // return true if empty, false otherwise.
    public boolean isEmpty(){
        return _size == 0; //TODO: Implement
    }

    // private method to move up the last item in the heap.
    private void moveUp() {
        //TODO: Implement
        int index = _size; // Start at the last inserted element
        while (index > 1 && _array[index].compareTo(_array[index / 2]) < 0) { // Compare with parent
            swap(index, index / 2); // Swap with the parent if the current value is smaller
            index = index / 2; // Move up the tree
        }

    }


    // Establish heap-order property from an arbitrary
    // arrangement of items. Runs in linear time.
    private void buildHeap(){
        //TODO: Implement
        for (int i = _size /2; i > 0; i--) {
            moveDown(i);
        }
    }

    private void swap(int i, int j) {
        T temp = _array[i];
        _array[i] = _array[j];
        _array[j] = temp;
    }

    // private method to move down in the heap.
    // curIndex is the index at which the move down begins.
    private void moveDown(int curIndex){
        //TODO: Implement
        while (curIndex * 2 <= _size) { // While the node has at least a left child
            int childIndex = curIndex * 2; // Left child
            // Check if right child exists and is smaller than the left child
            if (childIndex < _size && _array[childIndex].compareTo(_array[childIndex + 1]) > 0) {
                childIndex++; // Choose the smaller of the two children
            }
            if (_array[curIndex].compareTo(_array[childIndex]) <= 0) {
                break; // The heap property is satisfied
            }
            swap(curIndex, childIndex); // Swap with the smaller child
            curIndex = childIndex; // Move down the tree
        }

    }
}
