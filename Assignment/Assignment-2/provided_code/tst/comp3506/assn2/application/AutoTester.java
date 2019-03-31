package comp3506.assn2.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import comp3506.assn2.utils.Pair;
import comp3506.assn2.utils.Triple;

/**
 * Hook class used by automated testing tool.
 * The testing tool will instantiate an object of this class to test the functionality of your assignment.
 * You must implement the constructor stub below and override the methods from the Search interface
 * so that they call the necessary code in your application.
 * 
 *
 * @author Jiabo Qin - 44273022
 */
public class AutoTester implements Search {
	private Scanner read = null;
	private Scanner documentFile = null;
	private Scanner indexFile = null;
	private Scanner stopWordsFile = null;
	private Trie trie;
	private MyHashMap bookIndex;
	private int rowNum;
	private int colNum;

	/**
	 * Create an object that performs search operations on a document.
	 * If indexFileName or stopWordsFileName are null or an empty string the document should be loaded
	 * and all searches will be across the entire document with no stop words.
	 * All files are expected to be in the files sub-directory and
	 * file names are to include the relative path to the files (e.g. "files//shakespeare.txt").
	 *
	 * @param documentFileName  Name of the file containing the text of the document to be searched.
	 * @param indexFileName     Name of the file containing the index of sections in the document.
	 * @param stopWordsFileName Name of the file containing the stop words ignored by most searches.
	 * @throws FileNotFoundException if any of the files cannot be loaded.
	 *                               The name of the file(s) that could not be loaded should be passed
	 *                               to the FileNotFoundException's constructor.
	 * @throws IllegalArgumentException if documentFileName is null or an empty string.
	 */
	public AutoTester(String documentFileName, String indexFileName, String stopWordsFileName)
			throws FileNotFoundException, IllegalArgumentException {

		if(documentFileName == null | documentFileName=="") {
			throw new IllegalArgumentException();
		}else {
			rowNum = 0;
			//this.stopWordsFile = fileRead(stopWordsFileName);
			trie = new Trie();
			bookIndex = new MyHashMap();
			this.insertFile(fileRead(documentFileName));
			this.insertIndex(fileRead(indexFileName));
		}
	}

	/**
	 * Load document
	 *
	 * Run-time: O(1).
	 *
	 * @param fileName The file name to be loaded.
	 * @return A new Scanner that produces values scanned from the specified file.
	 * @throws FileNotFoundException if any of the files cannot be loaded.
	 */
	public Scanner fileRead(String fileName) throws FileNotFoundException {
		read = new Scanner(new File(fileName));
		return read;
	}

	/**
	 * Insert document in trie
	 *
	 * Run-time: O(n).
	 *
	 * @param file The file which has read.
	 */
	public void insertFile(Scanner file) {
		while(file.hasNextLine()){
			rowNum++;
			colNum = 1;
			String word = file.nextLine();
			String[] parts = word.toLowerCase().split(" ");
			Boolean isFirst = true;
			for(int i=1;i<parts.length;i++) {
				if(isFirst) {
					if(!(parts[0].isEmpty())){
						colNum = 1;
						trie.insert(parts[0].replaceAll("[ '][' ]", " ").replaceAll("[!-&(-/:-?\\[-`{-~]", "").replaceAll("“", ""), rowNum, colNum);
					}
					isFirst=false;
				}
				colNum = colNum + parts[i-1].length() + 1;
				if(!(parts[i].isEmpty())) {
					trie.insert(parts[i].replaceAll("[ '][' ]", " ").replaceAll("[!-&(-/:-?\\[-`{-~]", "").replaceAll("“", ""), rowNum, colNum);
				}
			}
		}
	}

