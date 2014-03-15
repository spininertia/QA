package edu.cmu.nlp.annotator;

import java.util.*;
import java.util.Map.Entry;

import edu.cmu.nlp.tools.WordNet;
import edu.cmu.nlp.util.Chunk;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefChain.CorefMention;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;

import edu.cmu.nlp.util.Pair;

public class Annotator {

	private static StanfordCoreNLP stanfordnlp = null;

	static {
		Properties props = new Properties();
		props.put("annotators",
				"tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		stanfordnlp = new StanfordCoreNLP(props);
	}

	public static Annotation AnnotateDoc(String doc) {
		Annotation document = new Annotation(doc);
		stanfordnlp.annotate(document);
		return document;
	}

	public static List<CoreMap> getSentences(Annotation document){
		return document.get(SentencesAnnotation.class);
	}
	
	public static List<Chunk> annotatePOSandNE(CoreMap sentence) {
		List<Chunk> wordAnnotation = new ArrayList<Chunk>();
		for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
			String word = token.get(TextAnnotation.class);
			String pos = token.get(PartOfSpeechAnnotation.class);
			String ne = token.get(NamedEntityTagAnnotation.class);
			Chunk chunk = new Chunk(word, pos, ne);
			wordAnnotation.add(chunk);
		}
		return wordAnnotation;
	}

	public static Tree parseSentence(CoreMap sentence) {
		return sentence.get(TreeAnnotation.class);
	}

	public static SemanticGraph dependencyParsing(CoreMap sentence) {
		return sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
	}

	public static Map<Integer, CorefChain> decoref(Annotation document) {
		return document.get(CorefChainAnnotation.class);
	}
	
	public static HashMap<Integer, ArrayList<Pair>> translateCoref(Map<Integer, CorefChain> graph){
		HashMap<Integer, ArrayList<Pair>> result = new HashMap<Integer, ArrayList<Pair>>();
		Iterator<Entry<Integer, CorefChain>> iter = graph.entrySet().iterator();
		while(iter.hasNext()){
			Entry<Integer, CorefChain> e = iter.next();
			List<CorefMention> list = e.getValue().getMentionsInTextualOrder();
			for(CorefMention men : list){
				int sentId = men.mentionID;
				ArrayList<String> coref = new ArrayList<String>();
				for(CorefMention menInner : list){
					coref.add(menInner.mentionSpan);
				}
				
				if(!result.containsKey(sentId)){
					result.put(sentId, new ArrayList<Pair>());
				}
				result.get(sentId).add(new Pair(men.mentionSpan, coref));
			}
		}
		return result;
	}

	public static List<String> annotateSynonyms(CoreMap sentence){
		List<String> synonyms = new ArrayList<String>();
		for(CoreLabel token : sentence.get(TokensAnnotation.class)){
			String word = token.get(TextAnnotation.class);
			String pos = token.get(PartOfSpeechAnnotation.class);
			if(pos.startsWith("NN")){
				synonyms.addAll(WordNet.getNounSynonyms(word));
			}else if(pos.startsWith("VB")){
				synonyms.addAll(WordNet.getVerbSynonyms(word));
			}else if(pos.startsWith("JJ")){
				synonyms.addAll(WordNet.getAdjSynonyms(word));
			}else if(pos.startsWith("RB")){
				synonyms.addAll(WordNet.getAdverbSynonyms(word));
			}
		}
		return synonyms;
	}
	
	public static void main(String[] args){
		Annotation document = Annotator.AnnotateDoc("");
		List<CoreMap> sentences = Annotator.getSentences(document);
		HashMap<Integer, Sentence> map = new HashMap<Integer, Sentence>();
		int id = 0;
		for (CoreMap sentence : sentences) {
			++ id;
			List<Chunk> posAndNe =  annotatePOSandNE(sentence);
			Tree parseTree = parseSentence(sentence);
			//SemanticGraph dependencies = dependencyParsing(sentence);
			List<String> nounPhrases = AnnotatePhrase.getNounPhrases(parseTree);
			List<String> verbPhrases = AnnotatePhrase.getVerbPhrases(parseTree);
			List<String> synonyms = Annotator.annotateSynonyms(sentence);
			Sentence sent = new Sentence();
			map.put(id, sent);
			sent.setId(id);
			sent.setSentence(sentence.toString());
			sent.setTokens(posAndNe);
			sent.setNounPhrases(nounPhrases);
			sent.setVerbPhrases(verbPhrases);
			sent.setSynonyms(synonyms);
		}
		Map<Integer, CorefChain> coref = decoref(document);
		HashMap<Integer, ArrayList<Pair>> corefChain = translateCoref(coref);
		Iterator<Entry<Integer, ArrayList<Pair>>> iter = corefChain.entrySet().iterator();
		while(iter.hasNext()){
			Entry<Integer, ArrayList<Pair>> e = iter.next();
			map.get(e.getKey()).setCoref(e.getValue());
		}
		//System.out.println(map);
	}
}