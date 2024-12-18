import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashMap;

/**
 * New tests for Java
 */
public class UnorderedMapTest {

    @Test
    void maptest0() {
        UnorderedMap<String, Integer> m = new UnorderedMap<>();
        m.put("a", 1);
        m.put("b", 2);
        m.erase("a");

        assertTrue( m.put("a", 1));
        assertTrue( m.put("c", 1));
        assertTrue( m.put("d", 1));
        assertTrue( m.put("cc", 1));
        assertTrue( m.put("dd", 1));
        assertTrue( m.put("aa", 1));
        assertTrue( m.put("ab", 1));
        assertTrue( m.put("ba", 1));
        assertTrue( m.put("bc", 1));
        assertTrue( m.put("bd", 1));
        assertTrue( m.put("bcc", 1));
        assertTrue( m.put("bdd", 1));
        assertTrue( m.put("baa", 1));
        assertTrue( m.put("bab", 1));
        assertTrue( m.put("cba", 1));
        assertTrue( m.put("cbc", 1));
        assertTrue( m.put("cbd", 1));
        assertTrue( m.put("cbcc", 1));
        assertTrue( m.put("cbdd", 1));
        assertTrue( m.put("cbaa", 1));
        assertTrue( m.put("cbab", 1));
        assertTrue( m.put("ccbab", 1));

        m.put("hahaha", 88);
        m.replace("hahaha", 888);
        m.replace("a",  88);

        assertEquals(24, m.size());
        assertEquals(101, m.tsize());

        // output all, order is not defined
        UnorderedMap<?, ?>.Iterator it = m.iterator();
        int count = 0;
        while(it.hasNext() ){
            System.out.println(it.next().key.toString());
            count++;
        }
        assertEquals(24, count);

        // testing copy constructor
        System.out.println("copy constructor >>>>>>>>>>");
        UnorderedMap<String, Integer> m2 = new UnorderedMap<>(m);
        assertEquals(m.size(), m2.size());
        assertEquals(m.tsize(), m2.tsize());

        m2.put("wild", 999);
        m2.put("wildwest", 999);

        assertEquals(m.size() + 2, m2.size());

        it = m2.iterator();
        count = 0;
        while(it.hasNext()){
            System.out.println(it.next().key.toString());
            count++;
        }
        assertEquals(26, count);
    }
}