	/**
	 * Insert index in hashmap
	 *
	 * Run-time: O(n).
	 *
	 * @param file The index which has read.
	 */
	public void insertIndex(Scanner file) {
		Pair pair;
		MyArrayList title = new MyArrayList();
		MyArrayList page = new MyArrayList();
		while(file.hasNextLine()){
			String word = file.nextLine();
			String[] parts = word.split(",");
			title.add(parts[0]);
			page.add(parts[1]);
		}
		page.add(String.valueOf(rowNum));
		for(int i=0;i<title.size();i++) {
			pair= new Pair(page.get(i),page.get(i+1));
			bookIndex.put(title.get(i), pair);
		}
		Pair a = (Pair) bookIndex.get("CYMBELINE");

	}

	/**
	 * Determines the number of times the word appears in the document.
	 *
	 * Run-time: O(n).
	 *
	 * @param word The word to be counted in the document.
	 * @return The number of occurrences of the word in the document.
	 * @throws IllegalArgumentException if word is null or an empty String.
	 */
	public int wordCount(String word) throws IllegalArgumentException {
		if(word == null | word == "") {
			throw new IllegalArgumentException();
		}
		return trie.searchWord(word);
	}

	/**
	 * Finds all occurrences of the phrase in the document.
	 * A phrase may be a single word or a sequence of words.
	 *
	 * Run-time: O(n).
	 *
	 * @param phrase The phrase to be found in the document.
	 * @return List of pairs, where each pair indicates the line and column number of each occurrence of the phrase.
	 *         Returns an empty list if the phrase is not found in the document.
	 * @throws IllegalArgumentException if phrase is null or an empty String.
	 */
	//not complete
	public List<Pair<Integer,Integer>> phraseOccurrence(String phrase) throws IllegalArgumentException {
		Pair pair;
		List result = new ArrayList();
		if(phrase == null | phrase == "") {
			throw new IllegalArgumentException();
		}
		//judge is it single word? or a sequence of words.
		String[] words = phrase.split(" ");
		MyArrayList word = new MyArrayList();
		if(words.length==1) {
			if(trie.searchCompleteWord(words[0])) {
				for(int i=0;i<trie.searchWord(words[0]);i++) {
					pair = new Pair(trie.getRowElement(words[0]).get(i),trie.getColumnElement(words[0]).get(i));
					result.add(pair);
				}
			}
			else {
				return result;
			}
		}
		else {
			/*if(trie.searchCompleteWord(words[0])) {
				for(int j=0;j<words.length;j++) {
					word.add(trie.getColumnElement(words[0]));
					for(int i=1;i<trie.searchWord(words[j])-1;i++) {
						word.retainAll(trie.getColumnElement(words[j]));
						for(int k=0;k<word.size();k++) {
							if(word.get(k)+1+word.get(k).length == word.get(k+1)) {
								
							}
						}
						pair = new Pair(trie.getRowElement(words[0]).get(i),trie.getColumnElement(words[0]).get(i));
						result.add(pair);
					}
				}
			}
			else {
				return result;
			}*/
		}
		return result;
	}

	/**
	 * Finds all occurrences of the prefix in the document.
	 * A prefix is the start of a word. It can also be the complete word.
	 * For example, "obscure" would be a prefix for "obscure", "obscured", "obscures" and "obscurely".
	 *
	 * Run-time: O(n).
	 *
	 * @param prefix The prefix of a word that is to be found in the document.
	 * @return List of pairs, where each pair indicates the line and column number of each occurrence of the prefix.
	 *         Returns an empty list if the prefix is not found in the document.
	 * @throws IllegalArgumentException if prefix is null or an empty String.
	 */
	//not complete
	public List<Pair<Integer,Integer>> prefixOccurrence(String prefix) throws IllegalArgumentException {
		Pair pair;
		List result = new ArrayList(phraseOccurrence(prefix));

		if(prefix == null | prefix == "") {
			throw new IllegalArgumentException();
		}
		if(trie.searchCompleteWord(prefix)) {
			result.add(trie.getPrefix(prefix));
		}
		else {
			return result;
		}
		return result;
	}

