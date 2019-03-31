package comp3506.assn2.application;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyArrayList provides a dynamic array which is used to store data.
 * 
 * Memory usage: O(n). n is the max size of array.
 * 
 * @author Jiabo Qin - 44273022
 *
 * @param <T> Type of the elements held in the ArrayList.
 */
public class MyArrayList<T> {
    private final static int INIT_CAPACITY = 12;
    private Object[] list = null;
    
    private int currentCapacity = 0;
    private int size = 0;
    
    public MyArrayList() {
        list = new Object[INIT_CAPACITY];
        currentCapacity = INIT_CAPACITY;
    }
    
    /**
	 * Appends the specified element to the end of this list.
	 * 
	 * Run-time: O(1).
	 * 
	 * @param item The element added to the ArrayList.
	 */
    public void add(T item) {
        if (size == currentCapacity) {
            expansion();
        }
        list[size] = item;
        size++;
    }
    
    /**
	 * Appends all of the elements in the MyArrayList to the end of this list.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param addList The list added to the ArrayList.
	 * @return true if the MyArrayList is added.
	 */
    public boolean addAll(MyArrayList addList) {  
    	Iterator a = addList.iterator();  
    	int numNew = addList.size(); 
    	while(a.hasNext()) {
    		add((T) a.next());
	      	if (size == currentCapacity) {
	            expansion();
	        }
        }
    	return numNew != 0;  
    }    
    
    /**
	 * Retains only the elements in this list that are contained in the MyArrayList.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param retainList The list retained to the ArrayList.
	 * @return true if the MyArrayList is retained.
	 */
    public boolean retainAll(MyArrayList retainList) {
    	for(int i=size-1;i>=0;i--) {
    		if(!retainList.contains(list[i])) {
    			this.remove(i);
    		}
    	}
        return retainList.size()!=0;
    }
    
    /**
	 * Removes from this list all of its elements that are contained in the MyArrayList.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param removeList The list removed to the ArrayList.
	 * @return true if the MyArrayList is removed.
	 */
    public boolean removeAll(MyArrayList removeList) {
    	for(int i=size-1;i>=0;i--) {
    		if(removeList.contains(list[i])) {
    			this.remove(i);
    		}
    	}
        return removeList.size()!=0;
   }
    
    /**
	 * Removes the element at the specified position in this list.
	 * 
	 * Run-time: O(1).
	 * 
	 * @param index points the specified position
	 *
	 */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Object[] newList = new Object[currentCapacity];
        System.arraycopy(list, 0, newList, 0, index);
        System.arraycopy(list, index + 1, newList, index, size - index);
        list = newList;
        size--;
    }
    
    
    /**
	 * Removes the specified element in this list.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param item specified item
	 * 
	 */
    public void remove(T item) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(item)) {
                remove(i);
                break;
            }
        }
    }
    
    /**
	 * Returns true if this list contains the specified element.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param item specified item
	 * @return true if the list contains the specified element.
	 */
    public boolean contains(T item) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
	 * Returns the element at the specified position in this list.
	 * 
	 * Run-time: O(1).
	 * 
	 * @param index points the specified position
	 * @return specidied element
	 */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T)list[index];
    }
    
    /**
	 * Returns true if this list contains no elements.
	 * 
	 * Run-time: O(1).
	 * 
	 * @return true if it is empty.
	 */
    public boolean isEmpty() {
        return (size == 0) ? true : false;
    }
    
    /**
	 * Returns the number of elements in this list.
	 * 
	 * Run-time: O(1).
	 * 
	 * @return the number of elements.
	 */
    public int size() {
        return size;
    }
    
    /**
   	 * Removes all of the elements from this list.
   	 * 
   	 * Run-time: O(1).
   	 * 
   	 */
    public void clear() {
        list = new Object[currentCapacity];
        size = 0;
    }
    
    /**
   	 * Expand the capacity, when size == currentCapacity
   	 * 
   	 * Run-time: O(1).
   	 * 
   	 */
    private void expansion() {
        Object[] oldList = list;
        Object[] newList = new Object[getNewCapacity()];
        System.arraycopy(oldList, 0, newList, 0, oldList.length);
        list = newList;
    }
    
    /**
   	 * get new capacity.
   	 * add half of current capacity when it is full.
   	 * 
   	 * Run-time: O(1).
   	 * 
   	 * @return extended capacity
   	 */
    private int getNewCapacity() {
        return currentCapacity = currentCapacity + (currentCapacity/2);
    }
    
    
    /**
	 * An iterator that can iterate over all the elements in the ArrayList.
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
    public Iterator<T> iterator() {
        class Itr implements Iterator<T> {
            private int cur = -1;
            
            /**
        	 * Returns true if the iteration has more elements. 
        	 * 
        	 * Run-time: O(1).
        	 * 
        	 * @return true if the iteration has more elements.
        	 */
            @Override
            public boolean hasNext() {
                 return cur + 1 < size();
            }
            
            /**
        	 * Returns the next element in the iteration.
        	 * 
        	 * Run-time: O(1).
        	 * 
        	 * @return the next element in the iteration
        	 */
            @Override
            public T next() {
                 cur++;
                 T tmp = get(cur);
                 return tmp;
            }  
        }
        return new Itr();
   }	
}

