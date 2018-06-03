
public class HASH {
	private static final int MAX_VALUE = 10;
	HASH_NODE[] hARRAY;

	class Data {
		private String Name;
		private String City;

		public Data(String name, String city) {
			this.Name = name;
			this.City = city;
		}
	}

	class HASH_NODE {
		private Data data;
		private HASH_NODE next;

		public HASH_NODE(Data d) {
			this.data = new Data(d.Name, d.City);
			this.next = null;
		}
	}

	void initialize() {
		hARRAY = new HASH_NODE[MAX_VALUE];
	}

	private int getHashIndex(String word) {
		char[] keyword = word.toCharArray();
		int length = keyword.length;
		int hash = 5381;
		for (int i = 0; i < length; i++) {
			hash = (hash << 5) + hash + keyword[i];
		}
		hash = hash % MAX_VALUE;
		if (hash < 0) {
			hash = hash * -1;
		}
		return hash;
	}

	void insert(String name, String city) {
		int index = getHashIndex(name);

		// Add at start
		HASH_NODE newNode = new HASH_NODE(new Data(name, city));
		newNode.next = hARRAY[index];
		hARRAY[index] = newNode;
	}

	void printAll() {
		for (int i = 0; i < MAX_VALUE; i++) {
			HASH_NODE traverse = hARRAY[i];
			while (traverse != null) {
				System.out.println(traverse.data.Name + " , " + traverse.data.City);
				traverse = traverse.next;
			}
		}
	}
	
	void search(String name) {
		int index = getHashIndex(name);
		HASH_NODE traverse = hARRAY[index];
		while (traverse != null) {
			if(traverse.data.Name == name)
			System.out.println(traverse.data.City);
			traverse = traverse.next;
		}
	}
}
