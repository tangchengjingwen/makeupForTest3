import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PerfectHash {
	private String[][] hashTable;

	public PerfectHash(int size) {
		hashTable = new String[size][];
	}

	public void insert(String key) {
		int index = key.hashCode();
		int size = hashTable.length;
		int hash1 = Math.abs(index % size);

		if (hashTable[hash1] == null) {
			hashTable[hash1] = new String[1];
			hashTable[hash1][0] = key;
		} else {
			int hash2 = Math.abs(index % hashTable[hash1].length);
			int tempLength = hashTable[hash1].length;
			while (hashTable[hash1][hash2] != null) {
				tempLength += 2;
				String[] tempArray = new String[tempLength];
				for (int i = 0; i < hashTable[hash1].length; ++i) {
					if (hashTable[hash1][i] != null) {
						int tempIndex = Math.abs(hashTable[hash1][i].hashCode() % tempLength);
						if (tempArray[tempIndex] != null) {
							tempLength += 2;
							tempArray = new String[tempLength];
							i = 0;
						} else {
							tempArray[tempIndex] = hashTable[hash1][i];
						}
					}
				}
				hashTable[hash1] = tempArray;
				hash2 = Math.abs(index % hashTable[hash1].length);
			}
			hashTable[hash1][hash2] = key;
		}
	}

	public boolean search(String word) {
		int hash1 = Math.abs(word.hashCode() % hashTable.length);
		if (hashTable[hash1] != null) {
			int hash2 = Math.abs(word.hashCode() % hashTable[hash1].length);
			if (hashTable[hash1][hash2] != null) {
				return hashTable[hash1][hash2].equals(word);
			}
		}
		return false;
	}

	public void printTable() {
		System.out.println("===================================================================================");
		for (int i = 0; i < hashTable.length; ++i) {
			System.out.printf("Slot %d:", i);
			if (hashTable[i] != null) {
				for (int j = 0; j < hashTable[i].length; ++j) {
					System.out.printf(" %s", hashTable[i][j]);
				}
			}
			System.out.println();
		}
	}

	public void load(String filename) throws FileNotFoundException {
		FileReader r = new FileReader(filename);
		Scanner s = new Scanner(r);
		while (s.hasNext()) {
			String word = s.next();
			insert(word);
		}
		s.close();
	}

	public static void main(String[] args) throws FileNotFoundException {

		PerfectHash p = new PerfectHash(50);
		PerfectHash p2 = new PerfectHash(80);
		PerfectHash p3 = new PerfectHash(100);
		PerfectHash p4 = new PerfectHash(150);
		PerfectHash p5 = new PerfectHash(200);
		p.load("c++Keywords");
		p.printTable();
		p2.load("c++Keywords");
		p2.printTable();
		p3.load("c++Keywords");
		p3.printTable();
		p4.load("c++Keywords");
		p4.printTable();
		p5.load("c++Keywords");
		p5.printTable();

	}
}
