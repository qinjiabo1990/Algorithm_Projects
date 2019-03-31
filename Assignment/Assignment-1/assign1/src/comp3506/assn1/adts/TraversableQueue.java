package comp3506.assn1.adts;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue with ability to iterate over all the elements in the queue.
 * 
 * Memory usage: O(n). n is the max size of queue.
 * 
 * @author Jiabo Qin - 44273022
 *
 * @param <T> Type of the elements held in the queue.
 */
public class TraversableQueue<T> implements IterableQueue<T> {
	private Node<T> head, tail;
	private int size;
	private static final int MAXSIZE=20000;
	
	/**
	 * Create a structure of a node.
	 * 
	 * @author Jiabo Qin - 44273022
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
	private static class Node<T>{
		private T element;
		private Node<T> next;
	}
	
	/**
	 * Initialize the the various. 
	 * 
	 * Run-time: O(1).
	 *
	 * @param <T> The type of element held in the data structure.
	 */
	public TraversableQueue(){
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Add a new element to the end of the queue.
	 * 
	 * Run-time: O(1).
	 * 
	 * @param element The element to be added to the queue.
	 * @throws IllegalStateException Queue cannot accept a new element (e.g. queue space is full).
	 */
	public void enqueue(T element) throws IllegalStateException{
		if(size()>MAXSIZE){
			throw new IllegalStateException("the queue space is full");
		}
		//give the element to the node
		Node<T> aNode = new Node<T>();
		aNode.element = element;
		
		if(head == null){
			head = aNode;
		}
		else{
			tail.next = aNode;
		}
		
		tail = aNode;
		size++;
	}
	
	/**
	 * Remove and return the element at the head of the queue.
	 * 
	 * Run-time: O(1).
	 * 
	 * @return Element at that was at the head of the queue.
	 * @throws IndexOutOfBoundsException Queue is empty and nothing can be dequeued.
	 */
	public T dequeue() throws IndexOutOfBoundsException{
		T removedElement = null;
		if (size()==0){
			throw new IndexOutOfBoundsException("the queueu is empty");
		}
		
		removedElement = head.element;
		head = head.next;
		size--;
		return removedElement;
	}
	
	/**
	 * Get the size of the queue.
	 * 
	 * Run-time: O(1).
	 * 
	 * @return Number of elements in the queue.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * An iterator over a collection.
	 * 
	 * Run-time: O(1).
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
	@Override
	public Iterator<T> iterator() {
		return new QueueIterator<T>(head);
	}
	
	/**
	 * An iterator that can iterate over all the elements in the IterableQueue.
	 * 
	 * @author Jiabo Qin - 44273022
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
	private static class QueueIterator<T> implements Iterator<T> {
        private Node<T> currentElement;
        
        /**
         * Run-time: O(1).
         * 
    	 * @param head  the first node of the queue.
    	 */
        public QueueIterator(Node<T> head) {
        	currentElement = head;
        }

        /**
    	 * Returns true if the iteration has more elements. 
    	 * (In other words, returns true if next() would return an element rather than throwing an exception.)
    	 * 
    	 * Run-time: O(1).
    	 * 
    	 * @return true if the iteration has more elements.
    	 */
        public boolean hasNext()  {
        	return currentElement != null;
        }
        
        
        /**
    	 * Returns the next element in the iteration.
    	 * 
    	 * Run-time: O(1).
    	 * 
    	 * @return the next element in the iteration
    	 * @throws NoSuchElementException - if the iteration has no more elements
    	 */
        public T next() {
            if (!hasNext()){ 
            	return null;
            }
            T item = currentElement.element;
            currentElement = currentElement.next; 
            return item;
        }
    }
}

/**
 * Design justification:
 * The TraversableQueue is designed based on structure of singly linked list.
 * Since the singly linked list is easily add to and remove from head of list.
 * It easily conforms to the "first-in, first-out" collection of queue.
 * 
 * - Memory usage:
 * Compared with an array of fixed size, the singly linked list provides a dynamic supply of memory for new objects 
 * at runtime but not at compile time and new elements can be stored anywhere in the memory, which means that
 * the memory usage efficiency of the singly linked list is higher.
 *  
 *  - Run-time:
 * The singly linked list uses node to store values and points next element. 
 * Hence, basically, the run-time is constant.
 * 
 */