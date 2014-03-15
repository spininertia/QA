package edu.cmu.nlp.util;

import java.io.*;
import java.util.*;

public class Dict {

	private static HashMap<String, Integer> dict = new HashMap<String, Integer>();
	private static int size = 0;
	
	static {
		try {
			Scanner sc = new Scanner(new FileInputStream(
					Configuration.getWordListPath()));
			int id = 0;
			while (sc.hasNextLine()) {
				dict.put(sc.nextLine(), id++);
			}
			setSize(dict.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getWordIdx(String word) {
		if (dict.containsKey(word)) {
			return dict.get(word);
		}
		return -1;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		Dict.size = size;
	}
}
