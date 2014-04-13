package edu.cmu.nlp.answer;

import java.util.List;

import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;

public interface IAnswerGenerator {
	public String answer(Question question, List<Sentence> candidates);
}
