public class TRIE {
	private static final int ALPHABET_COUNT = 26;
	private TRIE_NODE root;

	class TRIE_NODE {
		char val;
		TRIE_NODE[] next;
		boolean isEOL;

		public TRIE_NODE(char data) {
			this.val = data;
			this.next = new TRIE_NODE[ALPHABET_COUNT];
			this.isEOL = false;
		}
	}

	void initialize() {
		root = new TRIE_NODE('\0');
	}

	void insert(char[] word) {
		int length = word.length;
		TRIE_NODE travese = root;
		for (int i = 0; i < length; i++) {
			int index = word[i] - 'a';
			TRIE_NODE child = travese.next[index];
			if (child == null) {
				TRIE_NODE newNode = new TRIE_NODE(word[i]);
				travese.next[index] = newNode;
			}
			if (i == length - 1)
				travese.next[index].isEOL = true;
			travese = travese.next[index];
		}
	}

	void printAll() {
		printData(root, "");
	}

	private void printData(TRIE_NODE node, String result) {
		for (int i = 0; i < ALPHABET_COUNT; i++) {
			TRIE_NODE traverse = node.next[i];
			if (traverse != null) {
				char data = traverse.val;
				if (traverse.isEOL) {
					System.out.println(result + data);
				}
				printData(traverse, result + data);
			}
		}
	}

	boolean search(String name) {
		char[] word = name.toCharArray();
		int length = word.length;
		TRIE_NODE travese = root;
		for (int i = 0; i < length; i++) {
			int index = word[i] - 'a';
			TRIE_NODE child = travese.next[index];
			if (child == null) {
				System.out.println(name + " is not found");
				return false;
			}
			if (i == length - 1 && travese.next[index].isEOL == true) {
				System.out.println(name + " is found");
				return true;
			}
			travese = travese.next[index];
		}
		System.out.println(name + " is not found");
		return false;
	}

}
