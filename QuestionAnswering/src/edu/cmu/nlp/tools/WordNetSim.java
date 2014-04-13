package edu.cmu.nlp.tools;

import java.io.PrintStream;

import net.didion.jwnl.JWNLException;
import edu.cmu.nlp.util.Configuration;
import edu.illinois.cs.cogcomp.wnsim.WNSim;

public class WordNetSim {

	private static WNSim wnsim;
	static {
		try {
			PrintStream out = System.out;
			System.setOut(System.err);
			wnsim = WNSim.getInstance(Configuration.getWordnetPathForSim(),
					"wordnet.xml");
			System.setOut(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double computeSim(String word1, String word2) {
		double sim = 0.0;
		try {
			sim = wnsim.getWupSimilarity(word1, word2);
		} catch (JWNLException e) {
			e.printStackTrace();
		}
		return sim;
	}

	public static double computeSim(String word1, String partOfSpeech1,
			String word2, String partOfSpeech2) {
		double sim = 0.0;
		try {
			sim = wnsim.getWupSimilarity(word1, partOfSpeech1, word2,
					partOfSpeech2);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sim;
	}

	public static void main(String[] args){
		System.out.println(WordNetSim.computeSim("car", "fagfagfa"));
	}
	
}
