package edu.cmu.nlp.util;

import java.util.List;

public class Pair{
	private String word;
	private List<String> coref;
	
	public Pair(){
	}
	
	public Pair(String word){
		this.setWord(word);
	}
	
	public Pair(String word, List<String> coref){
		this.setWord(word);
		this.setCoref(coref);
	}

	public String toString(){
		return "{" + this.getWord() + "," + this.getCoref() + "}";
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public List<String> getCoref() {
		return coref;
	}

	public void setCoref(List<String> coref) {
		this.coref = coref;
	}
}