	/**
	 * Searches the document for lines that contain all the words in the 'words' parameter.
	 * Implements simple "and" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 *
	 * Run-time: O(n^2).
	 *
	 * @param words Array of words to find on a single line in the document.
	 * @return List of line numbers on which all the words appear in the document.
	 *         Returns an empty list if the words do not appear in any line in the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in the array are null or empty.
	 */
	public List<Integer> wordsOnLine(String[] words) throws IllegalArgumentException {
		List result = new ArrayList();
		MyArrayList temp = new MyArrayList();
		if(words == null | words.length == 0) {
			throw new IllegalArgumentException();
		}
		temp.addAll(trie.getRowElement(words[0]));
		for(int i=1;i<words.length;i++) {
			if(trie.searchCompleteWord(words[i])) {
				temp.retainAll(trie.getRowElement(words[i]));
			}
		}
		for(int j=0;j<temp.size();j++) {
			result.add(temp.get(j));
		}
		return result;
	}

	/**
	 * Searches the document for lines that contain any of the words in the 'words' parameter.
	 * Implements simple "or" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 *
	 * Run-time: O(n^2).
	 *
	 * @param words Array of words to find on a single line in the document.
	 * @return List of line numbers on which any of the words appear in the document.
	 *         Returns an empty list if none of the words appear in any line in the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in the array are null or empty.
	 */
	public List<Integer> someWordsOnLine(String[] words) throws IllegalArgumentException {
		List result = new ArrayList();
		MyArrayList all = new MyArrayList();
		MyArrayList retain = new MyArrayList();
		if(words == null | words.length == 0) {
			throw new IllegalArgumentException();
		}
		all.addAll(trie.getRowElement(words[0]));
		retain.addAll(trie.getRowElement(words[0]));
		for(int i=1;i<words.length;i++) {
			if(trie.searchCompleteWord(words[i])) {
				all.addAll(trie.getRowElement(words[i]));
				retain.retainAll(trie.getRowElement(words[i]));
				all.removeAll(retain);
			}
		}
		for(int l=0;l<all.size();l++) {
			result.add(all.get(l));
		}
		return result;
	}

	/**
	 * Searches the document for lines that contain all the words in the 'wordsRequired' parameter
	 * and none of the words in the 'wordsExcluded' parameter.
	 * Implements simple "not" logic when searching for the words.
	 * The words do not need to be contiguous on the line.
	 *
	 * Run-time: O(n^2).
	 *
	 * @param wordsRequired Array of words to find on a single line in the document.
	 * @param wordsExcluded Array of words that must not be on the same line as 'wordsRequired'.
	 * @return List of line numbers on which all the wordsRequired appear
	 *         and none of the wordsExcluded appear in the document.
	 *         Returns an empty list if no lines meet the search criteria.
	 * @throws IllegalArgumentException if either of wordsRequired or wordsExcluded are null or an empty array
	 *                                  or any of the Strings in either of the arrays are null or empty.
	 */
	public List<Integer> wordsNotOnLine(String[] wordsRequired, String[] wordsExcluded)
			throws IllegalArgumentException {
		List result = new ArrayList();
		MyArrayList excluded = new MyArrayList();
		MyHashMap required = new MyHashMap();
		MyArrayList temp = new MyArrayList();
		if(wordsRequired == null | wordsRequired.length == 0 | wordsExcluded == null | wordsExcluded.length == 0  ) {
			throw new IllegalArgumentException();
		}

		//handle by hashmap get unique key
		for(int i=0;i<wordsRequired.length;i++) {
			if(trie.searchCompleteWord(wordsRequired[i])) {
				for(int m=0;m<trie.getRowElement(wordsRequired[i]).size();m++) {
					required.put(trie.getRowElement(wordsRequired[i]).get(m),m);
				}
			}
		}

		//handle excluded size=7
		for(int j=0;j<wordsExcluded.length;j++) {
			if(trie.searchCompleteWord(wordsExcluded[j])) {
				excluded.addAll(trie.getRowElement(wordsExcluded[j]));
			}
		}

		//get unique required row
		temp.addAll(required.getKey());

		for(int l=0;l<temp.size();l++) {
			if(!excluded.contains(temp.get(l))) {
				result.add(temp.get(l));
			}
		}
		return result;
	}

