import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class MapTest {

    /*
       The constructor should create a map with zero size
     */
    @Test
    void constructorCreatesZeroSizeMap() {
        Map<String, String> map = new Map<>();
        assertEquals(0, map.size());

    }

    /*
       Inserting into an empty map should return true and result in a size of one
     */
    @Test
    void insertWhileEmptyReturnsTrueAndMapSizeIsOne() {
        Map<String, String> map = new Map<>();
        assertTrue(map.insert("key1", "value1"));
        assertEquals(1,map.size());
    }

    /*
      using the copy constructor results in twp maps of the same correct size
     */
    @Test
    void copyConstructorMakesCorrectSize() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        map.insert("key2", "value2");

        Map<String, String> copyOfMap = new Map<>(map);
        assertEquals(map.size(), copyOfMap.size());
    }

    /*
      Using the copy constructor with an existing map makes its own copy such
      that if the value assiciated with a key is changed in the copy it does
      not change the associated value in the original, and inserts/erases on
      either won't affect the other.
     */
    @Test
    void copyConstructorMakesSeparateCopy()
    {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        map.insert("key2", "value2");

        Map<String, String> copy = new Map<>(map);
        copy.replace("key1", "newValue1");

        assertNotEquals(map.get("key1"), copy.get("key1"));
        copy.insert("key3", "value3");

        assertEquals(2, map.size());
        assertEquals(3, copy.size());
    }

    /*
      Inserting a key that already exists should fail and not change the map size
     */
    @Test
    void insertOfSameKeyReturnsFalseAndMapSizeRemainsSame() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        assertFalse(map.insert("key1", "value2"));
        assertEquals(1, map.size());
    }


    /*
      Accessing an existing item using get should return correct value and not
      not change the map size
     */
    @Test
    void getOfExistingKeyReturnsProperValueAndSizeIsSame() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        assertEquals("value1", map.get("key1"));
        assertEquals(1, map.size());
    }

    /*
      Using get on empty map gives correct result and and insert in an empty
      map should add the item with an appropriate value and increase the map
      size to one
     */
    @Test
    void getThenInsertOnEmptyMapProperlySetsValueAndSizeIsOne() {
        Map<String, String> map = new Map<>();
        assertNull(map.get("key1"));

        map.insert("key1", "value1");
        assertEquals(1, map.size());
        assertEquals("value1", map.get("key1"));
    }

    /*
      get on missing key produces correct result and insert
      properly sets value and increments size
     */
    @Test
    void getThenInsertOnMissingKeyProperlySetsValueAndIncrementsSize() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        assertNull(map.get("key2"));
        map.insert("key2", "value2");
        assertEquals(2, map.size());
        assertEquals("value2", map.get("key2"));
    }

    /*
      Erasing a key that exists i nthe map succeeds and decrements the map size
     */
    @Test
    void eraseOfExistingKeyReturnsTrueAndDecrementsSize() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        assertTrue(map.erase("key1"));
        assertEquals(0, map.size());
    }

    /*
      Erase on an empty map fails and keeps the map size at zero
     */
    @Test
    void eraseOnEmptyMapReturnsFalseAndSizeRemainsZero() {
        Map<String, String> map = new Map<>();
        assertFalse(map.erase("key1"));
        assertEquals(0, map.size());
    }

    /*
      Erase of a non-existant key fails and does not change the map size
     */
    @Test
    void eraseOfMissingKeyReturnsFalseAndSizeRemainsSame() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        assertFalse(map.erase("key2"));
        assertEquals(1, map.size());
    }


    /*
      Using the clone method results in two maps of the same, correct size
     */
    @Test
    @SuppressWarnings("unchecked")
    void cloneMakesCorrectSize() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");

        Map<String, String> cloneMap = (Map<String, String>) map.clone();
        assertEquals(map.size(), cloneMap.size());
    }

    /*
      Using the clone method with an existing map makes its own copy such that
      if the value associated with a key is changed in the copy it does not
      change the associated value in the original, and inserts/erases on either
      won't affect the other.
     */
    @Test
    @SuppressWarnings("unchecked")
    void cloneMakesSeparateCopy() {
        Map<String, String> map = new Map<>();
        map.insert("key1", "value1");
        Map<String, String> mapClone = (Map<String, String>) map.clone();
        mapClone.replace("key1", "newValue");
        assertNotEquals(map.get("key1"), mapClone.get("key1"));
    }

    /*
      Using the clone method with an existing map with over 1000 items
      makes a copt that has the correct keys/values at a couple places
      deep within the map (values that aren't at either end of the range
      of keys used
     */
    @Test
    @SuppressWarnings("unchecked")
    void cloneWithManyItemsHasCorrectValues() {
        Map<Integer, String> map = new Map<>();
        for (int i = 1; i <= 1000; i++) {
            map.insert(i, "value" + i);
        }
        Map<Integer, String> mapClone = (Map<Integer, String>) map.clone();
        assertEquals("value500", mapClone.get(500));
        assertEquals("value999", mapClone.get(999));
        assertEquals("value1", mapClone.get(1));
    }
}
