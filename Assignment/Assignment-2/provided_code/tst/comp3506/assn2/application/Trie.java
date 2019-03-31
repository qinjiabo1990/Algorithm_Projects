package comp3506.assn2.application;

import comp3506.assn2.utils.Pair;

/**
 * a trie structure that is used to store document 
 * 
 * Memory usage: O(n). n is the max size of trie.
 * 
 * @author Jiabo Qin - 44273022
 *
 */
public class Trie {

	/**
	 * Create a structure of a node.
	 * 
	 * @author Jiabo Qin - 44273022
	 * 
	 * @param <T> The type of element held in the data structure.
	 */
	private static class TrieNode<T> {
		private MyHashMap<Character, TrieNode> children;
		boolean isLeafNode=false;
		int count;
		int countWord;
		MyArrayList col;
		MyArrayList row;
		boolean isLineEnd=false;
		
		/**
		 * Create a structure of a node.
		 * 
		 * @author Jiabo Qin - 44273022
		 * 
		 */
		public TrieNode() {
			children = new MyHashMap<Character, TrieNode>();
			count = 0;
			countWord = 0;
			col = new MyArrayList();
			row = new MyArrayList();
		}
		
		/**
		 * Get the character.
		 * 
		 * Run-time: O(1).
		 * 
		 * @param ch A character
		 * @return the character contained
		 */
		public TrieNode getChild(char ch) {
			return children.get(ch);
		}
		
		/**
		 * set the character.
		 * 
		 * Run-time: O(1).
		 * 
		 * @param ch A character
		 */
		public void setChild(char ch) {
			this.children.put(ch, new TrieNode());
		}

		/**
		 * indicate the character is the word end.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return true if it is the end of word.
		 */
		public boolean isLeafNode() {
			return isLeafNode;
		}

		/**
		 * set the character is the word end.
		 * 
		 * Run-time: O(1).
		 * 
		 * @param isLeafNode if it is the end of word
		 * @param newRow the Row of the word
		 * @param newColumn the column of the word
		 */
		public void setLeafNode(boolean isLeafNode, int newRow, int newColumn) {
			this.isLeafNode = isLeafNode;
			if(isLeafNode==true) {
				countWord++;
				this.row.add(newRow);
				this.col.add(newColumn);
			}
		}
//////////////////////////////////////////////////////////
		/**
		 * indicate the word is the last word in a sentence.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return true if it is the last word in a sentence.
		 */
		public boolean isLineEnd() {
			return isLineEnd;
		}

		/**
		 * ser the word is the last word in a sentence.
		 * 
		 * Run-time: O(1).
		 * 
		 * @param isLineEnd true if it is last word in a sentence.
		 */
		public void setLineEnd(boolean isLineEnd) {
			this.isLineEnd = isLineEnd;
		}
//////////////////////////////////////////////////////////
		/**
		 * get the list of column.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return return a list of column.
		 */
		public MyArrayList getCol() {
			return this.col;
		}
		
		/**
		 * get the list of row.
		 * 
		 * Run-time: O(1).
		 * 
		 * 2return return a list of row.
		 */
		public MyArrayList getRow() {
			return this.row;
		}
		
		/**
		 * get the current number of word.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return return the current number of word.
		 */
		public int getCountWord() {
			return countWord;
		}

		/**
		 * get the occurred number of word.
		 * 
		 * Run-time: O(1).
		 * 
		 * @return return the occurred number of word.
		 */
		public int getCount() {
			return count;
		}

		/**
		 * add the occurred number of word.
		 * 
		 * Run-time: O(1).
		 * 
		 */
		public void setCount(int count) {
			this.count = count;
		}	
	}
	
	private TrieNode root;
	
	/**
	 * initialize the constructor.
	 * 
	 * Run-time: O(1).
	 * 
	 */
	public Trie() {
		root = new TrieNode();
		}

