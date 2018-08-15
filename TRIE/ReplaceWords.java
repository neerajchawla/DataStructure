/*
Problem: Replace Words
Description: In English, we have a concept called root, which can be followed by some other words to form another longer word - let's call this word successor. For example, the root an, followed by other, which can form another word another.
Now, given a dictionary consisting of many roots and a sentence. You need to replace all the successor in the sentence with the root forming it. If a successor has many roots can form it, replace it with the root with the shortest length.
You need to output the sentence after the replacement.
Link: https://leetcode.com/problems/replace-words/description/
*/

import java.util.LinkedList;
import java.util.List;

public class ReplaceWords {
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

	private String searchInTrie(String input) {
		String out = "";
		char[] data = input.toCharArray();
		int strlen = data.length;
		TRIE_NODE traverse = trie;
		for (int i = 0; i < strlen; i++) {
			out = out + data[i];
			int index = data[i] - 'a';
			TRIE_NODE val = traverse.next[index];
			if (val == null) {
				return input;
			}
			if (traverse.next[index].isEOS) {
				return out;
			}
			traverse = traverse.next[index];
		}
		return input;
	}

	public String replaceWords(List<String> dict, String sentence) {
		String out = "";
		initalize();
		int dicSize = dict.size();
		for (int i = 0; i < dicSize; i++) {
			String word = dict.get(i);
			insertToTrie(word);
		}
		String[] words = sentence.split(" ");
		for (int i = 0; i < words.length; i++) {
			String updatedWord = searchInTrie(words[i]);
			out = out + updatedWord;
			if (i < words.length - 1) {
				out = out + " ";
			}
		}
		return out;
	}

	public static void main(String[] args) {
		ReplaceWords trie = new ReplaceWords();
		List<String> root = new LinkedList<String>();
		root.add("cat");
		root.add("rat");
		root.add("bat");
		String sen = "the cattle was rattled by the battery";
		System.out.println(trie.replaceWords(root, sen));
	}

}
