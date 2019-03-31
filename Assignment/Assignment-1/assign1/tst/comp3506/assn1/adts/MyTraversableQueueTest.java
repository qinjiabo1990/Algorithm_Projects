package comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class MyTraversableQueueTest {

	@Test(timeout=500)
	public void testElementDequeue() {
		IterableQueue<Object> testQueue = new TraversableQueue<>();
		Object element_one = new Object();
		Object element_two = new Object();
		Object element_three = new Object();
		testQueue.enqueue(element_one);
		testQueue.enqueue(element_two);
		testQueue.enqueue(element_three);
		assertThat("shoud remove the first element.", 
				   testQueue.dequeue(), is(equalTo(element_one)));
		assertThat("shoud remove the second element.", 
				   testQueue.dequeue(), is(equalTo(element_two)));
		
	}
	
	@Test(timeout=500)
	public void testMultipleElementQueueSize() {
		IterableQueue<Object> testQueue = new TraversableQueue<>();
		testQueue.enqueue(new Object());
		testQueue.enqueue(new Object());
		testQueue.enqueue(new Object());
		assertThat("A queue with three element does not have a size of 3.", 
				testQueue.size(), is(equalTo(3)));
	}
	
	@Test(timeout=500)
	public void testNoNext() {
		IterableQueue<Object> testQueue = new TraversableQueue<>();
		Iterator<Object> it = testQueue.iterator();
		assertThat("A empty queue should no next element", it.next(), is(equalTo(null)));
	}
	
	@Test(timeout=500)
	public void testIteratorNotHasNextOnSingleEntityQueue() {
		IterableQueue<Object> testQueue = new TraversableQueue<>();
		Iterator<Object> it = testQueue.iterator();
		assertThat("It should be no next", 
				   it.hasNext(), is(equalTo(false)));
	}
	
	@Test(timeout=500)
	public void testIteratorNext() {
		IterableQueue<Object> testQueue = new TraversableQueue<>();
		Object element_one = new Object();
		Object element_two = new Object();
		testQueue.enqueue(element_one);
		testQueue.enqueue(element_two);
		Iterator<Object> it = testQueue.iterator();
		assertThat("The next element is incorrect.", it.next(), is(equalTo(element_one)));
		assertThat("The next element is incorrect.", it.next(), is(equalTo(element_two)));
	}
}