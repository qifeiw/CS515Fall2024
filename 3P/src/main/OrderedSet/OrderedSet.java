package OrderedSet;

import java.util.NoSuchElementException;

/**
 * Set implemented with doubly linked list and sentinel head & tail.
 * Supports set operations and iterators.
 * Based on the Java SortedSet: https://docs.oracle.com/javase/7/docs/api/java/util/Set.html
 *                              https://docs.oracle.com/javase/7/docs/api/java/util/SortedSet.html
 * @param <E> the type being stored in the set
 */
public class OrderedSet<E extends Comparable<E>> {

    private Elem head, tail;
    private int size;

    /**
     * Constructs an empty set.
     */
    public OrderedSet() {
        this.head = new Elem();
        this.tail = new Elem();

        this.head.prev = null;
        this.head.next = tail;

        this.tail.prev = head;
        this.tail.next = null;

        this.size = 0;
    }

    /**
     * Copy constructor.
     * @param s the set to be copied
     */
    public OrderedSet(OrderedSet<E> s) {
        // TODO : Implement
        // Initialize the current set with sentinel head and tail
        this.head = new Elem();
        this.tail = new Elem();
        this.head.next = tail;
        this.tail.prev = head;
        this.size = 0;

        // Traverse the original set 's' and insert its elements into the new set
        OrderedIterator it = s.orderedIterator();
        while (it.hasNext()) {
            this.insert(it.next());
        }
        }

    /**
     * Inserts an element and keeps the list sorted.
     * @param value the element to be added
     */
    public void insert(E value) {
        // TODO : Implement
        Elem newElem = new Elem();
        newElem.info = value;

        Elem curr = head.next;
        while (curr != tail && curr.info.compareTo(value) < 0) {
            curr = curr.next;
        }

        if (curr != tail && curr.info.compareTo(value) == 0) {
            return;
        }

        newElem.prev = curr.prev;
        newElem.next = curr;
        curr.prev.next = newElem;
        curr.prev = newElem;

        size++;
    }

