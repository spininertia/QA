package edu.cmu.nlp.util;

public class BinaryFeatureVector {

	private int[] vec;
	
	public BinaryFeatureVector(int len){
		vec = new int[len];
	}
	
	public boolean set(int idx){
		if(idx < 0 || idx >= vec.length){
			return false;
		}
		vec[idx] = 1;
		return true;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i : vec){
			sb.append(i + " ");
		}
		return sb.toString().trim();
	}
}
