package edu.cmu.nlp.filter;

import java.util.List;

import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;

/**
 * coarse filter may allow false positive
 * @author Siping Ji <sipingji@cmu.edu>
 *
 */
public abstract class AbstractFilter {
	public abstract List<Sentence> doFilter(List<Sentence> candidates, Question question);
	
	public List<Sentence> filter(List<Sentence> candidates, Question question) {
		if (candidates == null || candidates.size() == 1) {
			return candidates;
		}
		
		List<Sentence> filtered = doFilter(candidates, question);
		
		if (filtered.size() == 0) {
			return candidates;
		} 
		return filtered;
	}
}
