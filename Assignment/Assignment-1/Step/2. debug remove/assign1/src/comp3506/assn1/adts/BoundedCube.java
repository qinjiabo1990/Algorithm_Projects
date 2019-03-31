package comp3506.assn1.adts;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A three-dimensional data structure that holds items in a positional relationship to each other.
 * Each cell in the data structure can hold multiple items.
 * A bounded cube has a specified maximum size in each dimension.
 * The root of each dimension is indexed from zero.
 * 
 * @author 
 *
 * @param <T> The type of element held in the data structure.
 */
public class BoundedCube<T> implements Cube<T> {
	
	private int length;
	private int breadth;
	private int height;
	
	private Element<T> aElement;
	private IterableQueue<Element> airQueue;//bigQueue
	private Iterator<Element> outer;
	private Element innerIndex = null;
	
	/**
	 * store the element for indicated position
	 * @author Qin
	 *
	 * @param <T> The type of element held in the data structure.
	 */
	private static class Element<T>{
		private int airSpaceXCoord;
		private int airSpaceYCoord;
		private int airSpaceAltitude;
		private IterableQueue<T> aircraftQueue = new TraversableQueue<>();
		Iterator<T> inner;
		
		private Element(int x, int y, int z){
			airSpaceXCoord=x;
			airSpaceYCoord=y;
			airSpaceAltitude=z;
		}
		private void addElement(T element){
			aircraftQueue.enqueue(element);
			inner = aircraftQueue.iterator();
		}
		
		private int getAirSpaceXCoord(){
			return airSpaceXCoord;
		}
		private int getAirSpaceYCoord(){
			return airSpaceYCoord;
		}
		private int getAirSpaceAltitude(){
			return airSpaceAltitude;
		}
		private T getFirst(){
			if(inner.hasNext()){
				return inner.next();
			}
			else{
				throw new NoSuchElementException();
			}
		}
		private void removeAll(){
			while(aircraftQueue.size() != 0){
				aircraftQueue.dequeue();
			}
		}
	}
	
	/**
	 * 
	 * @param length  Maximum size in the 'x' dimension.
	 * @param breadth Maximum size in the 'y' dimension.
	 * @param height  Maximum size in the 'z' dimension.
	 * @throws IllegalArgumentException If provided dimension sizes are not positive.
	 */
	public BoundedCube(int length, int breadth, int height) throws IllegalArgumentException {
		airQueue = new TraversableQueue<Element>();
		if(length>0 && breadth>0 && height>0){
			this.length = length;
			this.breadth = breadth;
			this.height = height;
		}
		else{
			throw new IllegalArgumentException("dimension sizes must be positive");
		}
	}
	
