package edu.cmu.nlp.annotator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.cmu.nlp.util.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.TypedDependency;

public class Sentence {

	private int id;
	private String sentence = "";
	private List<Chunk> tokens = new ArrayList<Chunk>();
	private List<String> nounPhrases = new ArrayList<String>();
	private List<String> verbPhrases = new ArrayList<String>();
	private List<Pair> coref = new ArrayList<Pair>();
	private List<String> synonyms = new ArrayList<String>();
	private SemanticGraph dependency;

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("SENTENCE{\n");
		sb.append("\tid:\t" + this.getId() + "\n");
		sb.append("\ttokens:\t" + this.getTokens() + "\n");
		sb.append("\tnounPhrases:\t" + this.getNounPhrases() + "\n");
		sb.append("\tverbPhrases:\t" + this.getVerbPhrases() + "\n");
		sb.append("\tcoref:\t" + this.getCoref() + "\n");
		sb.append("\tsynonyms:\t" + this.getSynonyms() + "\n");
		sb.append("}\n");
		return sb.toString();
	}
	
	public Sentence() {
	}
	
	public Sentence(int id, String sentence, List<Chunk> tokens,
			List<String> nounPhrases, List<String> verbPhrases,
			List<Pair> coref, List<String> synonyms, SemanticGraph edpendency) {
		this.setId(id);
		this.setSentence(sentence);
		this.setTokens(tokens);
		this.setNounPhrases(nounPhrases);
		this.setVerbPhrases(verbPhrases);
		this.setCoref(coref);
		this.setSynonyms(synonyms);
		this.setDependency(edpendency);
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

	public List<Pair> getCoref() {
		return coref;
	}

	public void setCoref(List<Pair> coref) {
		this.coref = coref;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public SemanticGraph getDependency() {
		return dependency;
	}

	public void setDependency(SemanticGraph dependency) {
		this.dependency = dependency;
	}

	public boolean containsNegation() {
		boolean negation = false;
		Iterator<TypedDependency> iter = getDependency().typedDependencies().iterator();
		
		while (iter.hasNext()) {
			TypedDependency td = iter.next();
			if (td.reln().getShortName().equals("neg")) {
				negation = !negation;
			}
		}

		return negation;
	}
	
	public boolean containsNE(String ne) {
		for (Chunk token : getTokens()) {
			if (token.getNe().equals(ne)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsPrep() {
		Iterator<TypedDependency> iter = getDependency().typedDependencies().iterator();
		
		while(iter.hasNext()) {
			TypedDependency td = iter.next();
			if (td.reln().getShortName().startsWith("prep")) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsWord(String word) {
		for (Chunk token : getTokens()) {
			if (token.getWord().equalsIgnoreCase(word)) {
				return true;
			}
		}
		
		return false;
	}
}