    /**
     * Returns whether or not a given element is found in the set.
     * @param value the element being searched for
     * @return true if element found; false otherwise
     */
    public boolean contains(E value) {
        // TODO : Implement
        Elem curr = head.next;
        while (curr != tail) {
            if (curr.info.compareTo(value) == 0) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    /**
     * Removes an element from the set if found.
     * @param value the element to be removed
     */
    public void erase(E value) {
        // TODO : Implement
        Elem curr = head.next;

        while (curr != tail) {
            if (curr.info.compareTo(value) == 0) {
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                size--;
                return;
            }
            curr = curr.next;
        }
        throw new NoSuchElementException("Element not found: " + value);
    }
    
    /**
     * Removes all the elements from the set.
     */
    public void clear() {
        // TODO : Implement
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    /**
     * Returns the size of the set.
     * @return the size of the set
     */
    public int size() {
        return this.size;
    }

    /**
     * Equals method override.
     * @param o the object being compared to
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof OrderedSet)) {
            return false;
        }
        OrderedSet<E> otherSet = (OrderedSet<E>) o;

        // TODO : Implement
        if (this.size != otherSet.size) {
            return false;
        }

        OrderedIterator thisIt = this.orderedIterator();
        OrderedIterator otherIt = otherSet.orderedIterator();

        while (thisIt.hasNext() && otherIt.hasNext()) {
            E thisE = thisIt.next();
            E otherE = otherIt.next();
            if (!thisE.equals(otherE)) {
                return false;
            }
        }
        return true;
    }

    /**
     * toString method override for printing set contents.
     * @return a string representing the sets content
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Elem curr = this.head.next;
        while (curr != this.tail) {
            sb.append(curr.info);
            sb.append(" ");
            curr = curr.next;
        }
        return sb.toString();
    }

    /**
     * Perform set union on this set and the given set.
     * (Add elements from other set to this set, if not already present).
     * @param b the other set
     */
    public void addAll(OrderedSet<E> b) {
        OrderedIterator bIterator = b.orderedIterator();

        // TODO : Implement hint: use the iterator
        while (bIterator.hasNext()) {
            E value = bIterator.next();
            this.insert(value);
        }
    }

    /**
     * Perform set intersection on this set and the given set.
     * (Retain only elements of this set that are also present in other set).
     * @param b the other set
     */
    public void retainAll(OrderedSet<E> b) {
        OrderedIterator aIterator = this.orderedIterator();

        // TODO : Implement hint: use the iterator

        while (aIterator.hasNext()) {
            E value = aIterator.next();

            if (!b.contains(value)) {
               this.erase(value);
            }
        }
    }

    /**
     * Perform set difference on this set and the given set.
     * (Remove elements from this set that are contained in the other set).
     * @param b the other set
     */
    public void removeAll(OrderedSet<E> b) {
        OrderedIterator aIterator = this.orderedIterator();

        // TODO : Implement hint: use the iterator
        while (aIterator.hasNext()) {
            E value = aIterator.next();
            if (b.contains(value)) {
                this.erase(value);
            }
        }
    }

    /**
     * Return an Iterator that starts at the first element of the set.
     * @return an Iterator for this set
     */
    public OrderedSet<E>.OrderedIterator orderedIterator() {
        return new OrderedIterator();
    }

    /**
     * Returns the first element in the set. 
     * @return the first element in the set
     */
    public E first() {
        // TODO : Implement
        if (this.size == 0) {
            throw new NoSuchElementException("Set is empty.");
        }
        return head.next.info;

    }

    /**
     * Returns the last element in the set.
     * @return the last element in the set
     */
    public E last() {
        // TODO : Implement
        if (this.size == 0) {
            throw new NoSuchElementException("Set is empty.");
        }
        return tail.prev.info;
    }

    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    /**
     * Inner class representing linked list node.
     */
    private class Elem {
        private E info;
        private Elem prev, next;

        public Elem() {
            this.prev = null;
            this.next = null;
        }
    }

    // * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    /**
     * Inner class representing OrderedIterator.
     * For more info see: https://docs.oracle.com/javase/7/docs/api/java/util/ListIterator.html
     */
    public class OrderedIterator {
        /* Iterator lives "in-between" elements */
        private Elem nextElem;
        private Elem prevElem;

        /**
         * Constructs a new Iterator for this set, starting from beginning. 
         */
        private OrderedIterator() {
            this.nextElem = head.next;
            this.prevElem = tail.prev;
        }

        /**
         * Returns the data from next element in the set.
         * @return the data from next element in the set
         */
        public E next() {
            // TODO : Implement
            if(!hasNext()) {
                throw new NoSuchElementException();
            }

            nextElem = nextElem.next;
            return nextElem.prev.info;
        }

        /**
         * Returns the data from previous element in the set.
         * @return the data from previous element in the set
         */
        public E prev() {
            // TODO : Implement
            if (!hasPrev()) {
                throw new NoSuchElementException();
            }

            E value = prevElem.info;
            prevElem = prevElem.prev;
            return value;
        }

        /**
         * Returns a boolean representing if another element after this one.
         * @return true if more elements, false otherwise.
         */
        public boolean hasNext() {
            // TODO : Implement
            return nextElem != tail;
        }

        /**
         * Returns a boolean representing if another element before this one.
         * @return true if more elements, false otherwise
         */
        public boolean hasPrev() {
            // TODO : Implement
            return prevElem != head;
        }

                /**
        * Equals method override.
        * @param o the object being compared to
        */
        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (!(o instanceof OrderedSet<?>.OrderedIterator)) {
                return false;
            }

            OrderedIterator otherIterator = (OrderedIterator) o;
            // TODO : Implement
            return false;
        }

    }
    
}