	/**
	 * Add an element at a fixed position.
	 * 
	 * @param element The element to be added at the indicated position.
	 * @param x X Coordinate of the position of the element.
	 * @param y Y Coordinate of the position of the element.
	 * @param z Z Coordinate of the position of the element.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public void add(int x, int y, int z, T element) throws IndexOutOfBoundsException{
		int i = 0;
		boolean exist = true;
		if((x > length || x < 0) && (y > breadth || y < 0) && (z > height || z < 0)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		if(airQueue.size()==0){
			aElement = new Element<T>(x,y,z);
			aElement.addElement(element);
			airQueue.enqueue(aElement);
		}
		else{
			while(i < airQueue.size()){//2
				if(outer.hasNext()){
					innerIndex = outer.next();
				}
				if(innerIndex.getAirSpaceXCoord() == x && innerIndex.getAirSpaceYCoord() == y 
						&& innerIndex.getAirSpaceAltitude() == z){
					innerIndex.addElement(element);
					exist = true;
				}
				else{
					if(outer.hasNext()){
						innerIndex = outer.next();
						if(innerIndex.getAirSpaceXCoord() == x && innerIndex.getAirSpaceYCoord() == y 
								&& innerIndex.getAirSpaceAltitude() == z){
							innerIndex.addElement(element);
							exist = true;
							break;
						}
					}
					exist = false;
				}
				i++;
			}
			if(exist == false){
				aElement = new Element<T>(x,y,z);
				aElement.addElement(element);
				airQueue.enqueue(aElement);
			}
		}
		outer = airQueue.iterator();
	}
	
	/**
	 * Return the 'oldest' element at the indicated position.
	 * 
	 * @param x X Coordinate of the position of the element.
	 * @param y Y Coordinate of the position of the element.
	 * @param z Z Coordinate of the position of the element.
	 * @return 'Oldest' element at this position or null if no elements at the indicated position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public T get(int x, int y, int z) throws IndexOutOfBoundsException{
		T oldestElement = null;
		int i = 0;
		if((x < 0 || x > length) && (y < 0 || y > breadth) && (z < 0 && z > height)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(i < airQueue.size()){
				innerIndex = outer.next();
				if(innerIndex.getAirSpaceXCoord() == x && innerIndex.getAirSpaceYCoord() == y 
						&& innerIndex.getAirSpaceAltitude() == z){
					oldestElement = (T) innerIndex.getFirst();
				}
				i++;
			}
		}
		return oldestElement;
	}
	
	/**
	 * Return all the elements at the indicated position.
	 * 
	 * @param x X Coordinate of the position of the element(s).
	 * @param y Y Coordinate of the position of the element(s).
	 * @param z Z Coordinate of the position of the element(s).
	 * @return An IterableQueue of all elements at this position or null if no elements at the indicated position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public IterableQueue<T> getAll(int x, int y, int z) throws IndexOutOfBoundsException{
		IterableQueue<T> allElement = null;
		int i = 0;
		if((x < 0 || x > length) && (y < 0 || y > breadth) && (z < 0 && z > height)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(i < airQueue.size()){
				if(outer.hasNext()){
					innerIndex = outer.next();
					if(innerIndex.getAirSpaceXCoord() == x && innerIndex.getAirSpaceYCoord() == y 
							&& innerIndex.getAirSpaceAltitude() == z){
						allElement = innerIndex.aircraftQueue;
					}
				}
				else{
					throw new NoSuchElementException(); 
				}
				i++;
			}
		}
		return allElement;
	}
	
	/**
	 * Indicates whether there are more than one elements at the indicated position.
	 * 
	 * @param x X Coordinate of the position of the element(s).
	 * @param y Y Coordinate of the position of the element(s).
	 * @param z Z Coordinate of the position of the element(s).
	 * @return true if there are more than one elements at the indicated position, false otherwise.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public boolean isMultipleElementsAt(int x, int y, int z) throws IndexOutOfBoundsException{
		boolean multipleElement = false;
		int i = 0;
		if((x < 0 || x > length) && (y < 0 || y > breadth) && (z < 0 && z > height)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(i < airQueue.size()){
				innerIndex = outer.next();
				if(innerIndex.getAirSpaceXCoord() == x && innerIndex.getAirSpaceYCoord() == y 
						&& innerIndex.getAirSpaceAltitude() == z){
					if(innerIndex.aircraftQueue.size()>1){
						multipleElement = true;
					}
				}
				i++;
			}
		}
		return multipleElement;
	}
	
	/**
	 * Removes the specified element at the indicated position.
	 * 
	 * @param element The element to be removed from the indicated position.
	 * @param x X Coordinate of the position.
	 * @param y Y Coordinate of the position.
	 * @param z Z Coordinate of the position.
	 * @return true if the element was removed from the indicated position, false otherwise.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public boolean remove(int x, int y, int z, T element) throws IndexOutOfBoundsException{
		IterableQueue<Element> tempQueue = new TraversableQueue<Element>(); 
		Element tempElement;
		int i = 0; 
		int j = 0;
		boolean remove = false;
		if((x < 0 || x > length) && (y < 0 || y > breadth) && (z < 0 && z > height)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			IterableQueue<Element> queue = (IterableQueue<Element>) getAll(x, y, z);
			while(i < innerIndex.aircraftQueue.size()){
				System.out.println(queue.size());
				System.out.println(queue.dequeue());
				
				tempElement = queue.dequeue();
				System.out.println(tempElement);
				
				if (tempElement == element){
					remove = true;
				}
				else{
					tempQueue.enqueue(tempElement);
				}
				i++;
			}
			while(j < tempQueue.size()){
				tempElement = tempQueue.dequeue();
				innerIndex.aircraftQueue.enqueue(tempElement);
				j++;
			}
		}
		return remove;
	}
	
	/**
	 * Removes all elements at the indicated position.
	 * 
	 * @param x X Coordinate of the position.
	 * @param y Y Coordinate of the position.
	 * @param z Z Coordinate of the position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	public void removeAll(int x, int y, int z) throws IndexOutOfBoundsException{
		int i = 0;
		if((x < 0 || x > length) && (y < 0 || y > breadth) && (z < 0 && z > height)){
			throw new IndexOutOfBoundsException("Out of bounds");
		}
		else{
			while(i < airQueue.size()){
				if(outer.hasNext()){
					innerIndex = outer.next();
					if(innerIndex.getAirSpaceXCoord() == x && innerIndex.getAirSpaceYCoord() == y 
							&& innerIndex.getAirSpaceAltitude() == z){
						innerIndex.removeAll();
					}
				}
				i++;
			}
		}
	}
	
	/**
	 * Removes all elements stored in the cube.
	 */
	public void clear(){
		while(airQueue.size() != 0){
			airQueue.dequeue();
		}
		
	}
}