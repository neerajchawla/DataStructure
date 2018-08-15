/*
Problem: Design a data structure that supports the following two operations:
void addWord(word)
bool search(word)
search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.
Link: https://leetcode.com/problems/add-and-search-word-data-structure-design/description/	
*/

public class WordDictionary {

	private static final int MAX_CHAR_COUNT = 26;
	private TRIE_NODE trie;

	class TRIE_NODE {
		char val;
		TRIE_NODE[] next;
		boolean isEOS;

		public TRIE_NODE(char data) {
			this.val = data;
			this.next = new TRIE_NODE[MAX_CHAR_COUNT];
			this.isEOS = false;
		}
	}

	private void initalize() {
		trie = new TRIE_NODE('\0');
	}

	private void insertToTrie(String input) {
		char[] data = input.toCharArray();
		int strlen = data.length;
		TRIE_NODE traverse = trie;
		for (int i = 0; i < strlen; i++) {
			int index = data[i] - 'a';
			TRIE_NODE val = traverse.next[index];
			if (val == null) {
				TRIE_NODE newNode = new TRIE_NODE(data[i]);
				traverse.next[index] = newNode;
			}
			if (i == strlen - 1) {
				traverse.next[index].isEOS = true;
			}
			traverse = traverse.next[index];
		}
	}

	private boolean newSearchInTrie(String input) {
		return newSearchLogic(input.toCharArray(), 0, "", trie);
	}

	private boolean newSearchLogic(char[] input, int mIndex, String result, TRIE_NODE node) {
		if (mIndex == input.length) {
			if (node.isEOS) {
//				System.out.println(result);
				return true;
			}
			return false;
		}
		char word = input[mIndex];
		mIndex = mIndex + 1;
		if (word == '.') {
			for (char c = 'a'; c <= 'z'; c++) {
				String newResult = result + c;
				int index = c - 'a';
				TRIE_NODE traverse = node.next[index];
				if (traverse != null) {
					boolean ans = newSearchLogic(input, mIndex, newResult, traverse);
					if (ans) {
						return ans;
					}
				}
			}
		} else {
			String newResult = result + word;
			int index = word - 'a';
			TRIE_NODE traverse = node.next[index];
			if (traverse != null) {
				boolean ans = newSearchLogic(input, mIndex, newResult, traverse);
				if (ans) {
					return ans;
				}
			}
		}
		return false;
	}

	/** Initialize your data structure here. */
	public WordDictionary() {
		initalize();
	}

	/** Adds a word into the data structure. */
	public void addWord(String word) {
		insertToTrie(word);
	}

	/**
	 * Returns if the word is in the data structure. A word could contain the
	 * dot character '.' to represent any one letter.
	 */
	public boolean search(String word) {
		return newSearchInTrie(word);
	}
	
	public static void main(String[] args) {
		WordDictionary obj = new WordDictionary();
		obj.addWord("neeraj");
		obj.addWord("ajay");
		obj.addWord("mohan");
		obj.addWord("zoo");
		obj.addWord("neeraja");
		obj.addWord("neerajb");
		// obj.printTrie();

		boolean result = obj.search("z..");
		System.out.println(result);
	}

}
