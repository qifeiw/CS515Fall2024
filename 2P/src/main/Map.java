import java.util.TreeMap;

public class Map<K extends Comparable<K>, V> implements Cloneable {
    private TreeMap<K,V>  internalMap;

    public Map() {
        internalMap = new TreeMap<>();
    }
    
    public Map(Map<K, V> v) {
        this.internalMap = new TreeMap<>(v.internalMap);

    }

    public boolean insert(K k, V v) {
        if (internalMap.containsKey(k)) {
            return false;
        }
        internalMap.put(k,v);
        return true;
    }

    public boolean erase(K k) {
        if (internalMap.containsKey(k)) {
            internalMap.remove(k);
            return true;
        }
        return false;
    }

    public int size() {
        return internalMap.size();
    }

    public V get(Object k) {
        return internalMap.get(k);
    }

    public Map<K, V> clone() {

        try {
            // Call the default clone method
            Map<K, V> cloned = (Map<K, V>) super.clone();

            // Create a deep copy of the internal TreeMap
            cloned.internalMap = new TreeMap<>(this.internalMap);

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }

    public V replace(K k, V v) {
        return internalMap.replace(k,v);
    }

    public TreeMap<K, V> getMap() {
        return this.internalMap;
    }

}
