public class Test {
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
		
		HASH hash = new HASH();
		hash.initialize();
		hash.insert("Neeraj", "Faridabad");
		hash.insert("Ajay", "Faridabad");
		hash.insert("Neeraj", "Delhi");
		hash.insert("Bala", "Chennai");
		hash.printAll();
		hash.search("Neeraj");
	}

}
