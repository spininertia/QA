package edu.cmu.nlp.qc;

import java.util.*;
import java.util.Map.*;

import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.tools.WordNet;
import edu.cmu.nlp.tools.WordNetSim;
import edu.cmu.nlp.util.*;

public class FeatureExtraction {

	private static HeadFinderWh headFinderWh;
	private static ModCollinsHeadFinderLK headFinderLK;
	
	static {
		headFinderWh = new HeadFinderWh();
		headFinderLK = new ModCollinsHeadFinderLK();
	}
	
	public FeatureExtraction(){
	}
	
	public static String extractFeatures(Question question){
		String featureVector = "";
		
		//WH feature vector
		String whWord = question.getParsingTree().headTerminal(headFinderWh).toString();
		BinaryFeatureVector whVec = new BinaryFeatureVector(8);
		if(WhWord.whMap.containsKey(whWord.toLowerCase())){
			whVec.set(WhWord.whMap.get(whWord.toLowerCase()));
		} else {
			whVec.set(7);
		}
		String whFV = whVec.toString();
		
		BinaryFeatureVector wordVec = new BinaryFeatureVector(Dict.getQcDictSize());
		String headWord = question.getParsingTree().headTerminal(headFinderLK).toString();
		if(Dict.getQcWordIdx(headWord.toLowerCase()) >= 0)
			wordVec.set(Dict.getQcWordIdx(headWord.toLowerCase()));
		String headCat = question.getWordPOS(headWord);
		HashSet<String> hypernyms = new HashSet<String>();
		if(headCat.startsWith("N")){
			hypernyms = WordNet.getNounHypernym(headWord, Configuration.getHypernymLevel());
		} else if(headCat.startsWith("V")){
			hypernyms = WordNet.getVerbHypernym(headWord, Configuration.getHypernymLevel());
		}
		for(String hypernym : hypernyms){
			if(Dict.getQcWordIdx(hypernym.toLowerCase()) >= 0)
				wordVec.set(Dict.getQcWordIdx(hypernym.toLowerCase()));
		}
		//Add unigram
		for(Chunk chunk : question.getTokens()){
			if(Dict.getQcWordIdx(chunk.getWord().toLowerCase()) >= 0)
				wordVec.set(Dict.getQcWordIdx(chunk.getWord().toLowerCase()));
		}
		String wordFV = wordVec.toString();
		
		//indirect hypernym feature vector general
		String indirectHypernymGeneral = indirectHypernymGeneral(headWord);
		BinaryFeatureVector hyperVec1 = new BinaryFeatureVector(6);
		hyperVec1.set(QuestionTaxonomy.generalCatIdx.get(indirectHypernymGeneral));
		String hyperGeneralFV = hyperVec1.toString();
		
		//indirect hypernym feature vector deep
		String indirectHypernymDeep = indirectHypernymDeep(headWord);
		BinaryFeatureVector hyperVec2 = new BinaryFeatureVector(50);
		hyperVec2.set(QuestionTaxonomy.deepCatIdx.get(indirectHypernymDeep));
		String hyperDeepFV = hyperVec2.toString();
		
		featureVector = cancatenate(whFV, wordFV, hyperGeneralFV, hyperDeepFV);
		return featureVector;
	}
	
	private static String cancatenate(String ... strs){
		StringBuilder sb = new StringBuilder();
		for(String str : strs){
			sb.append(str + " ");
		}
		return sb.toString().trim();
	}
	
	private static String indirectHypernym(String head, HashMap<String, List<String>> map){
		String cate = null;
		double max = -1.0;
		
		Iterator<Entry<String, List<String>>> iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, List<String>> e = iter.next();
			String tempCat = e.getKey();
			for(String desc : e.getValue()){
				double sim = WordNetSim.computeSim(head, desc);
				if(max < sim){
					max = sim;
					cate = tempCat;
				}
			}
		}
		
		return cate;
	}
	
	public static String indirectHypernymGeneral(String head){
		return indirectHypernym(head, QuestionTaxonomy.generalDesc);
	}
	
	public static String indirectHypernymDeep(String head){
		return indirectHypernym(head, QuestionTaxonomy.deepDesc);
	}
	
	public static void main(String[] args){
		String str = "What does William Cohen do?";
		Question question = Annotator.annotateQuestion(str);
		String featureVector = FeatureExtraction.extractFeatures(question);
		System.out.println(featureVector);
		System.out.println(featureVector.split("  *").length);
	}
}

class WhWord{
	public static String what = "what";
	public static String which = "which";
	public static String when = "when";
	public static String where = "where";
	public static String who = "who";
	public static String how = "how";
	public static String why = "why";
	public static String rest = "rest";
	
	public static HashMap<String, Integer> whMap = new HashMap<String, Integer>();
	
	static {
		whMap.put(what, 0);
		whMap.put(which, 1);
		whMap.put(when, 2);
		whMap.put(where, 3);
		whMap.put(who, 4);
		whMap.put(how, 5);
		whMap.put(why, 6);
		whMap.put(rest, 7);
	}
}