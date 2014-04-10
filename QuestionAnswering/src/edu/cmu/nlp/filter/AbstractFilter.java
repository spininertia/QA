package edu.cmu.nlp.filter;

import java.util.List;

import edu.cmu.nlp.annotator.Sentence;

public abstract class AbstractFilter {
	public abstract List<Sentence> doFilter(List<Sentence> candidates);
	
	public List<Sentence> filter(List<Sentence> candidates) {
		if (candidates == null || candidates.size() == 1) {
			return candidates;
		}
		
		List<Sentence> filtered = doFilter(candidates);
		
		if (filtered.size() == 0) {
			return candidates;
		} 
		return filtered;
	}
}
