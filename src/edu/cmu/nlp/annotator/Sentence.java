package edu.cmu.nlp.annotator;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.nlp.util.*;

public class Sentence {

	private int id;
	private String sentence = "";
	private List<Chunk> tokens = new ArrayList<Chunk>();
	private List<String> nounPhrases = new ArrayList<String>();
	private List<String> verbPhrases = new ArrayList<String>();
	private List<Pair> coref = new ArrayList<Pair>();
	private List<String> synonyms = new ArrayList<String>();

	public Sentence() {
	}

	public Sentence(int id, String sentence, List<Chunk> tokens,
			List<String> nounPhrases, List<String> verbPhrases,
			List<Pair> coref, List<String> synonyms) {
		this.setId(id);
		this.setSentence(sentence);
		this.setTokens(tokens);
		this.setNounPhrases(nounPhrases);
		this.setVerbPhrases(verbPhrases);
		this.setCoref(coref);
		this.setSynonyms(synonyms);
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
}
