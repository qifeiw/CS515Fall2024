import org.junit.jupiter.api.Test;
import OrderedSet.OrderedSet;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * New tests for Java set using Java iterator and set methods.
 */
public class SetTest {

    /*
     * [1] Testing the addAll (union), retainAll (intersection), removeAll
     * (difference) operations work correctly.
     */

    // * * * * * Testing that these set operations work on empty sets * * * * *
    @Test
    public void addAll_OnEmptySets_KeepsSetEmpty() {
        OrderedSet<String> testSet = new OrderedSet<String>();
        OrderedSet<String> otherSet = new OrderedSet<String>();

        assertEquals(0, testSet.size());
        assertEquals(0, otherSet.size());

        testSet.addAll(otherSet); // Union

        assertEquals(0, testSet.size());
    }

    @Test
    public void retainAll_OnEmptySets_KeepsSetEmpty() {
        OrderedSet<String> testSet = new OrderedSet<String>();
        OrderedSet<String> otherSet = new OrderedSet<String>();

        assertEquals(0, testSet.size());
        assertEquals(0, otherSet.size());

        testSet.retainAll(otherSet); // Intersection

        assertEquals(0, testSet.size());
    }

    @Test
    public void removeAll_OnEmptySets_KeepsSetEmpty() {
        OrderedSet<String> testSet = new OrderedSet<String>();
        OrderedSet<String> otherSet = new OrderedSet<String>();

        assertEquals(0, testSet.size());
        assertEquals(0, otherSet.size());

        testSet.removeAll(otherSet); // Difference

        assertEquals(0, testSet.size());
    }

    // * * * * Testing that these set operations update set size correctly * * *
    @Test
    public void addAll_OnNonEmptySets_UpdatesSizeCorrectly() {
        OrderedSet<String> testSet = new OrderedSet<String>();
        OrderedSet<String> otherSet = new OrderedSet<String>();

        testSet.insert("zebra");
        testSet.insert("giraffe");
        testSet.insert("capybara");
        testSet.insert("elephant");
        testSet.insert("penguin");
        testSet.insert("lion");
        assertEquals(6, testSet.size());

        otherSet.insert("pinao");
        otherSet.insert("guitar");
        otherSet.insert("flute");
        otherSet.insert("clarinet");
        assertEquals(4, otherSet.size());

        testSet.addAll(otherSet); // Union

        assertEquals(10, testSet.size());
    }

    @Test
    public void retainAll_OnNonEmptySets_UpdatesSizeCorrectly() {
        OrderedSet<String> testSet = new OrderedSet<String>();
        OrderedSet<String> otherSet = new OrderedSet<String>();

        testSet.insert("red");
        testSet.insert("orange");
        testSet.insert("green");
        testSet.insert("blue");
        testSet.insert("purple");
        testSet.insert("brown");
        testSet.insert("yellow");
        assertEquals(7, testSet.size());

        otherSet.insert("red");
        otherSet.insert("blue");
        otherSet.insert("yellow");
        otherSet.insert("black");
        otherSet.insert("white");
        assertEquals(5, otherSet.size());

        testSet.retainAll(otherSet); // Intersection

        assertEquals(3, testSet.size());
    }

    @Test
    public void removeAll_OnNonEmptySets_UpdatesSizeCorrectly() {
        OrderedSet<String> testSet = new OrderedSet<String>();
        OrderedSet<String> otherSet = new OrderedSet<String>();

        testSet.insert("red");
        testSet.insert("orange");
        testSet.insert("green");
        testSet.insert("blue");
        testSet.insert("purple");
        testSet.insert("brown");
        testSet.insert("yellow");
        assertEquals(7, testSet.size());

        otherSet.insert("red");
        otherSet.insert("blue");
        otherSet.insert("yellow");
        otherSet.insert("black");
        otherSet.insert("white");
        assertEquals(5, otherSet.size());

        testSet.removeAll(otherSet); // Difference

        assertEquals(4, testSet.size());
    }

    // * * * Testing that these set operations update set contents correctly * *
    @Test
    public void addAll_OnNonEmptySets_UpdatesSetContentsCorrectly() {
        OrderedSet<Character> testSet = new OrderedSet<Character>();
        OrderedSet<Character> otherSet = new OrderedSet<Character>();

        // Split the alphabet between both sets
        char c = 'a';
        for (int i = 0; i < 26; i++) {
            if (c % 2 == 0) {
                testSet.insert(c);
            } else {
                otherSet.insert(c);
            }
            c++;
        }

        testSet.addAll(otherSet); // Union them together

        // Verify set contents
        for (c = 'a'; c <= 'z'; c++) {
            assertTrue(testSet.contains(c));
        }
    }

