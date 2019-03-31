package comp3506.assn1.adts;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A three-dimensional data structure that holds items in a positional relationship to each other.
 * Each cell in the data structure can hold multiple items.
 * A bounded cube has a specified maximum size in each dimension.
 * The root of each dimension is indexed from zero.
 * 
 * Memory usage: O(n). n is the amount of position.
 * The cell will usually contain a reference to another object, 
 * but that is just a constant multiplier for the size of the array.
 * 
 * @author Jiabo Qin - 44273022.
 *
 * @param <T> The type of element held in the data structure.
 */
public class BoundedCube<T> implements Cube<T> {
	private int length;
	private int breadth;
	private int height;
	private IterableQueue<Position> airQueue;//The queue stores each position.
	private Iterator<Position> airQueueIndex;
	private Position aCell;
	
	/**
	 * store elements for an indicated position.
	 * 
	 * @author Jiabo Qin - 44273022
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
	private static class Position<T>{
		private int airSpaceXCoord;
		private int airSpaceYCoord;
		private int airSpaceAltitude;
		private IterableQueue<T> aircraftQueue = new TraversableQueue<>();
		Iterator<T> aircraftQueueIndex;
		
		/**
		 * 
		 * @param x  an indicated position x.
		 * @param y an indicated position y.
		 * @param z  an indicated position z.
		 */
		private Position(int x, int y, int z){
			airSpaceXCoord=x;
			airSpaceYCoord=y;
			airSpaceAltitude=z;
		}
		
		/**
		 * Add an element in this position.
		 * 
		 * Run-time: O(1).
		 * 
		 * @param element The element to be added at the indicated position.
		 */
		private void addElement(T element){
			if(aircraftQueue.size()==0){
				aircraftQueue.enqueue(element);
				aircraftQueueIndex = aircraftQueue.iterator();
			}else{
				aircraftQueue.enqueue(element);
			}
		}
		
		/**
		 * Return the value of x of the indicated position.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return the value of x..
		 */
		private int getAirSpaceXCoord(){
			return airSpaceXCoord;
		}
		
		/**
		 * Return the value of y of the indicated position.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return the value of x..
		 */
		private int getAirSpaceYCoord(){
			return airSpaceYCoord;
		}
		
		/**
		 * Return the value of z of the indicated position.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return the value of x..
		 */
		private int getAirSpaceAltitude(){
			return airSpaceAltitude;
		}
		
		/**
		 * Return the 'oldest' element in this position.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return the value of x..
		 */
		private T getFirst(){
			if(aircraftQueueIndex.hasNext()){
				return aircraftQueueIndex.next();
			}
			else{
				return null;
			}
		}
		
