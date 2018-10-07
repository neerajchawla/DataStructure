import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
	private final static int MAX_MAP_SIZE = 64;
	private final static int ZONE_SIZE = 4;
	private static char[][] input;
	private static Scanner sc;

	public static void main(String[] args) throws FileNotFoundException {
		Solution obj = new Solution();
		UserSolution usObj = new UserSolution();
		System.setIn(new FileInputStream("res/input.txt"));
		sc = new Scanner(System.in);
		int testcases = sc.nextInt();
		for (int tc = 0; tc < testcases; tc++) {
			int max_calls = sc.nextInt();
			int n = sc.nextInt();
			input = new char[n][n];
			char[][] guess = new char[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					input[i][j] = (char) sc.nextInt();
					if (i == 0 || i == 1 || i == n - 2 || i == n - 1 || j == 0 || j == 1 || j == n - 2 || j == n - 1) {
						guess[i][j] = input[i][j];
					} else {
						guess[i][j] = 0;
					}
				}
			}
			usObj.reconstruct(n, guess, max_calls);
			boolean answer = true;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (input[i][j] != guess[i][j]) {
						answer = false;
					}
				}
			}
			if (answer)
				System.out.println("Testcase# " + tc + " 100");
			else
				System.out.println("Testcase# " + tc + " -1");

		}

		sc.close();
	}

	public static void randomscan(char[][] zone) {
		for (int i = 0; i < ZONE_SIZE; i++) {
			for (int j = 0; j < ZONE_SIZE; j++) {
				zone[i][j] = (char) sc.nextInt();
			}
		}
	}

}
