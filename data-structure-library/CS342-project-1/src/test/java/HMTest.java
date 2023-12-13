import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

public class HMTest {
    private MyHashMap<Integer> map;

    @BeforeEach
    void init() { //for each test, create a map with one key-value pair in it
        map = new MyHashMap<>("one", 1);
    }

    @Test
    void HMConstructorAndContainsTest() { //test that the constructor and contains() work properly
        map = new MyHashMap<>("one", 1);
        assertTrue(map.contains("one"), "HM constructor does not initialize properly");
    }

    @Test
    void HMConstructorAndGetTest() { //test that the constructor and get() work properly
        map = new MyHashMap<>("one", 1);
        assertEquals(1, map.get("one"), "HM constructor does not initialize properly");
    }

    @Test
    void HMConstructorAndIsEmptyTest() { //test that the constructor and isEmpty() work properly
        map = new MyHashMap<>("one", 1);
        assertFalse(map.isEmpty(), "HM constructor does not initialize properly");
    }

    @Test
    void HMConstructorAndSizeTest() { //test that the constructor and size() work properly
        map = new MyHashMap<>("one", 1);
        assertEquals(1, map.size(), "HM constructor does not initialize properly");
    }

    @Test
    void HMPutAndContainsTest() { //test that put() and contains() work properly
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        assertTrue(map.contains("one"));
        assertTrue(map.contains("two"));
        assertTrue(map.contains("three"));
        assertTrue(map.contains("four"));
    }

    @Test
    void HMPutAndGetTest() { //test that put() and get() work properly
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));
        assertEquals(4, map.get("four"));
    }

    @Test
    void HMPutAndGetAndSizeTest() { //test that put() and get() and size() work properly
        map.put("ex", 10);
        map.put("something", 6);
        map.put("idk", 13);
        assertEquals(1, map.get("one"));
        assertEquals(10, map.get("ex"));
        assertEquals(6, map.get("something"));
        assertEquals(13, map.get("idk"));
        assertEquals(4, map.size());
    }

    @Test
    void HMPutAndGetAndIsEmptyTest() { //test that put() and get() and isEmpty() work properly
        map.put("stuff", 0);
        map.put("another", 10);
        map.put("one more", 100);
        assertEquals(1, map.get("one"));
        assertEquals(0, map.get("stuff"));
        assertEquals(10, map.get("another"));
        assertEquals(100, map.get("one more"));
        assertFalse(map.isEmpty());
    }

    @Test
    void HMReplaceTest() { //test that replace() works properly
        assertEquals(1, map.replace("one", 100), "replace did not return old value");

    }

    @Test
    void HMReplaceAndGetTest() { //test that replace() and get() work properly
        assertEquals(1, map.replace("one", 100), "replace did not return old value");
        assertEquals(100, map.get("one"), "get did not return new value from replace");
    }

    @Test
    void HMReplaceNothingTest() { //test that replace() works properly with no key to replace
        map.put("something", 100);
        assertEquals(100, map.replace("something", 0), "replace did not return old value");
        assertNull(map.replace("not a key", 0), "replace did not return null when key was not found");
    }

    @Test
    void HMIteratorTest() { //test that iterator() works properly with while loop
        int i = 0;
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        Iterator<Integer> it = map.iterator();
        while (it.hasNext()) {
            assertTrue(expected.contains(it.next()), "HM iterator does not iterate correctly");
            i++;
        }
    }

    @Test
    void HMIteratorForEachLoopTest() { //test that iterator() works properly with for each loop
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        for (Integer i : map) {
            assertTrue(expected.contains(i), "HM iterator does not iterate correctly");
        }
    }

    @Test
    void HMIteratorForEachMethodTest() { //test that iterator() works properly with for each method
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        map.forEach(item->
                assertTrue(expected.contains(item), "HM iterator does not iterate correctly"));
    }

}
