package comp3506.assn1.adts;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue with ability to iterate over all the elements in the queue.
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
	 * create a structure of a node .
	 * @author Jiabo Qin - 44273022
	 * 
	 *
	 * @param <T> The type of element held in the data structure.
	 */
	private static class Node<T>{
		private T element;
		private Node<T> next;
	}
	
	/**
	 * initialize the the various. 
	 * @author Jiabo Qin - 44273022
	 * 
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
	 * @param element The element to be added to the queue.
	 * @throws IllegalStateException Queue cannot accept a new element (e.g. queue space is full).
	 */
	public void enqueue(T element) throws IllegalStateException{
		if(size()==MAXSIZE){
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
	 * @return Number of elements in the queue.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * An iterator over a collection.
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
	@Override
	public Iterator<T> iterator() {
		return new QueueIterator<T>(head);
	}
	
	/**
	 * an iterator that can iterate over all the elements in the IterableQueue.
	 * @author Jiabo Qin - 44273022
	 * 
	 *
	 * @param <T> The type of element held in the data structure.
	 */
	private static class QueueIterator<T> implements Iterator<T> {
        private Node<T> currentElement;
        
        /**
    	 * @param head  the first node of the queue.
    	 */
        public QueueIterator(Node<T> head) {
        	currentElement = head;
        }

        /**
    	 * Returns true if the iteration has more elements. 
    	 * (In other words, returns true if next() would return an element rather than throwing an exception.)
    	 * 
    	 * @return true if the iteration has more elements.
    	 */
        public boolean hasNext()  {
        	return currentElement != null;
        }
        
        
        /**
    	 * Returns the next element in the iteration.
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