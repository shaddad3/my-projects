import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class GQTest {

    private GenericQueue<Integer> genericQueue;
    private static Iterator<Integer> it;
    private ArrayList<Integer> expected;

    @BeforeAll
    static void setup() { //create an iteratorQueue to use in later iterator tests
        GenericQueue<Integer> iteratorQueue = new GenericQueue<>(1);
        iteratorQueue.enqueue(100);
        iteratorQueue.enqueue(3);
        iteratorQueue.enqueue(14);
        iteratorQueue.enqueue(19);
        iteratorQueue.enqueue(50);
        iteratorQueue.enqueue(0);
        it = iteratorQueue.iterator();
    }

    @BeforeEach
    void init() { //for each test case, create a genericQueue and an ArrayList of expected values, called expected
        genericQueue = new GenericQueue<>(1);
        expected = new ArrayList<>();
        expected.add(1);
    }

    @Test
    void GQConstructorAndGetTest() { //test that the constructor initializes the first value properly using get()
        GenericQueue<Integer> queue = new GenericQueue<>(10);
        assertEquals(10, queue.get(0), "GQ constructor did not initialize properly");
    }

    @Test
    void GQConstructorAndGetHeadTest() { //test that the constructor initializes the first value properly using getHead()
        GenericQueue<Integer> queue = new GenericQueue<>(6);
        GenericList.Node<Integer> node = queue.getHead();
        assertEquals(node, queue.getHead(), "GQ constructor did not initialize properly");
    }

    @Test
    void GQConstructorAndNodeTest() { //test that the constructor initializes the first value properly using Node
        GenericQueue<Integer> queue = new GenericQueue<>(1);
        GenericList.Node<Integer> node = queue.getHead();
        assertEquals(node.data, queue.getHead().data, "GQ constructor did not initialize properly");
    }

    @Test
    void GQAddAndGetTest() { //test that add() and get work properly
        genericQueue.add(2);
        genericQueue.add(3);
        genericQueue.add(10);
        expected.add(2);
        expected.add(3);
        expected.add(10);
        for (int i = 0; i < 3; i++) {
            assertEquals(expected.get(i), genericQueue.get(i), "add and get method did not work properly");
        }
    }

    @Test
    void GQAddCodeAndGetCodeTest() { //test that addCode() and get work properly
        genericQueue.add(7, 1);
        genericQueue.add(0, 2);
        expected.add(7);
        expected.add(0);
        for (int i = 0; i < 2; i++) {
            assertEquals(expected.get(i), genericQueue.get(i), "addCode and get method did not work properly");
        }
    }

    @Test
    void GQAddAndDumpListTest() { //test that add() and dumpList() work properly
        genericQueue.add(4);
        genericQueue.add(9);
        genericQueue.add(101);
        expected.add(4);
        expected.add(9);
        expected.add(101);
        assertEquals(expected, genericQueue.dumpList(), "add and dump list methods did not work properly");
    }

    @Test
    void GQEnqueueAndDumpListTest() { //test that enqueue() and dumpList() work properly
        genericQueue.enqueue(23);
        genericQueue.enqueue(2);
        genericQueue.enqueue(100);
        expected.add(23);
        expected.add(2);
        expected.add(100);
        assertEquals(expected, genericQueue.dumpList(), "enqueue and dump list methods did not work properly");
    }

    @Test
    void GQEnqueueAndGetTest() { //test that enqueue() and get() work properly
        genericQueue.enqueue(7);
        genericQueue.enqueue(0);
        genericQueue.enqueue(23);
        genericQueue.enqueue(5);
        genericQueue.enqueue(0);
        expected.add(7);
        expected.add(0);
        expected.add(23);
        expected.add(5);
        expected.add(0);
        for (int i = 0; i < 6; i++) {
            assertEquals(expected.get(i), genericQueue.get(i), "enqueue and get method did not work properly");
        }
    }

    @Test
    void GQDeleteTest() { //test that delete() works properly
        Integer i = genericQueue.delete();
        assertEquals(1, i, "delete did not return the proper value");
    }

    @Test
    void GQDeleteEmptyTest() { //test that delete() and delete on an empty queue work properly
        Integer i = genericQueue.delete();
        assertEquals(1, i, "delete did not return the proper value");
        Integer j = genericQueue.delete();
        assertNull(j, "delete did not return null when attempting to delete from an empty queue");
    }

    @Test
    void GQDeleteAndGetTest() { //test that delete() and get() work properly
        genericQueue.enqueue(2);
        genericQueue.enqueue(3);
        expected.clear();
        expected.add(2);
        expected.add(3);
        Integer i = genericQueue.delete();
        assertEquals(1, i, "delete did not return the proper value");
        for (int index = 0; index < 2; index++) {
            assertEquals(expected.get(i), genericQueue.get(i), "delete did not remove the node from the list");
        }
    }

    @Test
    void GQDequeueTest() { //test that delete() works properly
        Integer i = genericQueue.dequeue();
        assertEquals(1, i, "dequeue did not return the proper value");
    }

    @Test
    void GQDequeueEmptyTest() { //test that dequeue() and delete on an empty queue work properly
        Integer i = genericQueue.dequeue();
        assertEquals(1, i, "dequeue did not return the proper value");
        Integer j = genericQueue.dequeue();
        assertNull(j, "dequeue did not return null when attempting to dequeue from an empty queue");
    }

    @Test
    void GQDequeueAndGetTest() { //test that dequeue() and get() work properly
        genericQueue.enqueue(56);
        genericQueue.enqueue(34);
        expected.clear();
        expected.add(56);
        expected.add(34);
        Integer i = genericQueue.dequeue();
        assertEquals(1, i, "dequeue did not return the proper value");
        for (int index = 0; index < 2; index++) {
            assertEquals(expected.get(i), genericQueue.get(i), "dequeue did not remove the node from the list");
        }
    }

    @Test
    void GQPrintTest() { //test that print() works properly
        genericQueue.enqueue(2);
        genericQueue.add(4);
        genericQueue.add(6, 0);
        genericQueue.enqueue(10);
        genericQueue.print();
    }

    @Test
    void GQDumpListTest() { //test that dumpList() works properly
        genericQueue.enqueue(2);
        genericQueue.add(4);
        genericQueue.add(6, 0);
        genericQueue.enqueue(10);
        expected.add(2);
        expected.add(4);
        expected.add(6);
        expected.add(10);
        assertEquals(expected, genericQueue.dumpList(), "dump list did not return an equivalent list");
    }

    @Test
    void GQGetHeadTest() { //test that getHead() works properly
        GenericList.Node<Integer> test = new GenericList.Node<>();
        test.data = 1;
        test.next = null;
        assertEquals(test.data, genericQueue.getHead().data, "getHead did not have the correct data value");
        assertEquals(test.next, genericQueue.getHead().next, "getHead did not have the correct next value");
    }

    @Test
    void GQSetHeadAndGetHeadTest() { //test that setHead() and getHead() work properly
        GenericList.Node<Integer> test = new GenericList.Node<>();
        test.data = 100;
        test.code = 100;
        test.next = null;
        genericQueue.setHead(test);
        assertEquals(test.data, genericQueue.getHead().data, "setHead did not set the correct data value");
        assertEquals(test.next, genericQueue.getHead().next, "setHead did not set the correct next value");
        assertEquals(test.code, genericQueue.getHead().code, "setHead did not set the correct code value");
    }

    @Test
    void GQGetLengthTest() { //test that getLength() works properly
        assertEquals(1, genericQueue.getLength(), "getLength did not have the correct length");
    }

    @Test
    void GQGetLengthTest2() { //test that getLength() works properly
        genericQueue.enqueue(3);
        genericQueue.add(2);
        genericQueue.add(7, 10);
        assertEquals(4, genericQueue.getLength(), "getLength did not have the correct length");
    }

    @Test
    void GQSetLengthAndGetLengthTest() { //test that setLength and getLength() work properly
        genericQueue.setLength(10);
        assertEquals(10, genericQueue.getLength(), "setLength did not set the correct length");
    }

    @Test
    void GQSetTest() { //test that set() works properly
        assertEquals(1, genericQueue.set(0, 100), "set did not return the old value");
    }

    @Test
    void GQSetAndGetTest() { //test that set() and get() work properly
        assertEquals(1, genericQueue.set(0, 100), "set did not return the old value");
        assertEquals(100, genericQueue.get(0), "get did not return the new value placed by set");
    }

    @Test
    void GQSetAndGetTest2() { //test that set() and get() work properly
        genericQueue.enqueue(10);
        genericQueue.add(13);
        assertEquals(13, genericQueue.set(2, 0), "set did not return the old value");
        assertEquals(0, genericQueue.get(2), "get did not return the new value placed by set");
    }

    @Test
    void GQIteratorTest() { //test that iterator() works properly with while loop
        int i = 1;
        genericQueue.enqueue(2);
        genericQueue.enqueue(3);
        genericQueue.enqueue(4);
        Iterator<Integer> it = genericQueue.iterator();
        while (it.hasNext()) {
            assertEquals(i, it.next(), "next does not have the correct return value");
            i++;
        }
    }

    @Test
    void GQIteratorForEachLoopTest() { //test that iterator() works properly with for each loop
        int index = 0;
        genericQueue.enqueue(5);
        genericQueue.enqueue(12);
        genericQueue.enqueue(0);
        expected.add(5);
        expected.add(12);
        expected.add(0);
        for (Integer i : genericQueue) {
            assertEquals(expected.get(index), i, "iterator from forEach loop did not work correctly");
            index++;
        }
    }

    @Test
    void GQIteratorForEachMethodTest() { //test that iterator() works properly with for each method
        genericQueue.enqueue(10);
        genericQueue.enqueue(100);
        genericQueue.enqueue(1000);

        expected.add(10);
        expected.add(100);
        expected.add(1000);

        ArrayList<Integer> forEachMethod = new ArrayList<>();

        genericQueue.forEach(item->
                forEachMethod.add(item)
        );
        assertArrayEquals(expected.toArray(), forEachMethod.toArray(), "iterator from forEach method did not work properly");
        assertArrayEquals(expected.toArray(), forEachMethod.toArray(), "iterator from forEach method did not work properly");
    }

    @ParameterizedTest
    @ValueSource (ints = {1, 100, 3, 14, 19, 50, 0})
    void GQIteratorParameterizedTest(int val) { //test that iterator() works properly with parameterized test
        assertEquals(val, it.next(), "next does not have the correct return value");
    }

    @Test
    void GQDescendingIteratorTest() { //test that descendingIterator() works properly with while loop
        genericQueue.enqueue(2);
        genericQueue.enqueue(3);
        genericQueue.enqueue(4);
        int i = genericQueue.getLength();
        Iterator<Integer> it = genericQueue.descendingIterator();
        while (it.hasNext()) {
            assertEquals(i, it.next(), "next does not have the correct return value");
            i--;
        }
    }


    @Test
    void GQDescendingIteratorForEachLoopTest() { //test that descendingIterator() works properly with for each loop
        int index = 0;
        genericQueue.enqueue(5);
        genericQueue.enqueue(12);
        genericQueue.enqueue(0);
        expected.clear();
        expected.add(0);
        expected.add(12);
        expected.add(5);
        expected.add(1);
        Iterator<Integer> it = genericQueue.descendingIterator();
        GenericQueue<Integer> descended = new GenericQueue<>(1);
        descended.dequeue();
        it.forEachRemaining(item->
                descended.add(item)
        );
        for (Integer i : descended) {
            assertEquals(expected.get(index), i, "descendingIterator from forEach loop did not work correctly");
            index++;
        }
    }

    @Test
    void GQDescendingIteratorForEachMethodTest() { //test that descendingIterator() works properly with for each method
        int index = 0;
        genericQueue.enqueue(10);
        genericQueue.enqueue(119);
        genericQueue.enqueue(7);
        expected.clear();
        expected.add(7);
        expected.add(119);
        expected.add(10);
        expected.add(1);
        Iterator<Integer> it = genericQueue.descendingIterator();
        ArrayList<Integer> descended = new ArrayList<>();
        it.forEachRemaining(item->
                descended.add(item)
        );
        assertArrayEquals(expected.toArray(), descended.toArray(), "descendingIterator from forEach method did not work properly");
        assertArrayEquals(expected.toArray(), descended.toArray(), "descendingIterator from forEach method did not work properly");
    }

}