	/**
	 * Searches the document for sections that contain all the words in the 'words' parameter.
	 * Implements simple "and" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * Run-time: O(n^3).
	 *
	 * @param titles Array of titles of the sections to search within,
	 *               the entire document is searched if titles is null or an empty array.
	 * @param words Array of words to find within a defined section in the document.
	 * @return List of triples, where each triple indicates the line and column number and word found,
	 *         for each occurrence of one of the words.
	 *         Returns an empty list if the words are not found in the indicated sections of the document,
	 *         or all the indicated sections are not part of the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in either of the arrays are null or empty.
	 */
	public List<Triple<Integer,Integer,String>> simpleAndSearch(String[] titles, String[] words)
			throws IllegalArgumentException {
		List result= new ArrayList();
		if(titles == null | titles.length == 0 | words == null | words.length == 0  ) {
			throw new IllegalArgumentException();
		}

		for(int i=0;i<titles.length;i++) {
			Pair range = (Pair) bookIndex.get(titles[i]);
			int floor = Integer.parseInt((String) range.getLeftValue());
			int ceiling = Integer.parseInt((String) range.getRightValue());

			MyArrayList<Triple<Integer,Integer,String>> loading = new MyArrayList<>();
			MyArrayList<Integer> count = new MyArrayList<>();

			for(int j=0;j<words.length;j++) {
				int check = 0;
				for(int k=0;k<trie.searchWord(words[j]);k++) {
					int wordRow = (int) trie.getRowElement(words[j]).get(k);
					int wordCol = (int) trie.getColumnElement(words[j]).get(k);
					if(wordRow>floor && wordRow<ceiling) {
						Triple triple = new Triple(wordRow,wordCol,words[j]);
						loading.add(triple);
						check++;
					}
				}
				count.add(check);
			}
			if(!count.contains(0)) {
				for(int d = 0;d<loading.size();d++) {
					Triple<Integer, Integer, String> triple = loading.get(d);
					result.add(triple);
				}
			}
		}
		return result;
	}