		/**
		 * Removes all elements at this position.
		 * 
		 * Run-time: O(n). n is the size of aircraftQueue.
		 */
		private void removeAll(){
			while(aircraftQueue.size() != 0){
				aircraftQueue.dequeue();
			}
		}
	}
	
	/**
	 * Run-time: O(1).
	 * 
	 * @param length  Maximum size in the 'x' dimension.
	 * @param breadth Maximum size in the 'y' dimension.
	 * @param height  Maximum size in the 'z' dimension.
	 * @throws IllegalArgumentException If provided dimension sizes are not positive.
	 */
	public BoundedCube(int length, int breadth, int height) throws IllegalArgumentException {
		airQueue = new TraversableQueue<Position>();
		if(length>0 && breadth>0 && height>0){
			this.length = length - 1;
			this.breadth = breadth - 1;
			this.height = height - 1;
		}
		else{
			throw new IllegalArgumentException("Dimension sizes cannot be negative");
		}
	}
	
	/**
	 * Add an element at a fixed position.
	 * 
	 * Run-time: O(n). n is the size of airQueue.
	 * 
	 * @param element The element to be added at the indicated position.
	 * @param x X Coordinate of the position of the element.
	 * @param y Y Coordinate of the position of the element.
	 * @param z Z Coordinate of the position of the element.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public void add(int x, int y, int z, T element) throws IndexOutOfBoundsException{
		boolean exist = true;
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		if(airQueue.size()==0){
			aCell = new Position<T>(x,y,z);
			aCell.addElement(element);
			airQueue.enqueue(aCell);
		}
		else{
			while(airQueueIndex.hasNext()){
				aCell = airQueueIndex.next();
				if(aCell.getAirSpaceXCoord() == x && aCell.getAirSpaceYCoord() == y 
						&& aCell.getAirSpaceAltitude() == z){
					aCell.addElement(element);
					exist = true;
				}
				else{
					exist = false;
				}
			}
			if(exist == false){
				aCell = new Position<T>(x,y,z);
				aCell.addElement(element);
				airQueue.enqueue(aCell);
			}
		}
		airQueueIndex = airQueue.iterator();
	}
	
	/**
	 * Return the 'oldest' element at the indicated position.
	 * 
	 * Run-time: O(n). n is the size of airQueue.
	 * 
	 * @param x X Coordinate of the position of the element.
	 * @param y Y Coordinate of the position of the element.
	 * @param z Z Coordinate of the position of the element.
	 * @return 'Oldest' element at this position or null if no elements at the indicated position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public T get(int x, int y, int z) throws IndexOutOfBoundsException{
		airQueueIndex = airQueue.iterator();
		T oldestElement = null;
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(airQueueIndex.hasNext()){
				aCell = airQueueIndex.next();
				if(aCell.getAirSpaceXCoord() == x && aCell.getAirSpaceYCoord() == y 
						&& aCell.getAirSpaceAltitude() == z){
					if(aCell.aircraftQueue.size()==0) {
						oldestElement = null;
					}
					else {
					oldestElement = (T) aCell.getFirst();
					}
				}
			}
		}
		return oldestElement;
	}
	
	/**
	 * Return all the elements at the indicated position.
	 * 
	 * Run-time: O(n). n is the size of airQueue.
	 * 
	 * @param x X Coordinate of the position of the element(s).
	 * @param y Y Coordinate of the position of the element(s).
	 * @param z Z Coordinate of the position of the element(s).
	 * @return An IterableQueue of all elements at this position or null if no elements at the indicated position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public IterableQueue<T> getAll(int x, int y, int z) throws IndexOutOfBoundsException{
		airQueueIndex = airQueue.iterator();
		IterableQueue<T> allElement = null;
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(airQueueIndex.hasNext()){
				aCell = airQueueIndex.next();
				if(aCell.getAirSpaceXCoord() == x && aCell.getAirSpaceYCoord() == y 
						&& aCell.getAirSpaceAltitude() == z){
					allElement = aCell.aircraftQueue;
				}
			}
		}
		return allElement;
	}
	
	/**
	 * Indicates whether there are more than one elements at the indicated position.
	 * 
	 * Run-time: O(n). n is the size of airQueue.
	 * 
	 * @param x X Coordinate of the position of the element(s).
	 * @param y Y Coordinate of the position of the element(s).
	 * @param z Z Coordinate of the position of the element(s).
	 * @return true if there are more than one elements at the indicated position, false otherwise.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public boolean isMultipleElementsAt(int x, int y, int z) throws IndexOutOfBoundsException{
		airQueueIndex = airQueue.iterator();
		boolean multipleElement = false;
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(airQueueIndex.hasNext()){
				aCell = airQueueIndex.next();
				if(aCell.getAirSpaceXCoord() == x && aCell.getAirSpaceYCoord() == y 
						&& aCell.getAirSpaceAltitude() == z){
					if(aCell.aircraftQueue.size()>1){
						multipleElement = true;
					}
				}
			}
		}
		return multipleElement;
	}
	
	/**
	 * Removes the specified element at the indicated position.
	 * 
	 * Run-time: O(n). n is the size of airQueue.
	 * 
	 * @param element The element to be removed from the indicated position.
	 * @param x X Coordinate of the position.
	 * @param y Y Coordinate of the position.
	 * @param z Z Coordinate of the position.
	 * @return true if the element was removed from the indicated position, false otherwise.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public boolean remove(int x, int y, int z, T element) throws IndexOutOfBoundsException{
		IterableQueue<Object> tempQueue = new TraversableQueue<Object>(); 
		Object currentDequeue;
		int currentQueueLength = aCell.aircraftQueue.size();
		int i = 0; 
		int j = 0;
		boolean removed = false;
		
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			IterableQueue<Position> queue = (IterableQueue<Position>) getAll(x, y, z);
			while(i < currentQueueLength){
				currentDequeue = queue.dequeue();
				if (currentDequeue == element){
					removed = true;
				}
				else{
					tempQueue.enqueue(currentDequeue);
				}
				i++;
			}
			currentQueueLength = tempQueue.size();
			while(j < currentQueueLength){
				aCell.aircraftQueue.enqueue(tempQueue.dequeue());
				j++;
			}
		}
		return removed;
	}
	
	/**
	 * Removes all elements at the indicated position.
	 * 
	 * Run-time: O(n^2). n is the size of airQueue.
	 * 
	 * @param x X Coordinate of the position.
	 * @param y Y Coordinate of the position.
	 * @param z Z Coordinate of the position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public void removeAll(int x, int y, int z) throws IndexOutOfBoundsException{
		airQueueIndex = airQueue.iterator();
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(airQueueIndex.hasNext()){
				aCell = airQueueIndex.next();
				if(aCell.getAirSpaceXCoord() == x && aCell.getAirSpaceYCoord() == y 
						&& aCell.getAirSpaceAltitude() == z){
					aCell.removeAll();
				}
			}
		}
	}
	
	/**
	 * Removes all elements stored in the cube.
	 * 
	 * Run-time: O(n). n is the size of airQueue..
	 */
	public void clear(){
		while(airQueue.size() != 0){
			airQueue.dequeue();
		}
	}
}

/**
 * Design justification:
 * In the BoundedCube, there is inner class Position. It is used to stored elements for each indicated position by queue.
 * Then, each indicated position will be stored in the cube by a queue.
 * 
 * - Memory usage:
 * By this data structure, elements under the same position will be stored into an queue of that position.
 * We do not need to create new position for each of these elements.
 * This will improve memory usage efficiency.
 * 
 * An alternative way is to create a 3D array for each indicated position. 
 * Memory is allocated as soon as the array is declared, at compile time. 
 * Compared with queue, array will occupy all declared memory, even if there are no elements in that indicated position.
 * Therefore, the data structure, queue, is much more memory usage efficiency since the indicated position will be created 
 * only if the element is added.
 * 
 * The limitation of my data structure is that when all elements are removed from one position, 
 * which means that the position queue is empty. That position will still stay in the cube queue.
 * This may waste the memory address. 
 * 
 * - Run-time:
 * Because the element and objects are stored in the queue and accessed by iterator. 
 * The run-time of most of the functions is O(n).
 * 
 */