package edu.cmu.nlp.annotator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.cmu.nlp.util.Chunk;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

public class Question {

	private String sentence = "";
	private String classification = "";
	private List<Chunk> tokens = new ArrayList<Chunk>();
	private List<String> nounPhrases = new ArrayList<String>();
	private List<String> verbPhrases = new ArrayList<String>();
	private Tree parsingTree;
	private SemanticGraph dependency;
	private String type;

	public Question() {
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Question{\n");
		sb.append("\tsentence:\t" + this.getSentence() + "\n");
		sb.append("\tclassification:\t" + this.getClassification() + "\n");
		sb.append("\ttokens:\t" + this.getTokens() + "\n");
		sb.append("\tnounPhrases:\t" + this.getNounPhrases() + "\n");
		sb.append("\tverbPhrases:\t" + this.getVerbPhrases() + "\n");
		sb.append("\tdependencie:" + this.getDependency() + "\n}\n");
		return sb.toString();
	}
	
	public Question(String sentence, List<Chunk> tokens,
			List<String> nounPhrases, List<String> verbPhrases) {
		this.setSentence(sentence);
		this.setTokens(tokens);
		this.setNounPhrases(nounPhrases);
		this.setVerbPhrases(verbPhrases);
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public List<Chunk> getTokens() {
		return tokens;
	}

	public void setTokens(List<Chunk> tokens) {
		this.tokens = tokens;
	}

	public List<String> getNounPhrases() {
		return nounPhrases;
	}

	public void setNounPhrases(List<String> nounPhrases) {
		this.nounPhrases = nounPhrases;
	}

	public List<String> getVerbPhrases() {
		return verbPhrases;
	}

	public void setVerbPhrases(List<String> verbPhrases) {
		this.verbPhrases = verbPhrases;
	}

	public Tree getParsingTree() {
		return parsingTree;
	}

	public void setParsingTree(Tree parsingTree) {
		this.parsingTree = parsingTree;
	}

	public SemanticGraph getDependency() {
		return dependency;
	}

	public void setDependency(SemanticGraph dependency) {
		this.dependency = dependency;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getWordPOS(String word){
		String pos = "";
		
		for(Chunk chunk : tokens){
			if(chunk.getWord().equals(word))
				return chunk.getPos();
		}
		return pos;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean containsNegation() {
		boolean negation = false;
		Iterator<TypedDependency> iter = dependency.typedDependencies().iterator();
		
		while (iter.hasNext()) {
			TypedDependency td = iter.next();
			if (td.reln().getShortName().equals("neg")) {
				negation = !negation;
			}
		}
		
		return negation;
	}
}