    @Test
    public void retainAll_OnNonEmptySets_UpdatesSetContentsCorrectly() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();
        OrderedSet<Character> vowels = new OrderedSet<Character>();

        // Fill one set with the entire alphabet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.insert(c);
        }

        // Fill one set with just vowels
        vowels.insert('a');
        vowels.insert('e');
        vowels.insert('i');
        vowels.insert('o');
        vowels.insert('u');
        vowels.insert('y');

        alphabet.retainAll(vowels); // Perform set intersection

        // Make sure all vowels are found
        assertTrue(alphabet.contains('a'));
        assertTrue(alphabet.contains('e'));
        assertTrue(alphabet.contains('i'));
        assertTrue(alphabet.contains('o'));
        assertTrue(alphabet.contains('u'));
        assertTrue(alphabet.contains('y'));

        // Make sure all consonants are NOT found
        Character consonants[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'};
        for (int i = 0; i < consonants.length; i++) {
            assertFalse(alphabet.contains(consonants[i]));
        }

    }

    @Test
    public void removeAll_OnNonEmptySets_UpdatesSetContentsCorrectly() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();
        OrderedSet<Character> vowels = new OrderedSet<Character>();

        // Fill one set with the entire alphabet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.insert(c);
        }

        // Fill one set with just vowels
        vowels.insert('a');
        vowels.insert('e');
        vowels.insert('i');
        vowels.insert('o');
        vowels.insert('u');
        vowels.insert('y');

        alphabet.removeAll(vowels); // Perform set difference

        // Make sure all vowels are NOT found
        assertFalse(alphabet.contains('a'));
        assertFalse(alphabet.contains('e'));
        assertFalse(alphabet.contains('i'));
        assertFalse(alphabet.contains('o'));
        assertFalse(alphabet.contains('u'));
        assertFalse(alphabet.contains('y'));

        // Make sure all consonants are still found
        Character consonants[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'};
        for (int i = 0; i < consonants.length; i++) {
            assertTrue(alphabet.contains(consonants[i]));
        }
    }

    // * * * * * Testing that these set operations maintain set order * * * * *
    @Test
    public void addAll_OnNonEmptySets_MaintainsOrder() {
        OrderedSet<Character> testSet = new OrderedSet<Character>();
        OrderedSet<Character> otherSet = new OrderedSet<Character>();

        // Split the alphabet between both sets
        char c = 'a';
        for (int i = 0; i < 26; i++) {
            if (c % 2 == 0) {
                testSet.insert(c);
            } else {
                otherSet.insert(c);
            }
            c++;
        }

        testSet.addAll(otherSet); // Union them together

        OrderedSet<Character>.OrderedIterator it = testSet.orderedIterator(); // Get an iterator

        // Verify that the alphabet stayed in order after union
        for (c = 'a'; c <= 'z'; c++) {
            assertEquals(c, it.next());
        }
    }

    @Test
    public void retainAll_OnNonEmptySets_MaintainsOrder() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();
        OrderedSet<Character> vowels = new OrderedSet<Character>();

        // Fill one set with the entire alphabet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.insert(c);
        }

        // Fill one set with just vowels
        vowels.insert('a');
        vowels.insert('e');
        vowels.insert('i');
        vowels.insert('o');
        vowels.insert('u');
        vowels.insert('y');

        alphabet.retainAll(vowels); // Perform set intersection

        // Make sure that all the vowels appear in order
        char vowelArr[] = {'a', 'e', 'i', 'o', 'u'};
        OrderedSet<Character>.OrderedIterator it = alphabet.orderedIterator(); // Get an iterator

        for (int i = 0; i < vowelArr.length; i++) {
            assertEquals(vowelArr[i], it.next());
        }
    }

    @Test
    public void removeAll_OnNonEmptySets_MaintainsOrder() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();
        OrderedSet<Character> vowels = new OrderedSet<Character>();

        // Fill one set with the entire alphabet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.insert(c);
        }

        // Fill one set with just vowels
        vowels.insert('a');
        vowels.insert('e');
        vowels.insert('i');
        vowels.insert('o');
        vowels.insert('u');
        vowels.insert('y');

        alphabet.removeAll(vowels); // Perform set difference

        // Make sure all consonants appear in order
        Character consonants[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z'};
        OrderedSet<Character>.OrderedIterator it = alphabet.orderedIterator(); // Get an iterator
        for (int i = 0; i < consonants.length; i++) {
            assertEquals(consonants[i], it.next());
        }
    }

    /*
     * [2] Testing the iterator's methods (next, hasNext, prev, hasPrev) work
     * correctly.
     */

    // * * * * Testing the hasNext method (also relies on next method) * * * * *
    @Test
    public void hasNext_OnEmptySet_ReturnsFalse() {
        OrderedSet<String> testSet = new OrderedSet<String>(); // Empty
        OrderedSet<String>.OrderedIterator testIterator = testSet.orderedIterator();

        assertFalse(testIterator.hasNext());
    }

    @Test
    public void hasNext_OnNonEmptySet_ReturnsCorrectBoolean() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();

        // Add the alphabet to the set (reverse order so set must sort)
        for (char c = 'z'; c >= 'a'; c--) {
            alphabet.insert(c);
        }

        // Get an iterator
        OrderedSet<Character>.OrderedIterator it = alphabet.orderedIterator();

        // Iterate through set and check hasNext returns true until on last elem
        for (int i = 0; i < 26; i++) {
            assertTrue(it.hasNext());
            it.next();                  // Move forward
        }
        assertFalse(it.hasNext()); // Should be nothing left
    }

    // * * * * Testing hasPrevious method (also relies on prev and next) * * * *
    @Test
    public void hasPrev_OnEmptySetRetunsFalse() {
        OrderedSet<String> testSet = new OrderedSet<String>(); // Empty
        OrderedSet<String>.OrderedIterator testIterator = testSet.orderedIterator();

        assertFalse(testIterator.hasPrev());
    }

    @Test
    public void hasPrev_OnNonEmptySet_ReturnsCorrectBoolean() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();

        // Add the alphabet to the set (reverse order so set must sort)
        for (char c = 'z'; c >= 'a'; c--) {
            alphabet.insert(c);
        }

        // Get an iterator
        OrderedSet<Character>.OrderedIterator it = alphabet.orderedIterator();

        // Iterate to the last elem so we can use hasPrev
        for (int i = 0; i < 26; i++) {
            it.next();
        }

        // Iterate though set and check hasPrev returns true until on first elem
        for (int i = 0; i < 26; i++) {
            assertTrue(it.hasPrev());
            it.prev();                  // Move backwards
        }

        assertFalse(it.hasPrev()); // Should be nothing left
    }

    // * * * * * * * * * * * * Testing next method * * * * * * * * * * * * * * *
    @Test
    public void next_OnEmptySet_ThrowsException() {
        OrderedSet<Character> emptySet = new OrderedSet<Character>();
        OrderedSet<Character>.OrderedIterator it = emptySet.orderedIterator();

        assertThrows(NoSuchElementException.class, () -> {
            it.next();
        });
    }

    @Test
    public void next_OnNonEmptySet_ReadsAllElementsAndInOrder() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();

        // Fill set with entire alphabet (reverse order, so set must sort it)
        for (char c = 'z'; c >= 'a'; c--) {
            alphabet.insert(c);
        }

        // Get an iterator
        OrderedSet<Character>.OrderedIterator iterator = alphabet.orderedIterator();

        // Make sure we get the alphabet in order
        Character result = '-';
        for (char c = 'a'; c <= 'z'; c++) {
            result = iterator.next();
            assertEquals(c, result);
        }
    }

    @Test
    public void next_BeyondEndOfSet_ThrowsException() {
        OrderedSet<String> mySet = new OrderedSet<String>();

        mySet.insert("apple");
        mySet.insert("bannana");
        mySet.insert("cherry");

        OrderedSet<String>.OrderedIterator iterator = mySet.orderedIterator();

        // Iterate to end of set
        for (int i = 0; i < 3; i++) {
            iterator.next();
        }

        // Further calls to next should throw exception
        for (int i = 0; i < 10; i++) {
            assertThrows(NoSuchElementException.class, () -> {
                iterator.next();
            });
        }
    }

    // * * * * * * * * Testing prev method (relies on next) * * * * * * * * * *
    @Test
    public void prev_OnEmptySet_ThrowsException() {
        OrderedSet<Character> emptySet = new OrderedSet<Character>();
        OrderedSet<Character>.OrderedIterator it = emptySet.orderedIterator();

        assertThrows(NoSuchElementException.class, () -> {
            it.prev();
        });
    }

    @Test
    public void prev_OnNonEmptySet_ReadsAllElementsAndInOrder() {
        OrderedSet<Character> alphabet = new OrderedSet<Character>();

        // Fill set with entire alphabet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.insert(c);
        }

        // Get an iterator
        OrderedSet<Character>.OrderedIterator iterator = alphabet.orderedIterator();

        // Make sure we get the alphabet in reverse order
        Character result = '-';
        for (char c = 'z'; c >= 'a'; c--) {
            result = iterator.prev();
            assertEquals(c, result);
        }
    }

    @Test
    public void prev_BeyondBeginningOfSet__ThrowsException() {
        OrderedSet<String> mySet = new OrderedSet<String>();

        mySet.insert("apple");
        mySet.insert("bannana");
        mySet.insert("cherry");

        OrderedSet<String>.OrderedIterator iterator = mySet.orderedIterator();

        // Iterate to end of set
        for (int i = 0; i < 3; i++) {
            iterator.next();
        }

        // Iterate to beginning of set
        for (int i = 0; i < 3; i++) {
            iterator.prev();
        }

        // Further calls to prev should throw exception
        for (int i = 0; i < 10; i++) {
            assertThrows(NoSuchElementException.class, () -> {
                iterator.prev();
            });
        }
    }

    /*
     * [3] Testing the set's first and last methods work correctly.
     */

    // * * * * * * * * * * Testing first method * * * * * * * * * * * * * * * *
    @Test
    public void first_OnEmptySet_ThrowsException() {
        OrderedSet<Character> testSet = new OrderedSet<Character>();

        assertThrows(NoSuchElementException.class, () -> {
            testSet.first();
        });
    }

    @Test
    public void first_OnNonEmptySet_ReturnsLeastElement() {
        OrderedSet<String> mySet = new OrderedSet<String>();

        mySet.insert("bannana");
        mySet.insert("zebra");
        mySet.insert("apple");
        mySet.insert("cherry");

        assertEquals("apple", mySet.first());
    }

    // * * * * * * * * * * Testing last method * * * * * * * * * * * * * * * * *
    @Test
    public void last_OnEmptySet_ThrowsException() {
        OrderedSet<Character> testSet = new OrderedSet<Character>();

        assertThrows(NoSuchElementException.class, () -> {
            testSet.last();
        });
    }

    @Test
    public void last_OnNonEmptySet_ReturnsGreatestElement() {
        OrderedSet<String> mySet = new OrderedSet<String>();

        mySet.insert("bannana");
        mySet.insert("zebra");
        mySet.insert("apple");
        mySet.insert("cherry");

        assertEquals("zebra", mySet.last());
    }

    // [4] Simple stress test (idea from c++ tests)
    @Test
    public void size_WithManyElements_ReturnsCorrectValue() {
        OrderedSet<String> testSet = new OrderedSet<String>();

        for (Integer i = 1; i < 1000; i++) {
            testSet.insert(i.toString());
            assertEquals(i, testSet.size());
        }

    }

    // [5] Test equality of Iterators (relies on hasNext)
    @Test
    public void iteratorEquals_OnSameFromOppositeDirections_ReturnsTrue() {
        OrderedSet<Integer> testSet = new OrderedSet<Integer>();
        for (int i = 0; i < 10; i++) {
            testSet.insert(i);
        }

        OrderedSet<Integer>.OrderedIterator it1 = testSet.orderedIterator();
        OrderedSet<Integer>.OrderedIterator it2 = testSet.orderedIterator();

        // Bring both iterators to center
        for (int i = 0; i < 5; i++) {
            it1.next();
            it2.prev();
        }

        assertTrue(it1.equals(it2));
    }

    @Test
    public void iteratorEquals_OnDifferentLocations_ReturnsFalse() {
        OrderedSet<Integer> testSet = new OrderedSet<Integer>();
        for (int i = 0; i < 10; i++) {
            testSet.insert(i);
        }

        OrderedSet<Integer>.OrderedIterator it1 = testSet.orderedIterator();
        OrderedSet<Integer>.OrderedIterator it2 = testSet.orderedIterator();

        // Iterate each iterator fowards a different amount
        for (int i = 0; i < 3; i++) {
            it1.next();
        }
        for (int i = 0; i < 7; i++) {
            it2.next();
        }

        assertFalse(it1.equals(it2)); // Should point to different elems
    }

    @Test
    public void iteratorEquals_OnHeadVersusUnmoved_ReturnsFalse() {
        OrderedSet<Integer> testSet = new OrderedSet<Integer>();
        for (int i = 0; i < 10; i++) {
            testSet.insert(i);
        }

        OrderedSet<Integer>.OrderedIterator it1 = testSet.orderedIterator();
        OrderedSet<Integer>.OrderedIterator it2 = testSet.orderedIterator();

        // Iterate one of the iterators forwards and back
        it2.next();
        it2.prev();

        assertFalse(it1.equals(it2)); // Iterators should not be equal
        // Initially, iterator has choice to go back once (to start at set end)
        // After being moved forwards once, going before first elem hits
        // senitnel head (and no longer wraps around)
    }
}