	/**
	 * add string in the trie structure
	 * 
	 * Run-time: O(n).
	 * 
	 * @param str string added
	 * @param row number of row added
	 * @param col number of column added
	 * @return return true if it inserted.
	 */
	public boolean insert(String str, int row, int col) {
		TrieNode head = root;
		head.setCount(head.getCount()+1);
		for(char ch : str.toCharArray()){
			if(head.getChild(ch) == null){
				head.setChild(ch);
			}			
			head = head.getChild(ch);
			head.setCount(head.getCount()+1);
		}
		head.setLeafNode(true, row, col);
		return true;
	}
	///////////////////////////////////////////
	public boolean isLineEnd(String str) {
		TrieNode head = root;
		for(char ch : str.toCharArray()){
			head = head.getChild(ch);
			if(head == null){
				return false;
			}
		}
		return head.isLineEnd;
	}
	
	/**
	 * return a list of column number
	 * 
	 * Run-time: O(n).
	 * 
	 * @param str string added
	 * 
	 * @return return a list of column number
	 */
	public MyArrayList getColumnElement(String str) {
		TrieNode head = root;
		MyArrayList list = null;
		for(int i=0;i<str.length();i++){
			char ch = str.charAt(i);
			head = head.getChild(ch);
			if(head == null){
				return null;
			}
			if(ch==str.charAt(str.length()-1)) {
				if(head.isLeafNode()) {
					list = head.getCol();
				}
			}
		}
		return list;
	}
	
	/**
	 * return a list of row number
	 * 
	 * Run-time: O(n).
	 * 
	 * @param str word added
	 * 
	 * @return return a list of row number
	 */
	public MyArrayList getRowElement(String str) {
		TrieNode head = root;
		MyArrayList list = null;
		for(int i=0;i<str.length();i++){
			char ch = str.charAt(i);
			head = head.getChild(ch);
			if(head == null){
				return null;
			}
			if(ch==str.charAt(str.length()-1)) {
				if(head.isLeafNode()) {
					list = head.getRow();
				}
			}
		}
		return list;
	}
	
	/**
	 * judge if the string existed.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param str word added
	 * 
	 * @return return true if it is searched
	 */
	public boolean searchCompleteWord(String str) {
		TrieNode head = root;
		for(char ch : str.toCharArray()){
			head = head.getChild(ch);
			if(head == null){
				return false;
			}
		}
		return head.isLeafNode();
	}
	
	/**
	 * get the number of completed word.
	 * 
	 * Run-time: O(n).
	 * 
	 * @param str searched word.
	 * 
	 * @return number of word.
	 */
	public int searchWord(String str) {
		TrieNode head = root;
		int wordNum = 0;
		for(int i=0;i<str.length();i++){
			char ch = str.charAt(i);
			head = head.getChild(ch);//next node
			if(head == null){
				return 0;
			}
			if(ch==str.charAt(str.length()-1)) {
				if(head.isLeafNode()) {
					wordNum = head.getCountWord();
				}
			}
		}
		return wordNum;
	}
	
	/////not complete
	public Pair getPrefix(String str) {
		Pair pair=null;
		TrieNode head = root;
		//char[] charactor = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		String letters = "abcdefghijklmnopqrstuvwxyz";
		for(char ch : str.toCharArray()){
			head = head.getChild(ch);
			if(head == null){
				return pair;
			}
		}
		for(int i=0;i<(this.searchPartialWord(str)-this.searchWord(str));i++) {
			for(char ch: letters.toCharArray()) {
				head = head.getChild(ch);
				if(head != null) {
				if(head.isLeafNode()) {
					for(int j=0; j<head.getCount();j++) {
						pair = new Pair(head.getRow().get(i),head.getCol().get(i));
					}
				}
				}
			}
		}
		return pair;
	}

	/**
	 * count number of words starting with str
	 * 
	 * Run-time: O(n).
	 * 
	 * @param str word searched
	 * 
	 * @return number of words starting with str
	 */
	public int searchPartialWord(String str) {
		TrieNode head = root;
		for(char ch : str.toCharArray()){
			head = head.getChild(ch);
			if(head == null){
				return 0;
			}
		}
		return head.getCount();
	}
}