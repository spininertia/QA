package edu.cmu.nlp.util;

import java.io.*;
import java.util.*;

public class Dict {

	private static HashMap<String, Integer> qcDict = new HashMap<String, Integer>();
	private static HashMap<String, Integer> dict = new HashMap<String, Integer>();
	private static int size = 0;
	private static HashSet<String> pronounDict = new HashSet<String>();
	private static String[] negateWords = {"except"};
	public static HashSet<String> negations = new HashSet<String>();

	static {
		for (String word : negateWords) {
			negations.add(word);
		}
		try {
			Scanner sc = new Scanner(new FileInputStream(Configuration.getWordListPath()));
			int id = 0;
			while (sc.hasNextLine()) {
				dict.put(sc.nextLine().toLowerCase(), id++);
			}
			setSize(dict.size());

			sc = new Scanner(new FileInputStream(Configuration.getPronounListPath()));
			while (sc.hasNextLine()) {
				pronounDict.add(sc.nextLine());
			}

			Scanner sc1 = new Scanner(new File(Configuration.getQuestionTrainPath()));
			while (sc1.hasNextLine()) {
				String buffer = sc1.nextLine();
				String[] strs = buffer.split("\\s+");
				int iter = 0;
				for (int i = 1; i < strs.length - 1; ++i) {
					if (!qcDict.containsKey(strs[i].toLowerCase())) {
						qcDict.put(strs[i].toLowerCase(), iter++);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isPronoun(String word) {
		return pronounDict.contains(word.toLowerCase());
	}

	public static int getWordIdx(String word) {
		if (dict.containsKey(word.toLowerCase())) {
			return dict.get(word.toLowerCase());
		}
		return -1;
	}

	public static int getQcWordIdx(String word) {
		if (qcDict.containsKey(word.toLowerCase())) {
			return qcDict.get(word.toLowerCase());
		}
		return -1;
	}

	public static int getSize() {
		return size;
	}

	public static int getQcDictSize() {
		return qcDict.size();
	}

	public static void setSize(int size) {
		Dict.size = size;
	}

	public static void main(String[] args) {
		System.out.println(Dict.getSize());
		System.out.println(Dict.getQcDictSize());
	}
}
