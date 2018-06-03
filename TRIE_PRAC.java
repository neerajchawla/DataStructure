public class TRIE_PRAC {
	public static void main(String[] args) {
		TRIE trie = new TRIE();
		trie.initialize();
		trie.insert("neeraj".toCharArray());
		trie.insert("ajay".toCharArray());
		trie.insert("zoo".toCharArray());
		trie.insert("zoom".toCharArray());
		trie.printAll();
		trie.search("neeraj");
		trie.search("neeraja");
		trie.search("neer");
	}

}
