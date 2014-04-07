package edu.cmu.nlp.util;

public class Chunk {

	private String word;
	private String pos;
	private String ne;
	
	public Chunk(){
	}
	
	public Chunk(String word, String pos, String ne){
		this.setWord(word);
		this.setPos(pos);
		this.setNe(ne);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getNe() {
		return ne;
	}

	public void setNe(String ne) {
		this.ne = ne;
	}
	
	public String toString(){
		return "{" + this.getWord() + "," + this.getPos() + "," + this.getNe() + "}";
	}
	
	public static void main(String[] args){
		Chunk test = new Chunk("word", "POS", "NE");
		System.out.println(test);
	}
}
