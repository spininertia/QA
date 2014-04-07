package edu.cmu.nlp.qc;

import java.util.List;

import edu.cmu.nlp.annotator.Question;
import edu.stanford.nlp.trees.Tree;

public class QuestionInversion {
	
	public static String invertYesNoQuestion(Question question){
		
		Tree tree = question.getParsingTree();
		StringBuilder sb = new StringBuilder();
		Tree treeN = null;
		Tree treeV = null;
		boolean n = false;
		boolean v = false;
		List<Tree> list = tree.preOrderNodeList();
		for(Tree subtree : list){
			if(subtree.toString().startsWith("(N") && !n){
				n = true;
				treeN = subtree;
			}
			if((subtree.toString().startsWith("(VB") || subtree.toString().startsWith("(M")) && !v){
				v = true;
				treeV = subtree;
			}
		}
		
		for (Tree subtree : tree.preOrderNodeList()) {
			if (subtree.isLeaf())
				sb.append(subtree.nodeString() + " ");
		}
		
		String[] words = sb.toString().trim().split("\\s+");
		
		sb = new StringBuilder();
		for (Tree subtree : treeN.preOrderNodeList()) {
			if (subtree.isLeaf())
				sb.append(subtree.nodeString() + " ");
		}
		String[] wordnps = sb.toString().trim().split("\\s+");
		
		sb = new StringBuilder();
		for (Tree subtree : treeV.preOrderNodeList()) {
			if (subtree.isLeaf())
				sb.append(subtree.nodeString() + " ");
		}
		String[] wordvs = sb.toString().trim().split("\\s+");
		
		int i = 0;
		for(int j = 0; j < wordnps.length; ++ j){
			words[i] = wordnps[j];
			++ i;
		}
		for(int j = 0; j < wordvs.length; ++ j){
			words[i] = wordvs[j];
			++ i;
		}
		
		sb = new StringBuilder();
		
		for(int j = 0; j < words.length; ++ j)
			sb.append(words[j] + " ");
		
		return sb.toString().trim();
	}

}
