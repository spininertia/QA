package edu.cmu.nlp.util;

import java.util.*;

public class Util {
	
	public static String cancatenate(String[] arr, int begin, int end){
		StringBuilder sb = new StringBuilder();
		for(int i = begin; i < end; ++ i){
			sb.append(arr[i] + " ");
		}
		return sb.toString().trim();
	}
	
	public static String generateVector(int from, int len, String delimit){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < len - 1; ++ i){
			sb.append(from + i + delimit);
		}
		sb.append(from + len - 1);
		return sb.toString();
	}
	
	public static void main(String[] args){
	}
	
	public static int[] String2Arr(String vec){
		String[] strs = vec.split("  *");
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < strs.length; ++ i){
			if(strs[i].trim().equals("1"))
				list.add(i);
		}
		int[] result = new int[list.size()];
		for(int i = 0; i < list.size(); ++ i){
			result[i] = list.get(i);
		}
		return result;
	}
	
	public static List<String> extractNEs(List<Chunk> chunks) {
		ArrayList<String> nes = new ArrayList<String>();
		for (Chunk chunk : chunks) {
			if(!chunk.getNe().equals("O"))
				nes.add(chunk.getWord());
		}
		return nes;
	}

	public static List<String> extractCoref(List<Pair> pairs) {
		ArrayList<String> result = new ArrayList<String>();
		for (Pair pair : pairs) {
			result.addAll(pair.getCoref());
		}
		return result;
	}
}
