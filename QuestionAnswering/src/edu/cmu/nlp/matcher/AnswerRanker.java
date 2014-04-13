package edu.cmu.nlp.matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;
import edu.cmu.nlp.filter.AbstractFilter;
import edu.cmu.nlp.filter.QuestionTypeFilter;
import edu.cmu.nlp.qc.SimpleQuestionClassifier;
import edu.cmu.nlp.util.Util;
import edu.stanford.nlp.trees.TypedDependency;

public class AnswerRanker {
	
	public void scoreBagOfWords(Question question, Sentence candidate) {
		List<String> qWords = Util.stemSent(question.getTokens());
		List<String> sWords = Util.stemSent(candidate.getTokens());

		HashSet<String> matched = new HashSet<String>();

		for (String qWord : qWords) {
			for (String sWord : sWords) {
				if (!matched.contains(qWord) && qWord.equals(sWord)) {
					matched.add(qWord);
				}
			}
		}
		candidate.setBowMatchScore(1.0f * matched.size() / qWords.size());
	}

	public void scoreDependency(Question question, Sentence candidate) {
		Collection<TypedDependency> qDeps = question.getDependency().typedDependencies();
		Collection<TypedDependency> sDeps = candidate.getDependency().typedDependencies();

		HashSet<TypedDependency> matched = new HashSet<TypedDependency>();

		for (TypedDependency qDep : qDeps) {
			for (TypedDependency sDep : sDeps) {
				if (!matched.contains(qDep) && matchDependency(qDep, sDep)) {
					matched.add(qDep);
				}
			}
		}

		candidate.setDependencyMatchScore(1.0f * matched.size() / qDeps.size());
	}
	
	public void rankSentences(Question question, List<Sentence> candidates) {
		for (Sentence candidate : candidates) {
			scoreBagOfWords(question, candidate);
			scoreDependency(question, candidate);
		}
		
		Collections.sort(candidates);
	}

	private boolean matchDependency(TypedDependency qDep, TypedDependency sDep) {
		boolean matched = false;
		
		String gov1 = Util.stemWord(qDep.gov().value());
		String dep1 = Util.stemWord(qDep.dep().value());
		String gov2 = Util.stemWord(sDep.gov().value());
		String dep2 = Util.stemWord(sDep.dep().value());
		String reln1 = qDep.reln().getShortName();
		String reln2 = sDep.reln().getShortName();

		if (reln1.equals(reln2)) {
			matched = gov1.equals(gov2) && dep1.equals(dep2);
		} else {
			if ((reln1.equals("nsubj") && reln2.equals("agent")) || (reln2.equals("agent") && reln1.equals("nsubj"))
					|| (reln1.equals("nsubjpass") && reln2.equals("dobj"))
					|| (reln2.equals("nsubjpass") && reln1.equals("dobj"))) {
				matched = gov1.equals(gov2) && dep1.equals(dep2);
			}
		}
		return matched;
	}
	
	public static void main(String[] args) {
		String rawQ = "Who loves Mary?";
		String doc = "Mary is Loved by John. Mary loves flappy bird. The man who killed John loves Mary's bird. ";
		
		HashMap<Integer, Sentence> sents = Annotator.annotateDoc(doc);
		List<Sentence> candidates = new ArrayList<Sentence>();
		for (int key : sents.keySet()) {
			candidates.add(sents.get(key));
		}
		
		Question question = Annotator.annotateQuestion(rawQ);

		SimpleQuestionClassifier clf = new SimpleQuestionClassifier();
		question.setQuestionType(clf.classifyQuestion(question));
		AbstractFilter filter = new QuestionTypeFilter();
		candidates = filter.filter(candidates, question);
		
		AnswerRanker scorer = new AnswerRanker();
		scorer.rankSentences(question, candidates);
		System.out.println(question.getDependency());
		for (Sentence candidate : candidates) {
			System.out.println(candidate.getSynonyms());
			System.out.println(candidate.getDependency());
			System.out.printf("%s\t%f %f\n", candidate.getSentence(), candidate.getBowMatchScore(), candidate.getDependencyMatchScore());
		}
	}
}
