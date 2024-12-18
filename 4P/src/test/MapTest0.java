import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;


public class MapTest0 {

    @Test
    void testMap0() {

        Map m1 = new Map();
        assertTrue(m1.insert("a", "boy"));
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        assertTrue( m1.insert("b", "breeze"));
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        assertTrue( m1.insert("r", "fire"));
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        assertTrue( m1.insert("d", "rose"));
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        assertTrue( m1.insert("c", "ice"));
        System.out.println(m1);
        assertFalse( m1.insert("c", "car"));


        assertTrue(m1.insert("new", "element"));
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        assertTrue(m1.insert("cool", "ice"));
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        m1.erase("cool");
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");

        Map m2 = new Map(m1);
        m2.erase("a");
        System.out.println(m1);
        System.out.println("+++++++++++++++++++++++++++++\n");
        System.out.println(m2);;

    }

    @Test
    public void testReplaceExistingKey() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");
        map.insert("cherry", "dark red");
        // Test replacing the value for an existing key
        String oldValue = map.replace("apple", "green");
        assertEquals("red", oldValue);  // Old value should be "red"
        assertEquals("green", map.get("apple"));  // New value should be "green"
    }

    @Test
    public void testReplaceNonExistingKey() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");
        map.insert("cherry", "dark red");
        // Test replacing the value for a non-existing key
        String result = map.replace("orange", "orange");
        assertNull(result);  // Should return null since "orange" doesn't exist
    }

    @Test
    public void testReplaceMultipleTimes() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");
        map.insert("cherry", "dark red");
        // Test replacing the value for the same key multiple times
        map.replace("banana", "light yellow");
        assertEquals("light yellow", map.get("banana"));

        String oldValue = map.replace("banana", "brown");
        assertEquals("light yellow", oldValue);  // Old value should be "light yellow"
        assertEquals("brown", map.get("banana"));  // New value should be "brown"
    }

    @Test
    public void testReplaceOnEmptyMap() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");
        map.insert("cherry", "dark red");
        // Test replacing a value when the map is empty
        Map emptyMap = new Map();
        assertNull(emptyMap.replace("anyKey", "anyValue"));  // Should return null
    }

    @Test
    public void testReplaceNoChangesForMissingKey() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");
        map.insert("cherry", "dark red");
        // Ensure map structure stays the same if replace is called on a non-existent key
        int originalSize = map.size();
        String result = map.replace("nonExistentKey", "newValue");
        assertNull(result);  // Should return null
        assertEquals(originalSize, map.size());  // Size should remain the same
    }

    @Test
    public void testClear() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");
        map.insert("cherry", "dark red");

        assertFalse(map.isEmpty()); // Ensure map is not empty before clearing

        map.clear(); // Clear the map

        assertTrue(map.isEmpty()); // After clearing, the map should be empty
        assertEquals(0, map.size()); // The size of the map should be 0
        assertNull(map.get("apple")); // Attempting to get any key should return null
        assertNull(map.get("banana"));
        assertNull(map.get("cherry"));
    }

    @Test
    public void testContainsKey() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");

        assertTrue(map.containsKey("apple"));  // Key exists in the map
        assertTrue(map.containsKey("banana")); // Key exists in the map
        assertFalse(map.containsKey("cherry")); // Key does not exist in the map
        assertFalse(map.containsKey("orange")); // Key does not exist in the map
    }

    @Test
    public void testGetOrDefault() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");

        assertEquals("red", map.getorDefault("apple", "default"));   // Key exists, should return "red"
        assertEquals("yellow", map.getorDefault("banana", "default")); // Key exists, should return "yellow"
        assertEquals("default", map.getorDefault("cherry", "default")); // Key doesn't exist, should return default
        assertEquals("not found", map.getorDefault("orange", "not found")); // Key doesn't exist, return "not found"
    }

    @Test
    public void testIsEmpty() {
        Map map = new Map();

        assertTrue(map.isEmpty()); // A new map should be empty

        map.insert("apple", "red");
        assertFalse(map.isEmpty()); // After insertion, map should not be empty

        map.erase("apple");
        assertTrue(map.isEmpty()); // After erasing the only key, map should be empty again

        map.insert("banana", "yellow");
        map.clear();
        assertTrue(map.isEmpty()); // After calling clear, the map should be empty
    }

    @Test
    public void testInsertIfAbsent_InsertNewKey() {
        Map map = new Map();
        map.insert("apple", "red");

        // Inserting a new key "banana" should succeed, and return null since it was absent.
        assertNull(map.insertIfAbsent("banana", "yellow"));

        // Check if the new key-value pair was correctly inserted.
        assertEquals("yellow", map.get("banana"));
    }

    @Test
    public void testInsertIfAbsent_ExistingKey() {
        Map map = new Map();
        map.insert("apple", "red");
        map.insert("banana", "yellow");

        // Inserting an existing key "banana" should return the current value, "yellow".
        assertEquals("yellow", map.insertIfAbsent("banana", "green"));

        // Ensure the value for "banana" was not modified.
        assertEquals("yellow", map.get("banana"));
    }

    @Test
    public void testInsertIfAbsent_EmptyMap() {
        Map map = new Map();

        // Inserting a new key in an empty map should succeed.
        assertNull(map.insertIfAbsent("apple", "red"));

        // Check if the new key-value pair was inserted correctly.
        assertEquals("red", map.get("apple"));
    }

    @Test
    public void testInsertIfAbsent_MultipleInsertions() {
        Map map = new Map();
        map.insert("apple", "red");

        // Inserting multiple new keys
        assertNull(map.insertIfAbsent("banana", "yellow"));
        assertNull(map.insertIfAbsent("cherry", "dark red"));

        // Ensure all keys are inserted with the correct values
        assertEquals("red", map.get("apple"));
        assertEquals("yellow", map.get("banana"));
        assertEquals("dark red", map.get("cherry"));
    }

    @Test
    public void testInsertIfAbsent_NoChangeOnDuplicate() {
        Map map = new Map();
        map.insert("apple", "red");

        // Attempt to insert the same key with a different value should return the existing value
        assertEquals("red", map.insertIfAbsent("apple", "green"));

        // Ensure the value for "apple" has not changed
        assertEquals("red", map.get("apple"));
    }

    public void testCloneMethod() {
        // Create and populate the original map
        Map originalMap = new Map();
        originalMap.insert("apple", "fruit");
        originalMap.insert("carrot", "vegetable");
        originalMap.insert("banana", "fruit");

        // Clone the map
        Map clonedMap = (Map) originalMap.clone();

        // Assert that both maps have the same size
        assert originalMap.size() == clonedMap.size() : "Size mismatch after cloning";

        // Assert that both maps contain the same key-value pairs
        assert "fruit".equals(clonedMap.get("apple")) : "Cloned map does not have 'apple' key with correct value";
        assert "vegetable".equals(clonedMap.get("carrot")) : "Cloned map does not have 'carrot' key with correct value";
        assert "fruit".equals(clonedMap.get("banana")) : "Cloned map does not have 'banana' key with correct value";

        // Modify the original map and check that the cloned map remains unchanged
        originalMap.erase("banana");
        assert originalMap.size() != clonedMap.size() : "Cloned map should not be affected by modification to the original map";

        // Verify the original map no longer has "banana" but the cloned map still does
        assert originalMap.get("banana") == null : "Original map should not contain 'banana' after erasing";
        assert "fruit".equals(clonedMap.get("banana")) : "Cloned map should still contain 'banana' after modification to original";

        System.out.println("testCloneMethod passed.");
    }
}


