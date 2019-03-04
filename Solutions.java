import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Node {
	int hashValue = 0;
	int time = 0;

	public Node(int hash, int t) {
		this.hashValue = hash;
		this.time = t;
	}
}

public class Solutions {

	static int[][] iArray = null;
	static int row;
	static int col;
	static int sIndex = -1;
	static Node[] sArray;

	public static void main(String[] args) throws FileNotFoundException {
		System.setIn(new FileInputStream(new File("res/input.txt")));

		Scanner sc = new Scanner(System.in);
		int test_cases = sc.nextInt();
		for (int i = 1; i <= test_cases; i++) {
			row = sc.nextInt();
			col = sc.nextInt();

			int t_result = sc.nextInt();
			sIndex = -1;
			sArray = new Node[1000];
			iArray = new int[row][col];

			for (int r = 0; r < row; r++) {
				for (int c = 0; c < col; c++) {
					iArray[r][c] = sc.nextInt();
				}
			}
			int result = runAlgo(t_result);
//			printArray();
			System.out.println("#" + i + " " + result);
		}
		sc.close();
	}

	private static int runAlgo(int t_result) {
//		System.out.println("t:: " + 1);
		boolean bRepeat = false;
		int currentTime = 0;
		int repeatTime = 1;

//		printArray();
		int hash = getHash();
		insertionSort(new Node(hash, 1));
//		printSortedArray();
//		System.out.println("=============");
		for (int t = 2; t <= t_result; t++) {
			currentTime = t;
//			System.out.println("t:: " + t);
			if (t % 2 == 0) {
				setMines();
			} else {
				blastMines();
			}

//			printArray();
			hash = getHash();
			int time = insertionSort(new Node(hash, t));
//			printSortedArray();

//			System.out.println("=============");
			if (time != -1) {
				bRepeat = true;
				repeatTime = time;
				break;
			}

//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}

//		System.out.println("current time: " + currentTime);
//		System.out.println("repeat time: " + repeatTime);
		int initVal = 0;
		if (bRepeat) {
			int t_diff = currentTime - repeatTime;
			initVal = t_result % t_diff;
		} else {
			initVal = t_result;
		}

		int result = 0;
		for (int i = 0; i < sIndex; i++) {
			if (sArray[i].time == initVal) {
				result = sArray[i].hashValue;
			}
		}

		return result;

	}

	private static void blastMines() {
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (iArray[r][c] == 2) {
					resetPosition(r, c);
					resetPosition(r - 1, c);
					resetPosition(r + 1, c);
					resetPosition(r, c - 1);
					resetPosition(r, c + 1);
				}
			}
		}
	}

	private static void resetPosition(int r, int c) {
		if (r >= 0 && r < row && c >= 0 && c < col) {
			iArray[r][c] = 0;
		}
	}

	private static void setMines() {
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				iArray[r][c] = iArray[r][c] + 1;
			}
		}
	}

	private static int getHash() {
		int hash_value = 0;
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				hash_value = 31 * hash_value + iArray[r][c];
			}
		}
//		System.out.println(hash_value);
		return hash_value;
	}

	private static void printArray() {
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				System.out.print(iArray[r][c] + " ");
			}
			System.out.println("");
		}
	}

	private static int insertionSort(Node data) {
		int i = sIndex;
		for (; i >= 0; i--) {
			if (sArray[i].hashValue == data.hashValue) {
				sArray[i + 1] = data;
				sIndex = sIndex + 1;
				return sArray[i].time;
			} else if (sArray[i].hashValue < data.hashValue) {
				sArray[i + 1] = data;
				sIndex = sIndex + 1;
				return -1;
			} else {
				sArray[i + 1] = sArray[i];
			}
		}
		sArray[i + 1] = data;
		sIndex = sIndex + 1;
		return -1;
	}

	private static void printSortedArray() {
		System.out.print("Hash: ");
		for (int i = 0; i <= sIndex; i++) {
			System.out.print(sArray[i].hashValue + " ");
		}
		System.out.println("");
	}
}
