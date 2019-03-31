package comp3506.assn1.adts;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class TraversableQueue<T> implements IterableQueue<T> {
	private Node<T> head, tail;
	private int size;
	public static final int MAXSIZE=20000;
	private T removedElement;
	
	private static class Node<T>{
		private T element;
		private Node<T> next;
	}
	
	public TraversableQueue(){
		head = null;
		tail = null;
		removedElement = null;
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
	
	@Override
	public Iterator<T> iterator() {
		return new QueueIterator<T>(head);
	}
	
	//need to iterate over all the elements.
	private static class QueueIterator<T> implements Iterator<T> {
        private Node<T> currentElement;

        public QueueIterator(Node<T> head) {
        	currentElement = head;
        }

        public boolean hasNext()  {
        	return currentElement != null;
        }
        
        public T next() {
            if (!hasNext()){ 
            	throw new NoSuchElementException();
            }
            T item = currentElement.element;
            currentElement = currentElement.next; 
            return item;
        }
    }
}