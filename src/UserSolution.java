
public class UserSolution {
	private final static int MAX_MAP_SIZE = 64;
	private final static int ZONE_SIZE = 4;
	private int dbCount;
	DB_Node head = null;
	HASH hash;

	class DB_Node {
		char[][] data = new char[ZONE_SIZE][ZONE_SIZE];
		DB_Node next;

		public DB_Node(char[][] guess) {
			for (int i = 0; i < ZONE_SIZE; i++)
				for (int j = 0; j < ZONE_SIZE; j++)
					data[i][j] = guess[i][j];
		}
	}

	private void insertToDB(char guess[][]) {
		DB_Node newNode = new DB_Node(guess);
		newNode.next = head;
		head = newNode;
		dbCount++;
	}

	class Coordinate {
		int x;
		int y;

		public Coordinate(int a, int b) {
			this.x = a;
			this.y = b;
		}
	}

	class HASH_NODE {
		int[][] value;
		Coordinate loc;
		HASH_NODE next;
		int match;

		public HASH_NODE(int[][] data, int a, int b) {
			this.value = new int[2][2];
			this.value[0][0] = data[0][0];
			this.value[0][1] = data[0][1];
			this.value[1][0] = data[1][0];
			this.value[1][1] = data[1][1];
			this.loc = new Coordinate(a, b);
			this.next = null;
			this.match = 0;
		}

		public HASH_NODE() {
			// TODO Auto-generated constructor stub
		}
	}

	private int cArrayCount;

	class HASH {
		private HASH_NODE[] hArray;

		private void initialize(int size) {
			hArray = new HASH_NODE[size];
		}

		private int getIndex(int[][] box) {
			int a = box[0][0];
			int b = box[0][1];
			int c = box[1][0];
			int d = box[1][1];
			int index = (((((a << 4) | b) << 4) | c) << 4) | d;
			return index;
		}

		private void addToHash(HASH_NODE node) {
			int index = getIndex(node.value);
			HASH_NODE newNode = node;
			newNode.next = hArray[index];
			hArray[index] = newNode;
		}

		private Coordinate[] searchInHash(int[][] iData) {
			cArrayCount = 0;
			int index = getIndex(iData);
			Coordinate[] cArray = new Coordinate[600]; // ToDo: Temp no 600
			if (hArray[index] != null) {
				HASH_NODE traverse = hArray[index];
				while (traverse != null) {
					int[][] data = traverse.value;
					if (data[0][0] == iData[0][0] && data[0][1] == iData[0][1] & data[1][0] == iData[1][0]
							&& data[1][1] == iData[1][1]) {
						// return traverse.loc;
						cArray[cArrayCount] = traverse.loc;
						cArrayCount++;
					}
					traverse = traverse.next;
				}
			}
			return cArray;
		}
	}

	public void reconstruct(int n, char guess[][], int max_calls) {
		dbCount = 0;
		hash = new HASH();
		hash.initialize(65535);
		int zeroBlockCount = ((n - 4) * (n - 4)) / 4;
		// printData(guess, n);
		for (int i = 0; i < n;) {
			for (int j = 0; j < n;) {
				if (i == 0 || i == n - 2 || j == 0 || j == n - 2) {
					int[][] data = new int[2][2];
					data[0][0] = (int) guess[i][j];
					data[0][1] = (int) guess[i][j + 1];
					data[1][0] = (int) guess[i + 1][j];
					data[1][1] = (int) guess[i + 1][j + 1];
					HASH_NODE node = new HASH_NODE(data, i, j);
					hash.addToHash(node);
				}
				j = j + 2;
			}
			i = i + 2;
		}

		char[][] zone = new char[ZONE_SIZE][ZONE_SIZE];
		for (int callCount = 0; callCount < max_calls; callCount++) {
			if (zeroBlockCount == 0)
				return;

			Solution.randomscan(zone);

			// Search data from DB
			boolean cellMatched = findPosition(zone, guess, n);
			if (!cellMatched) {
				insertToDB(zone);
			}

			for (int tCount = 0; tCount < dbCount; tCount++) {
				DB_Node traverse = head;
				// Find first element
				char[][] data = traverse.data;
				cellMatched = findPosition(data, guess, n);
				if (cellMatched) {
					head = head.next;
					dbCount--;
					tCount = 0;
					continue;
				}

				// Find other element
				while (traverse.next != null) {
					data = traverse.next.data;
					cellMatched = findPosition(data, guess, n);
					if (cellMatched) {
						traverse.next = traverse.next.next;
						tCount = 0;
						dbCount--;
						break;
					}
					traverse = traverse.next;
				}
			}
		}
		printData(guess, n);
	}

	private boolean findPosition(char zone[][], char guess[][], int guessSize) {
		printData(zone, ZONE_SIZE);

		int block = -1;
		for (int i = 0; i < ZONE_SIZE;) {
			for (int j = 0; j < ZONE_SIZE;) {
				// boolean bMatched = false;
				block++;
				int[][] data = new int[2][2];
				data[0][0] = (int) zone[i][j];
				data[0][1] = (int) zone[i][j + 1];
				data[1][0] = (int) zone[i + 1][j];
				data[1][1] = (int) zone[i + 1][j + 1];
				Coordinate[] loc = hash.searchInHash(data);

				for (int l = 0; l < cArrayCount; l++) {
					int startX = 0, startY = 0;
					if (block == 0) {
						startX = loc[l].x;
						startY = loc[l].y;
					} else if (block == 1) {
						startX = loc[l].x;
						startY = loc[l].y - 2;
					} else if (block == 2) {
						startX = loc[l].x - 2;
						startY = loc[l].y;
					} else if (block == 3) {
						startX = loc[l].x - 2;
						startY = loc[l].y - 2;
					}
					int matchCount = 0;
					if (startX >= 0 && startY >= 0 && startX <= (guessSize - ZONE_SIZE)
							&& startY <= (guessSize - ZONE_SIZE)) {
						for (int x = 0; x < ZONE_SIZE; x++) {
							for (int y = 0; y < ZONE_SIZE; y++) {
								if (zone[x][y] == guess[startX + x][startY + y]) {
									matchCount++;
								}
							}
						}
					}

					if (matchCount >= 8) {
						for (int x = 0; x < ZONE_SIZE; x = x + 2) {
							for (int y = 0; y < ZONE_SIZE; y = y + 2) {
								if (guess[startX + x][startY + y] == 0) {
									guess[startX + x][startY + y] = zone[x][y];
									guess[startX + x][startY + y + 1] = zone[x][y + 1];
									guess[startX + x + 1][startY + y] = zone[x + 1][y];
									guess[startX + x + 1][startY + y + 1] = zone[x + 1][y + 1];

									printData(guess, guessSize);

									int[][] data1 = new int[2][2];
									data1[0][0] = zone[x][y];
									data1[0][1] = zone[x][y + 1];
									data1[1][0] = zone[x + 1][y];
									data1[1][1] = zone[x + 1][y + 1];

									hash.addToHash(new HASH_NODE(data1, startX + x, startY + y));
								}
							}
						}
						return true;
					}
				}
				j = j + 2;
			}
			i = i + 2;
		}
		return false;
	}

	private void printData(char zone[][], int size) {
		// for (int i = 0; i < size; i++) {
		// for (int j = 0; j < size; j++) {
		// System.out.print((int) zone[i][j] + "\t");
		// }
		// System.out.println("");
		// }
		// System.out.println("===============");
	}
}
