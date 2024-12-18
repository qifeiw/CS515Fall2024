import java.util.ArrayList;
import java.util.Collections;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * CS515 Assignment 8
 * Name: Qifei Wang
 * Section: 2
 * Date: 11/20/2024
 * Collaboration Declaration: None
 */

public class UnorderedMap<KEY, T> {
    final int INIT_SIZE = 101;

    class Entry {
        KEY key;
        T data;
        Entry next;

        Entry() {
            next = null;
        }

        Entry(KEY newKey, T newData, Entry newNext) {
            key = newKey;
            data = newData;
            next = newNext;
        }
    }

    ;

    private ArrayList<Entry> table; // each table index has a linked list of Entries.
    private int tablesize;  // table size
    private int entrysize;  // total number of entries

    // Internal method to test if a positive number is prime (not efficient)
    private boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;
        if (n == 1 || n % 2 == 0)
            return false;
        for (int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }

    // Internal method to return a prime number at least as large as n.
    // Assumes n > 0.
    private int nextPrime(int n) {
        if (n % 2 == 0)
            n++;
        for (; !isPrime(n); n += 2)
            ;
        return n;
    }

    // Constructs an empty unordered_map object, containing no elements and with a entry size of zero
    // Notice the hash table with a default size 101 is constructed but empty.
    public UnorderedMap() {
        //TODO: Implement
        this.tablesize = INIT_SIZE;
        this.entrysize = 0;
        this.table = new ArrayList<>(tablesize);
        for (int i = 0; i < tablesize; i++) {
            table.add(null);
        }
    }

    // Copy constructor The object is initialized to have the same contents and properties as the UnorderedMap object rhs.
    UnorderedMap(UnorderedMap<KEY, T> rhs) {
        //TODO: Implement
        this.tablesize = rhs.tablesize;
        this.entrysize = rhs.entrysize;
        this.table = new ArrayList<>(tablesize);

        for (int i = 0; i < tablesize; i++) {
            table.add(null);
        }
        //copy all contents from rhs table
        for (int i = 0; i < rhs.table.size(); i++) {
            Entry curr = table.get(i);
            while (curr != null) {
                table.set(i, new Entry(curr.key, curr.data, table.get(i)));
                curr = curr.next;
            }
        }
    }

    // A simple forward iterator. The order of iterator is not specified.
    class Iterator {
        private Entry _cur;
        private int _index;

        private UnorderedMap<KEY, T> _map;

        public Iterator() {
        }

        public Iterator(Entry cur) {
            _cur = cur;
        }

        public boolean hasNext() {
            return (_cur != null);
        }

        public Entry next() {
            //TODO: Implement
            if (!hasNext()) {
                throw new IllegalStateException("No more elements");
            }

            Entry current = _cur;
            _cur = _cur.next;
            return current;
        }

        @Override
        public boolean equals(Object ob) {
            //TODO: Implement
            if (this == ob) {
                return true;
            }

            if (ob == null || getClass() != ob.getClass()) {
                return false;
            }

            Iterator other = (Iterator) ob;
            return _cur == other._cur && _index == other._index && _map == other._map;

        }
    }

    ;

    // Returns an iterator at the first element in the UnorderedMap container
    // Note that an UnorderedMap object makes no guarantees on which specific element is considered its first element.
    // But, in any case, the range that goes from its begin to its end covers all the elements in the container.
    Iterator iterator() {
        //TODO: Implement
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i) != null) {
                return new Iterator(table.get(i));
            }
        }
        return new Iterator(null); // Return an empty iterator if no elements exist
    }

    // applies the action on each entry in the Hash Table
    public void forEach(BiConsumer<? super KEY, ? super T> action) {
        //TODO: Implement
        for (Entry bucket : table) {
            while (bucket != null) {
                action.accept(bucket.key, bucket.data);
                bucket = bucket.next;
            }
        }
    }

    // applies the function on each entry in the Hash Table, updated the Entry's
    // data
    public void replaceAll(BiFunction<? super KEY, ? super T, ? extends T> function) {
        //TODO: Implement
        for (Entry bucket : table) {
            while (bucket != null) {
                bucket.data = function.apply(bucket.key, bucket.data);
                bucket = bucket.next;
            }
        }
    }


    // Inserts new elements in the unordered_map.
    // Each element is inserted only if its key is not equivalent to the key of any other element already in the container
    // (keys in an UnorderedMap are unique). function returns true if new element is inserted, false otherwise.
    boolean put(KEY key, T val) {
        //TODO:: Implement
        int index = Math.floorMod(key.hashCode(), tablesize);
        Entry current = table.get(index);

        // Check if the key already exists
        while (current != null) {
            if (current.key.equals(key)) {
                return false; // Key already exists, do not insert
            }
            current = current.next;
        }

        // Insert new entry at the head of the chain
        table.set(index, new Entry(key, val, table.get(index)));
        entrysize++;

        // Rehash if load factor exceeds 2
        if ((double) entrysize / tablesize > 2.0) {
            rehash();
        }
        return true;
    }

    // Removes from the UnorderedMap container a single element
    // returns true if the element is erased, false otherwise
    boolean erase(KEY key) {
        //TODO: Implement
        int index = Math.floorMod(key.hashCode(), tablesize);
        Entry current = table.get(index);
        Entry prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table.set(index, current.next); // Remove head
                } else {
                    prev.next = current.next; // Bypass the current entry
                }
                entrysize--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false; // Key not found
    }


    // Searches the container for an element with the given key and returns the associated Entry if found,
    // otherwise it returns null.
    private Entry getEntry(KEY key) {
        //TODO: Implement
        int index = Math.floorMod(key.hashCode(), tablesize);
        Entry current = table.get(index);

        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    // Searches the container for an element with the given key and returns the associated value if found,
    // otherwise it returns null.
    T get(KEY key) {
        //TODO: Implement
        Entry entry = getEntry(key);
        return (entry != null) ? entry.data : null;
    }

    // Searches the container for an element with the given key and returns true if found,
    // otherwise it returns null.
    boolean containsKey(KEY key) {
        //TODO: Implement
        return getEntry(key) != null;
    }

    // Replaces the value for the specified key only if already mapped to a value. Returns the
    // prior value in that case. If it was not already mapped, returns false.
    T replace(KEY key, T val) {
        //TODO: Implement
        Entry entry = getEntry(key);
        if (entry != null) {
            T oldVal = entry.data;
            entry.data = val;
            return oldVal;
        }
        return null;
    }

    // Returns the number of elements in the UnorderedMap container.
    int size() {
        return entrysize;
    }

    // Returns the table size; included for verifying rehash operation.
    int tsize() {
        return tablesize;
    }

    // double the hashtable when the load factor reaches 2.
    private void rehash() {
        //TODO: Implement
        int newTableSize = nextPrime(tablesize * 2);
        ArrayList<Entry> oldTable = table;

        table = new ArrayList<>(newTableSize);
        for (int i = 0; i < newTableSize; i++) {
            table.add(null);
        }

        tablesize = newTableSize;
        entrysize = 0;

        // Reinsert all entries into the new table
        for (Entry head : oldTable) {
            while (head != null) {
                put(head.key, head.data);
                head = head.next;
            }
        }
    }
}
