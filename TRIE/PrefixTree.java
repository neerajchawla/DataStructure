/*
Problem: Implement Trie (Prefix Tree)
Implement a trie with insert, search, and startsWith methods.
Link: https://leetcode.com/problems/implement-trie-prefix-tree/description/	
*/

public class PrefixTree {

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

	/** Initialize your data structure here. */
	public PrefixTree() {
		trie = new TRIE_NODE('\0');
	}

	/** Inserts a word into the trie. */
	public void insert(String word) {
		char[] data = word.toCharArray();
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

	/** Returns if the word is in the trie. */
	public boolean search(String word) {
		char[] data = word.toCharArray();
		int strlen = data.length;
		TRIE_NODE traverse = trie;
		for (int i = 0; i < strlen; i++) {
			int index = data[i] - 'a';
			TRIE_NODE val = traverse.next[index];
			if (val == null) {
				return false;
			}
			if (i == strlen - 1 && traverse.next[index].isEOS) {
				return true;
			}
			traverse = traverse.next[index];
		}
		return false;
	}

	/**
	 * Returns if there is any word in the trie that starts with the given
	 * prefix.
	 */
	public boolean startsWith(String prefix) {
		char[] data = prefix.toCharArray();
		int strlen = data.length;
		TRIE_NODE traverse = trie;
		for (int i = 0; i < strlen; i++) {
			int index = data[i] - 'a';
			TRIE_NODE val = traverse.next[index];
			if (val == null) {
				return false;
			}
			if (i == strlen - 1) {
				return true;
			}
			traverse = traverse.next[index];
		}
		return false;

	}

	public static void main(String[] args) {
		PrefixTree trie = new PrefixTree();
		trie.insert("neeraj");
		trie.insert("ajay");
		trie.insert("mohan");
		trie.insert("zoo");
		trie.insert("neeraja");
		trie.insert("neerajb");
		boolean result = trie.search("neeraja");
		System.out.println(result);
		result = trie.search("neerajb");
		System.out.println(result);
		result = trie.search("neerajc");
		System.out.println(result);
		result = trie.search("neeraj");
		System.out.println(result);
		result = trie.startsWith("nee");
		System.out.println(result);
	}
}
