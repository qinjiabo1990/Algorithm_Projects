package comp3506.assn2.application;

/**
* Store data in key-value pair form by hash code.
* 
* Memory usage: O(n). n is the max size of array.
* 
* @author Jiabo Qin - 44273022
*
* @param <K, V> Key-Value pair in the Hashmap.
*/
public class MyHashMap<K, V> {
	private Entry<K,V>[] map;
    private int size= 6;
    MyArrayList keyList = new MyArrayList();
    
    static class Entry<K, V> {
        K key;
        V value;
        Entry<K,V> next;
    
        /**
		 * 
		 * @param key  the key of entry.
		 * @param value the value of entry.
		 * @param next  indicate the next entry.
		 */
        public Entry(K key, V value, Entry<K,V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    
    /**
	 * Initialize the custom hashmap  
	 *
	 */
    public MyHashMap(){
    	map = new Entry[size];
    }

    /**
	 * calculate the position to store data
	 * 
	 * Run-time: O(1).
	 * 
	 * @param key The key of hashmap.
	 */
    private int hashCalculation(K key){
    	return Math.abs(key.hashCode()) % size;
    }
    
   
    /**
	 * Associates the specified value with the specified key in this map.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param key unique key
	 * @param data value inserted
	 */
    public void put(K newKey, V data){
    	if(newKey==null) { return; }//does not allow to store null.
     
      //calculate hash of key.
    	int hash=hashCalculation(newKey);
    	
      //create new entry.
    	Entry<K,V> newEntry = new Entry<K,V>(newKey, data, null);
     
      //if map location does not contain any entry, store entry there.
    	if(map[hash] == null){
    		map[hash] = newEntry;
    	}else{
    		Entry<K,V> previous = null;
    		Entry<K,V> current = map[hash];
          
    		while(current != null){ //we have reached last entry of bucket.
    			if(current.key.equals(newKey)){           
    				if(previous==null){  //node has to be insert on first of bucket.
    					newEntry.next=current.next;
    					map[hash]=newEntry;
    				}
    				else{
    					newEntry.next=current.next;
    					previous.next=newEntry;
    					
    				}
    			}
    			previous=current;
    			current = current.next;
    			
    		}
    		previous.next = newEntry;
    	}
    }

    /**
	 * Returns the value to which the specified key is mapped, 
	 * or null if this map contains no mapping for the key.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param key key searched
	 * 
	 * @return value which the specified key is mapped.
	 */
    public V get(K key){
    	int hash = hashCalculation(key);
    	if(map[hash] == null){
    		return null;
    	}else{
    		Entry<K,V> temp = map[hash];
    		while(temp!= null){
    			if(temp.key.equals(key)) {
    				return temp.value;
    			}
    			temp = temp.next; //return value corresponding to key.
    		}         
    		return null;   //returns null if key is not found.
    	}
    }
   	
    /**
	 * Returns a list of key.
	 * 
	 * Run-time: O(n^2).
	 * 
	 * 
	 * @return Returns a list of key.
	 */
   	public MyArrayList getKey() {
   		for(int i=0;i<size;i++){
   			if(map[i]!=null){
   				Entry<K, V> entry=map[i];
   				while(entry!=null){
   					keyList.add(entry.key);
   					entry=entry.next;
   				}
   			}
   		}    
   		return keyList;
   	}
   	
   	/**
	 * Returns the number of elements in this list.
	 * 
	 * Run-time: O(n^2).
	 * 
	 * @return the number of elements.
	 */
   	public int size(){
   		int count = 0;
   		for(int i=0;i<size;i++){
   			if(map[i]!=null){
   				Entry<K, V> entry=map[i];
   				while(entry!=null){
   					entry=entry.next;
   					count++;
   				}
   			}
   		}     
   		return count;
   	}
}