	/**
	 * Searches the document for sections that contain any of the words in the 'words' parameter.
	 * Implements simple "or" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * Run-time: O(n^3).
	 *
	 * @param titles Array of titles of the sections to search within,
	 *               the entire document is searched if titles is null or an empty array.
	 * @param words Array of words to find within a defined section in the document.
	 * @return List of triples, where each triple indicates the line and column number and word found,
	 *         for each occurrence of one of the words.
	 *         Returns an empty list if the words are not found in the indicated sections of the document,
	 *         or all the indicated sections are not part of the document.
	 * @throws IllegalArgumentException if words is null or an empty array
	 *                                  or any of the Strings in either of the arrays are null or empty.
	 */
	public List<Triple<Integer,Integer,String>> simpleOrSearch(String[] titles, String[] words)
			throws IllegalArgumentException {
		List result= new ArrayList();
		if(titles == null | titles.length == 0 | words == null | words.length == 0  ) {
			throw new IllegalArgumentException();
		}

		for(int i=0;i<titles.length;i++) {
			Pair range = (Pair) bookIndex.get(titles[i]);
			int floor = Integer.parseInt((String) range.getLeftValue());
			int ceiling = Integer.parseInt((String) range.getRightValue());

			MyArrayList<Triple<Integer,Integer,String>> loading = new MyArrayList<>();
			MyArrayList<Integer> count = new MyArrayList<>();

			for(int j=0;j<words.length;j++) {

				for(int k=0;k<trie.searchWord(words[j]);k++) {
					int wordRow = (int) trie.getRowElement(words[j]).get(k);
					int wordCol = (int) trie.getColumnElement(words[j]).get(k);
					if(wordRow>floor && wordRow<ceiling) {
						Triple triple = new Triple(wordRow,wordCol,words[j]);
						loading.add(triple);

					}
				}
			}

				for(int d = 0;d<loading.size();d++) {
					Triple<Integer, Integer, String> triple = loading.get(d);
					result.add(triple);
				}

		}
		return result;
	}
	/**
	 * Searches the document for sections that contain all the words in the 'wordsRequired' parameter
	 * and none of the words in the 'wordsExcluded' parameter.
	 * Implements simple "not" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * Run-time: O(n^2).
	 *
	 * @param titles Array of titles of the sections to search within,
	 *               the entire document is searched if titles is null or an empty array.
	 * @param wordsRequired Array of words to find within a defined section in the document.
	 * @param wordsExcluded Array of words that must not be in the same section as 'wordsRequired'.
	 * @return List of triples, where each triple indicates the line and column number and word found,
	 *         for each occurrence of one of the required words.
	 *         Returns an empty list if the words are not found in the indicated sections of the document,
	 *         or all the indicated sections are not part of the document.
	 * @throws IllegalArgumentException if wordsRequired is null or an empty array
	 *                                  or any of the Strings in any of the arrays are null or empty.
	 */
	public List<Triple<Integer,Integer,String>> simpleNotSearch(String[] titles, String[] wordsRequired,
			                                                     String[] wordsExcluded)
			throws IllegalArgumentException {
		if(titles == null | titles.length == 0 | wordsRequired == null
				| wordsRequired.length == 0 | wordsExcluded == null | wordsExcluded.length == 0 ) {
			throw new IllegalArgumentException();
		}

		List result= new ArrayList();

		MyArrayList excluded = new MyArrayList();
		for(int l=0; l<wordsExcluded.length;l++) {
			if(trie.searchCompleteWord(wordsExcluded[l])) {
				excluded.addAll(trie.getRowElement(wordsExcluded[l]));
			}
		}

		for(int i=0;i<titles.length;i++) {
			Pair range = (Pair) bookIndex.get(titles[i]);
			int floor = Integer.parseInt((String) range.getLeftValue());
			int ceiling = Integer.parseInt((String) range.getRightValue());

			MyArrayList<Triple<Integer,Integer,String>> loading = new MyArrayList<>();
			MyArrayList<Integer> count = new MyArrayList<>();
			int excludedWord = 0;
			for(int l=0;l<excluded.size();l++) {
				int excludedRow = (int) excluded.get(l);
				if(excludedRow > floor && excludedRow < ceiling) {
					excludedWord++;
				}
			}

			if(excludedWord==0) {
				for(int j=0;j<wordsRequired.length;j++) {//word loop
					int check = 0;
					for(int k=0;k<trie.searchWord(wordsRequired[j]);k++) {//location loop
						int wordRow = (int) trie.getRowElement(wordsRequired[j]).get(k);
						int wordCol = (int) trie.getColumnElement(wordsRequired[j]).get(k);
						if(wordRow>floor && wordRow<ceiling) {
							Triple triple = new Triple(wordRow, wordCol, wordsRequired[j]);
							loading.add(triple);
							check++;
						}
					}//location loop
					count.add(check);
				}
				if(!count.contains(0)) {
					for(int d = 0;d<loading.size();d++) {
						Triple<Integer, Integer, String> triple = loading.get(d);
						result.add(triple);
					}
				}
			}
		}
		return result;
	}

