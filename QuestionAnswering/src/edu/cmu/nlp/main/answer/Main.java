package edu.cmu.nlp.main.answer;

import java.util.*;
import java.util.Map.*;

import org.apache.solr.common.SolrDocumentList;

import edu.cmu.nlp.Indexer.Indexer;
import edu.cmu.nlp.Indexer.Searcher;
import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;
import edu.cmu.nlp.qc.QC;
import edu.cmu.nlp.qc.QuestionTaxonomy;
import edu.cmu.nlp.util.Util;

public class Main {

	public static void main(String[] args) {
		String doc = "Kosgi Santosh sent an email to Stanford University. He didn't get a reply. Lucy Cohen is a really nice and beautiful girl. Her dog is called Lily.";
		String ques = "Did Kosgi Santosh send an email to Stanford University?";

		System.out.println("Begin Annotate Sentences------------------------------------------------------");
		HashMap<Integer, Sentence> map = Annotator.annotateDoc(doc);
		System.out.println(map);
		System.out.println("End Annotate Sentences--------------------------------------------------------");

		System.out.println("Begin Indexing Sentenes-------------------------------------------------------");
		Iterator<Entry<Integer, Sentence>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, Sentence> e = iter.next();
			Indexer.indexSentence(e.getValue());
		}
		System.out.println("End Indexing Sentenes---------------------------------------------------------");

		System.out.println("Begin Searching---------------------------------------------------------------");
		Question q = Annotator.annotateQuestion(ques);
		String solrQuery = Searcher.formSolrQuery(q);
		System.out.println(solrQuery);
		SolrDocumentList results = Searcher.search(solrQuery);
		Searcher.printSolrResult(results);
		System.out.println("End Searching-----------------------------------------------------------------");

		System.out.println("Begin Question Classify-------------------------------------------------------");
		String questionClass = QC.classifyQuestion(q);
		System.out.println(questionClass);
		System.out.println("End Question Classify---------------------------------------------------------");

		System.out.println("Begin Answer Question-------------------------------------------------------");

		String answer = "";
		if (questionClass.equals(QuestionTaxonomy.YES_OR_NO)) {
			if (results.size() == 0) {
				answer = "0No";
			} else {
				List<String> questionNes = Util.extractNEs(q.getTokens());

				Sentence candidate = map.get(Integer.valueOf(results.get(0).get("id").toString()));
				List<String> candidateNes = Util.extractNEs(candidate.getTokens());
				System.out.println(candidateNes);
				System.out.println(questionNes);
				if (!sameNESet(questionNes, candidateNes)) {
					answer = "1No";
				} else {
					if (q.containsNegation() == candidate.containsNegation()) {
						answer = "Yes";
					} else {
						answer = "2No";
					}
				}
			}
		} else {
			Sentence candidate = map.get(Integer.valueOf(results.get(0).get("id").toString()));
			answer = candidate.getSentence();
		}

		System.out.println(answer);
		System.out.println("End Answer Question-------------------------------------------------------");
	}

	public static boolean sameNESet(List<String> qNEs, List<String> cNEs) {
		for (String ne : cNEs) {
			if (!qNEs.contains(ne)) {
				return false;
			}
		}

		return true;
	}
}
