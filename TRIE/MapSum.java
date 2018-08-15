/*
Problem: Map Sum Pairs
Description: Implement a MapSum class with insert, and sum methods.
For the method insert, you'll be given a pair of (string, integer). The string represents the key and the integer represents the value. If the key already existed, then the original key-value pair will be overridden to the new one.
For the method sum, you'll be given a string representing the prefix, and you need to return the sum of all the pairs' value whose key starts with the prefix.
Link: https://leetcode.com/problems/map-sum-pairs/description/
*/
public class MapSum {
	private static final int MAX_CHAR_COUNT = 26;
	private TRIE_NODE trie;
	private int finalSum = 0;

	class TRIE_NODE {
		char val;
		TRIE_NODE[] next;
		boolean isEOS;
		int sValue;

		public TRIE_NODE(char data) {
			this.val = data;
			this.next = new TRIE_NODE[MAX_CHAR_COUNT];
			this.isEOS = false;
			this.sValue = 0;
		}
	}

	/** Initialize your data structure here. */
	public MapSum() {
		trie = new TRIE_NODE('\0');
	}

	public void insert(String key, int val) {
		char[] data = key.toCharArray();
		int strlen = data.length;
		TRIE_NODE traverse = trie;
		for (int i = 0; i < strlen; i++) {
			int index = data[i] - 'a';
			if (traverse.next[index] == null) {
				TRIE_NODE newNode = new TRIE_NODE(data[i]);
				traverse.next[index] = newNode;
			}
			if (i == strlen - 1) {
				traverse.next[index].isEOS = true;
				traverse.next[index].sValue = val;
			}
			traverse = traverse.next[index];
		}
	}
	
	public int sum(String prefix) {
		finalSum = 0;
		char[] data = prefix.toCharArray();
		int strlen = data.length;
		TRIE_NODE traverse = trie;
		for (int i = 0; i < strlen; i++) {
			int index = data[i] - 'a';
			TRIE_NODE val = traverse.next[index];
			if (val == null) {
				return 0;
			}
			if (i == strlen - 1) {
				finalSum = traverse.next[index].sValue;
				sumAllValues(traverse.next[index], "");
				 return finalSum;
			}
			traverse = traverse.next[index];
		}
		return 0;
	}

	private void sumAllValues(TRIE_NODE node, String result) {
		for (int i = 0; i < MAX_CHAR_COUNT; i++) {
			if (node.next[i] != null) {
				String value = result + node.next[i].val;
				if (node.next[i].isEOS) {
					System.out.println(value);
					finalSum = finalSum+node.next[i].sValue;
				}
				sumAllValues(node.next[i], value);

			}
		}
	}

	public static void main(String[] args) {
		MapSum trie = new MapSum();
		trie.insert("neeraj", 1);
		trie.insert("ajay", 2);
		trie.insert("mohan", 3);
		trie.insert("zoo", 4);
		trie.insert("neeraja", 5);
		trie.insert("neerajb", 6);
		System.out.println(trie.sum("nee"));
	}

}
