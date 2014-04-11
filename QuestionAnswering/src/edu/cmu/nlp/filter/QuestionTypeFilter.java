package edu.cmu.nlp.filter;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;

public class QuestionTypeFilter extends AbstractFilter {

	@Override
	public List<Sentence> doFilter(List<Sentence> candidates, Question question) {
		List<Sentence> filtered = new ArrayList<Sentence>();
		for (Sentence candidate : candidates) {
			if (!shouldFilter(question, candidate)) {
				filtered.add(candidate);
			}
		}
		return filtered;
	}

	private boolean shouldFilter(Question question, Sentence candidate) {
		boolean filter = true;

		switch (question.getQuestionType()) {
			case WHEN :
			case HOW_LONG :
			case HOW_OFTEN :
				if (candidate.containsNE("TIME") || candidate.containsNE("DATE") || candidate.containsNE("DURATION")) {
					filter = false;
				} else {
					filter = true;
				}
				break;

			case WHERE :
				if (candidate.containsNE("LOCATION") || candidate.containsNE("ORGANIZATION")
						|| candidate.containsPrep()) {
					filter = false;
				} else {
					filter = true;
				}

				break;

			case HOW_MUCH :
				if (candidate.containsNE("MONEY")) {
					filter = false;
				} else {
					filter = true;
				}
				
				break;

			case WHO :
				// TODO: recheck, might be buggy
				if (candidate.containsNE("PERSON") || candidate.containsWord("he") || candidate.containsWord("him")
						|| candidate.containsWord("she") || candidate.containsWord("her")
						|| candidate.containsWord("they") || candidate.containsWord("them")) {
					filter = false;
				} else {
					filter = true;
				}
				
				break;
				
			default :
				filter = false;

		}

		return filter;
	}
}
