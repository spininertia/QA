package edu.cmu.nlp.util;

import weka.classifiers.Classifier;

import java.io.*;

/**
 * Description: print out models to files
 */
public class Printer {

	private FileOutputStream fos = null;
	private PrintStream ps = null;

	public Printer(String path) {
		try {
			this.fos = new FileOutputStream(path);
			this.ps = new PrintStream(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeLine(String str) {
		this.ps.println(str);
	}

	public static void outputModel(String path, Classifier classify) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(path));
			oos.writeObject(classify);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
