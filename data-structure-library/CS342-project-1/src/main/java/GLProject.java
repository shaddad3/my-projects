/*
* CS 342: Project 1
* Name: Samuel Haddad
* NetID: shadd3
* Email: shadd3@uic.edu
* Project Description: In this project, we create 3 different data
* structures: GenericList (which is like a Linked List), GenericQueue
* (which is like a Queue), and MyHashMap (which is, you guessed it, like
* a hash map). We also create 3 different iterators, which each know how
* to iterate over their specified data structure. The GLLIterator knows
* how to iterate over our GenericList, the ReverseGLLIterator knows how
* to iterate over our GenericList in reverse, and the HMIterator, which
* knows how to iterate over our hash map. Finally, there are two test
* files, which contain test cases to all the methods defined in these classes.
*/

import java.util.ArrayList;
import java.util.Iterator;

public class GLProject {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to project 1");
		GenericQueue<Integer> queue = new GenericQueue<>(12);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		queue.add(13);
		queue.add(1);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Deleted: " + queue.delete());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Deleted: " + queue.delete());
		System.out.println("Deleted: " + queue.delete());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Deleted: " + queue.delete());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		queue.add(1);
		queue.add(2);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		queue.add(12);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Deleted: " + queue.delete());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Deleted: " + queue.delete());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Deleted: " + queue.delete());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		queue.enqueue(10);
		queue.enqueue(12);
		queue.enqueue(3);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Dequeued: " + queue.dequeue());
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Got: " + queue.get(0));
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Got: " + queue.get(1));
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Got: " + queue.get(2));
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Set: " + queue.set(0, 8));
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Set: " + queue.set(1, 12));
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Set: " + queue.set(2, 0));
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		ArrayList<Integer> dump = queue.dumpList();
		dump.forEach(System.out::println);
		System.out.println();

		queue.add(45, 123);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		queue.add(4, 1234);
		System.out.println("Length: " + queue.getLength());
		queue.print();
		System.out.println();

		System.out.println("Cozmo".hashCode());
		System.out.println("omzoC".hashCode());
		System.out.println("Cozmo".hashCode() & 9);
		System.out.println("omzoC".hashCode() & 9);
		System.out.println();

		MyHashMap<Integer> map = new MyHashMap<>("Cozmo", 10);
		System.out.println(map.isEmpty());
		System.out.println(map.contains("Cozmo"));
		System.out.println(map.get("Cozmo"));
		System.out.println("Map size: " + map.size());
		System.out.println();

		map.put("Sammy", 20);
		System.out.println(map.contains("Sammy"));
		System.out.println(map.get("Sammy"));
		System.out.println("Map size: " + map.size());
		System.out.println();

		map.put("Faris", 22);
		System.out.println(map.contains("Faris"));
		System.out.println(map.get("Faris"));
		System.out.println("Map size: " + map.size());
		System.out.println();

		System.out.println(map.contains("sammy"));
		System.out.println(map.get("sammy"));
		System.out.println("Map size: " + map.size());
		System.out.println();

		System.out.println(map.size());
		System.out.println();

		System.out.println("Replaced: " + map.replace("Cozmo", 0));
		System.out.println(map.get("Cozmo"));
		System.out.println("Map size: " + map.size());
		System.out.println();

		GenericQueue<Integer> iteratorTest = new GenericQueue<>(1);
		iteratorTest.add(2);
		iteratorTest.add(0);
		iteratorTest.print();
		System.out.println();
		Iterator<Integer> it = iteratorTest.iterator();
//		it.forEachRemaining(System.out::println);
//		System.out.println();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		System.out.println();

//		GenericQueue<Integer> descendingIteratorTest = new GenericQueue<>(4);
//		descendingIteratorTest.add(3);
//		descendingIteratorTest.add(2);
//		descendingIteratorTest.print();
//		System.out.println();
//		Iterator<Integer> it2 = descendingIteratorTest.descendingIterator();

//		it2.forEachRemaining(System.out::println);
		System.out.println();
//		while (it2.hasNext()) {
//			System.out.println(it2.next());
//		}
//		for (Integer i : descendingIteratorTest) {
//			System.out.println(i);
//		}
		System.out.println();

		MyHashMap<Integer> hashMap = new MyHashMap<>("Sammy", 20);
		System.out.println(hashMap.contains("Sammy"));
		System.out.println(hashMap.get("Sammy"));
		System.out.println(hashMap.size());
		System.out.println();




		hashMap.put("Faris", 22);
		System.out.println(hashMap.contains("Sammy"));
		System.out.println(hashMap.get("Sammy"));
		System.out.println(hashMap.size());

		System.out.println(hashMap.contains("Faris"));
		System.out.println(hashMap.get("Faris"));
		System.out.println(hashMap.size());




		hashMap.put("Radi", 29);
