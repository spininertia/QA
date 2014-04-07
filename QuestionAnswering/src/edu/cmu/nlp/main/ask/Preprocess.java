package edu.cmu.nlp.main.ask;

import edu.cmu.nlp.annotator.AnnotatePhrase;
import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Sentence;
import edu.cmu.nlp.tools.WordNet;
import edu.cmu.nlp.tools.WordNetSim;
import edu.cmu.nlp.util.Chunk;
import edu.cmu.nlp.util.Dict;
import edu.cmu.nlp.util.Pair;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;
import java.util.Map.Entry;

public class Preprocess {
	
	private static String getMostSimilar(String word, HashSet<String> words) {
		double maxval = -10.0;
		String newWord = "";

		for (String str : words) {
			double val = WordNetSim.computeSim(word, str);
			if(word.equals(str))
				continue;
			if (val > maxval) {
				maxval = val;
				newWord = str;
			}
		}
		return newWord;
	}

	private static String negation(Sentence sent, String POS) {
		String sentence = sent.getSentence();
		List<Chunk> tokens = sent.getTokens();
		for (Chunk chunk : tokens) {
			String word = chunk.getWord();
			HashSet<String> antos = null;
			if (chunk.getPos().startsWith(POS)) {
				if (POS.equals("VB"))
					antos = WordNet.getVerbAntonym(word);
				else if (POS.equals("JJ"))
					antos = WordNet.getAdjAntonym(word);
			}
			if (chunk.getPos().startsWith(POS)) {
				String newWord = getMostSimilar(word, antos);
				if (newWord.length() > 0)
					sentence = sentence.replaceFirst(word, newWord);
			}
		}
		return sentence;
	}

	public static String negationVerb(Sentence sent) {
		return negation(sent, "VB");
	}

	public static String negationAdj(Sentence sent) {
		return negation(sent, "JJ");
	}

	private static String synonymExp(Sentence sent, String POS) {
		String sentence = sent.getSentence();
		List<Chunk> tokens = sent.getTokens();
		for (Chunk chunk : tokens) {
			String word = chunk.getWord();
			HashSet<String> syno = null;
			if (chunk.getPos().startsWith(POS)) {
				if (POS.equals("JJ"))
					syno = WordNet.getAdjSynonyms(word);
				else if (POS.equals("VB"))
					syno = WordNet.getVerbSynonyms(word);
				else if (POS.equals("NN"))
					syno = WordNet.getNounSynonyms(word);
			}
			if(chunk.getPos().startsWith(POS)){
				String newWord = getMostSimilar(word, syno);
				if (newWord.length() > 0)
					sentence = sentence.replaceFirst(word, newWord);
			}
		}
		return sentence;
	}

	public static String synonymAdjExp(Sentence sent) {
		return synonymExp(sent, "JJ");
	}

	public static String synonymVerbExp(Sentence sent) {
		return synonymExp(sent, "VB");
	}

	public static String synonymNounExp(Sentence sent) {
		return synonymExp(sent, "NN");
	}

	public static String replaceCoref(Sentence sent) {
		List<Pair> corefList = sent.getCoref();
		List<Chunk> tokens = sent.getTokens();

		String sentence = sent.getSentence();
		for (Chunk chunk : tokens) {
			if (chunk.getPos().equals("PRP")) {

				String word = chunk.getWord();
				for (Pair pair : corefList) {
					if (pair.getWord().equals(word)) {
						List<String> coref = pair.getCoref();
						for (String str : coref) {
							if (!Dict.isPronoun(str)){
								sentence = sentence.replaceFirst(word, str);
								break;
							}
						}
					}
				}

			} else if (chunk.getPos().equals("PRP$")) {

				String word = chunk.getWord();
				for (Pair pair : corefList) {
					if (pair.getWord().equals(word)) {
						List<String> coref = pair.getCoref();
						for (String str : coref) {
							if (!Dict.isPronoun(str)){
								if(!str.endsWith("'s") && !str.endsWith("s'")){
									if(str.endsWith("s"))
										str += "'";
									else
										str += "'s";
								}
								sentence = sentence.replaceFirst(word, str);
								break;
							}
						}
					}
				}
			}
		}
		
		return sentence;
	}

	public static void main(String[] args) {
		
		Annotation document = Annotator.AnnotateDoc("Kosgi Santosh sent an email to Stanford University. He didn't get a reply. Lucy Cohen is a really nice and beautiful girl. Her dog is called Lily.");
		List<CoreMap> sentences = Annotator.getSentences(document);
		HashMap<Integer, Sentence> map = new HashMap<Integer, Sentence>();
		int id = 0;
		for (CoreMap sentence : sentences) {
			++ id;
			List<Chunk> posAndNe =  Annotator.annotatePOSandNE(sentence);
			Tree parseTree = Annotator.parseSentence(sentence);
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
		Map<Integer, CorefChain> coref = Annotator.decoref(document);
		HashMap<Integer, ArrayList<Pair>> corefChain = Annotator.translateCoref(coref);
		Iterator<Entry<Integer, ArrayList<Pair>>> iter = corefChain.entrySet().iterator();
		while(iter.hasNext()){
			Entry<Integer, ArrayList<Pair>> e = iter.next();
			map.get(e.getKey()).setCoref(e.getValue());
		}
		System.out.println(map);
		for(Sentence sent : map.values()){
			System.out.println(Preprocess.replaceCoref(sent));
			System.out.println(Preprocess.negationAdj(sent));
			System.out.println(Preprocess.synonymNounExp(sent));
		}
	}
}