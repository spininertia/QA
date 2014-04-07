package edu.cmu.nlp.annotator;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.Tree;

/**
 * Get various kinds of phrases based on the Parsing tree
 */
public class AnnotatePhrase {
	
	// For a reference of all the phrase tags, go to www.clips.ua.ac.be/pages/mbsp-tags
	
	private static List<String> getPhrases(Tree parse, String type) {
		List<String> phraseList = new ArrayList<String>();
		for (Tree subtree : parse) {
			if (subtree.label().value().equals(type)) {
				StringBuilder sb = new StringBuilder();
				for (Tree tree : subtree.preOrderNodeList()) {
					if (tree.isLeaf())
						sb.append(tree.nodeString() + " ");
				}
				phraseList.add(sb.toString().substring(0, sb.length() - 1));
				//System.out.println(subtree);
			}
		}
		return phraseList;
	}

	public static List<String> getNounPhrases(Tree parse) {
		return getPhrases(parse, "NP");
	}

	public static List<String> getVerbPhrases(Tree parse) {
		return getPhrases(parse, "VP");
	}
	
	public static List<String> getPrepPhrases(Tree parse) {
		return getPhrases(parse, "PP");
	}
	
	public static List<String> getAdjPhrases(Tree parse) {
		return getPhrases(parse, "ADJP");
	}
	
	public static List<String> getAdvPhrases(Tree parse){
		return getPhrases(parse, "ADVP");
	}
}
