package edu.cmu.nlp.tools;

import java.util.Arrays;
import java.util.HashSet;

import edu.smu.tspell.wordnet.AdjectiveSynset;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.VerbSynset;
import edu.smu.tspell.wordnet.AdverbSynset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;
import edu.cmu.nlp.util.Configuration;

public class WordNet {
	
	private static WordNetDatabase database = null;
	
	static {
		System.setProperty("wordnet.database.dir",
				Configuration.getWordnetDatabasePath());
		database = WordNetDatabase.getFileInstance();
	}

	public static HashSet<String> getNounSynonyms(String noun){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(noun, SynsetType.NOUN)) {
			NounSynset nounSynset = (NounSynset) synset;
			for(String str : nounSynset.getWordForms())
				result.add(str);
		}
		return result;
	} 
	
	public static HashSet<String> getAdverbSynonyms(String adverb){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(adverb, SynsetType.ADVERB)) {
			AdverbSynset adverbSynset = (AdverbSynset) (synset);
			for(String str : adverbSynset.getWordForms())
				result.add(str);
		}
		return result;
	}
	
	public static HashSet<String> getAdverbAntonym(String adverb){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(adverb, SynsetType.ADVERB)) {
			AdverbSynset adverbSynset = (AdverbSynset) (synset);
			for(WordSense sense : adverbSynset.getAntonyms(adverb))
				result.add(sense.getWordForm());
		}
		return result;
	}
	
	public static HashSet<String> getVerbSynonyms(String verb){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(verb, SynsetType.VERB)) {
			VerbSynset verbSynset = (VerbSynset) (synset);
			for(String str : verbSynset.getWordForms())
				result.add(str);
		}
		return result;
	}
	
	public static HashSet<String> getVerbAntonym(String verb){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(verb, SynsetType.VERB)) {
			VerbSynset verbSynset = (VerbSynset) (synset);
			for(WordSense sense : verbSynset.getAntonyms(verb))
				result.add(sense.getWordForm());
		}
		return result;
	}
	
	public static HashSet<String> getAdjSynonyms(String adj){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(adj, SynsetType.ADJECTIVE)) {
			AdjectiveSynset adjSynset = (AdjectiveSynset) (synset);
			for(String str : adjSynset.getWordForms())
				result.add(str);
		}
		return result;
	}
	
	public static HashSet<String> getAdjAntonym(String adj){
		HashSet<String> result = new HashSet<String>();
		for (Synset synset : database.getSynsets(adj, SynsetType.ADJECTIVE)) {
			AdjectiveSynset adjSynset = (AdjectiveSynset) (synset);
			for(WordSense sense : adjSynset.getAntonyms(adj))
				result.add(sense.getWordForm());
		}
		return result;
	}
	
	public static HashSet<String> getNounHypernym(String noun) {
		HashSet<String> hypernyms = new HashSet<String>();
		for (Synset synset : database.getSynsets(noun, SynsetType.NOUN)) {
			NounSynset nounSynset = (NounSynset) synset;
			NounSynset[] hypernymSynset = nounSynset.getHypernyms();
			for (NounSynset set : hypernymSynset) {
				hypernyms.addAll(Arrays.asList(set.getWordForms()));
			}
		}
		return hypernyms;
	}
	
	public static HashSet<String> getNounHypernym(String noun, int level){
		HashSet<String> hypernyms = new HashSet<String>();
		
		if(level <= 0){
		} else {
			for(String str : getNounHypernym(noun)){
				hypernyms.add(str);
				hypernyms.addAll(getNounHypernym(str, level - 1));
			}
		}
		
		return hypernyms;
	}
	
	public static HashSet<String> getVerbHypernym(String verb) {
		HashSet<String> hypernyms = new HashSet<String>();
		for (Synset synset : database.getSynsets(verb, SynsetType.VERB)) {
			VerbSynset verbSynset = (VerbSynset) synset;
			VerbSynset[] hypernymSynset = verbSynset.getHypernyms();
			for (VerbSynset set : hypernymSynset) {
				hypernyms.addAll(Arrays.asList(set.getWordForms()));
			}
		}

		return hypernyms;
	}
	
	public static HashSet<String> getVerbHypernym(String verb, int level){
		HashSet<String> hypernyms = new HashSet<String>();
		
		if(level == 0){
		} else {
			for(String str : getVerbHypernym(verb)){
				hypernyms.add(str);
				hypernyms.addAll(getVerbHypernym(str, level - 1));
			}
		}
		
		return hypernyms;
	}
	
	public static void main(String[] args) {
		System.out.println(WordNet.getNounSynonyms("car"));
		System.out.println(WordNet.getVerbSynonyms("like"));
		System.out.println(WordNet.getVerbAntonym("like"));
		System.out.println(WordNet.getAdjSynonyms("smart"));
		System.out.println(WordNet.getAdjAntonym("similar"));
		System.out.println(WordNet.getNounHypernym("cars"));
		System.out.println(WordNet.getVerbHypernym("like"));
		System.out.println(WordNet.getNounHypernym("cars", 3));
		System.out.println(WordNet.getVerbHypernym("like", 3));
	}
}