//		hashMap.put("Sammy", 20);
		System.out.println();
		System.out.println("Sammy".hashCode() & 9);
		System.out.println("Faris".hashCode() & 9);
		System.out.println("Radi".hashCode() & 9);
		System.out.println();
		System.out.println(hashMap.contains("Sammy"));
		System.out.println(hashMap.get("Sammy"));
		System.out.println(hashMap.size());
		System.out.println();
		hashMap.put("ex", 1);
		hashMap.put("ex2", 2);
		hashMap.put("ex3", 3);
		hashMap.put("radi", 28);
		System.out.println();
		System.out.println("All the hash indices in the map");
		System.out.println();
		System.out.println("Sammy " + ("Sammy".hashCode() & 9));
		System.out.println("Faris " + ("Faris".hashCode() & 9));
		System.out.println("Radi " + ("Radi".hashCode() & 9));
		System.out.println("ex " + ("ex".hashCode() & 9));
		System.out.println("ex2 " + ("ex2".hashCode() & 9));
		System.out.println("ex3 " + ("ex3".hashCode() & 9));
		System.out.println("radi " + ("radi".hashCode() & 9));
		System.out.println();
//		System.out.println(hashMap.get("radi"));
		System.out.println();
		Iterator<Integer> hashIt = hashMap.iterator(); //the creation of the hashMap iterator is making my map lose values, so change the way you pass the map
		System.out.println("CHECKING THE HMITERATOR");
		System.out.println();
		while (hashIt.hasNext()) { //loses some values, for example 28 and 29 are both at index 0, but do not show up
			System.out.println(hashIt.next());
		}
		System.out.println();

		System.out.println("CHECKING THAT THE HASHMAP CONTAINS ALL THE VALUES I ADD");
		System.out.println();
		System.out.println("Does the hashMap contain Sammy?");
		System.out.println(hashMap.contains("Sammy"));
		System.out.println(hashMap.get("Sammy"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("Does the hashMap contain Faris?");
		System.out.println(hashMap.contains("Faris"));
		System.out.println(hashMap.get("Faris"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("Does the hashMap contain Radi?");
		System.out.println(hashMap.contains("Radi"));
		System.out.println(hashMap.get("Radi"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("Does the hashMap contain ex?");
		System.out.println(hashMap.contains("ex"));
		System.out.println(hashMap.get("ex"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("Does the hashMap contain ex2?");
		System.out.println(hashMap.contains("ex2"));
		System.out.println(hashMap.get("ex2"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("Does the hashMap contain ex3?");
		System.out.println(hashMap.contains("ex3"));
		System.out.println(hashMap.get("ex3"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("Does the hashMap contain radi?");
		System.out.println(hashMap.contains("radi"));
		System.out.println(hashMap.get("radi"));
		System.out.println(hashMap.size());

		System.out.println();
		hashMap.put("Sammy", 100);
		System.out.println("Does the hashMap contain Sammy?");
		System.out.println(hashMap.contains("Sammy"));
		System.out.println(hashMap.get("Sammy"));
		System.out.println(hashMap.size());

		System.out.println();
		System.out.println("THE HASHMAP DOES CONTAIN ALL THE KEY-VALUE PAIRS I ADDED");

		//Changes were being reflected in the hashMap. Since the buildQueue() function was wrong
		//the hashMap's contents were being changed and messed up. Check to make sure the other
		//two iterators are not ruining the original contents.

		GenericQueue<Integer> gq = new GenericQueue<>(1);
		gq.enqueue(2);
		gq.enqueue(3);
		gq.print();
		System.out.println();

		Iterator<Integer> gqIt = gq.iterator();
//		gqIt.forEachRemaining(System.out::println);
		while (gqIt.hasNext()) {
			System.out.println(gqIt.next());
		}
		System.out.println();

		gq.print();
		System.out.println();

		Iterator<Integer> gqItDescending = gq.descendingIterator();
//		gqItDescending.forEachRemaining(System.out::println);
//		gqItDescending.next();
		while(gqItDescending.hasNext()) {
			System.out.println(gqItDescending.next());
		}

		System.out.println();

		gq.print();
		System.out.println();

	}
}