	/**
	 * Searches the document for sections that contain all the words in the 'wordsRequired' parameter
	 * and at least one of the words in the 'orWords' parameter.
	 * Implements simple compound "and/or" logic when searching for the words.
	 * The words do not need to be on the same lines.
	 *
	 * Run-time: O(n^3).
	 *
	 * @param titles Array of titles of the sections to search within,
	 *               the entire document is searched if titles is null or an empty array.
	 * @param wordsRequired Array of words to find within a defined section in the document.
	 * @param orWords Array of words, of which at least one, must be in the same section as 'wordsRequired'.
	 * @return List of triples, where each triple indicates the line and column number and word found,
	 *         for each occurrence of one of the words.
	 *         Returns an empty list if the words are not found in the indicated sections of the document,
	 *         or all the indicated sections are not part of the document.
	 * @throws IllegalArgumentException if wordsRequired is null or an empty array
	 *                                  or any of the Strings in any of the arrays are null or empty.
	 */
	public List<Triple<Integer,Integer,String>> compoundAndOrSearch(String[] titles, String[] wordsRequired,
			                                                         String[] orWords)
			throws IllegalArgumentException {
		if(titles == null | titles.length == 0 | wordsRequired == null
				| wordsRequired.length == 0 | orWords == null | orWords.length == 0 ) {
			throw new IllegalArgumentException();
		}

		List result= new ArrayList();

		MyArrayList<Triple<Integer, Integer, String>> finalList = new MyArrayList();
		MyArrayList orSearch = new MyArrayList();
		MyArrayList requiredSearch = new MyArrayList();
		//compare with orWord
		for(int i=0;i<titles.length;i++) {
			Pair range = (Pair) bookIndex.get(titles[i]);
			int floor = Integer.parseInt((String) range.getLeftValue());
			int ceiling = Integer.parseInt((String) range.getRightValue());
			int addTimes = 0;

			for(int j=0;j<orWords.length;j++) {//word loop
				for(int k=0;k<trie.searchWord(orWords[j]);k++) {//location loop
					int wordRow = (int) trie.getRowElement(orWords[j]).get(k);
					int wordCol = (int) trie.getColumnElement(orWords[j]).get(k);
					if(wordRow>floor && wordRow<ceiling) {
						addTimes++;
					}
				}
			}
			if(addTimes != 0) {
				orSearch.add(titles[i]);
			}
		}
		//the result compares with required
		for(int i=0;i<orSearch.size();i++) {
			Pair range = (Pair) bookIndex.get(orSearch.get(i));
			int floor = Integer.parseInt((String) range.getLeftValue());
			int ceiling = Integer.parseInt((String) range.getRightValue());

			MyArrayList<Triple<Integer,Integer,String>> loading = new MyArrayList<>();
			MyArrayList<Integer> count = new MyArrayList<>();

			for(int j=0;j<wordsRequired.length;j++) {
				int check = 0;
				for(int k=0;k<trie.searchWord(wordsRequired[j]);k++) {
					int wordRow = (int) trie.getRowElement(wordsRequired[j]).get(k);
					int wordCol = (int) trie.getColumnElement(wordsRequired[j]).get(k);
					if(wordRow>floor && wordRow<ceiling) {
						Triple triple = new Triple(wordRow, wordCol, wordsRequired[j]);
						loading.add(triple);
						check++;
					}
				}
				count.add(check);
			}
			if(!count.contains(0)) {
				for(int d = 0;d<loading.size();d++) {
					Triple<Integer, Integer, String> triple = loading.get(d);
					finalList.add(triple);
				}
				requiredSearch.add(orSearch.get(i));
			}
		}
		//remove orWord
		for(int i=0;i<requiredSearch.size();i++) {
			Pair range = (Pair) bookIndex.get(requiredSearch.get(i));

			int floor = Integer.parseInt((String) range.getLeftValue());
			int ceiling = Integer.parseInt((String) range.getRightValue());

			for(int j=0;j<orWords.length;j++) {

				for(int k=0;k<trie.searchWord(orWords[j]);k++) {
					int wordRow = (int) trie.getRowElement(orWords[j]).get(k);
					int wordCol = (int) trie.getColumnElement(orWords[j]).get(k);
					if(wordRow>floor && wordRow<ceiling) {
						Triple triple = new Triple(wordRow, wordCol, orWords[j]);
						result.add(triple);
					}
				}
			}
		}

		for(int i=0; i<finalList.size();i++) {
			result.add(finalList.get(i));
		}
		return result;
	}
}
