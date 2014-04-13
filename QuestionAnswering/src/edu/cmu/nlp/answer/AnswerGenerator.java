package edu.cmu.nlp.answer;

import java.util.List;

import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;

public class AnswerGenerator implements IAnswerGenerator {
	
	@Override
	public String answer(Question question, List<Sentence> candidates) {
		IAnswerGenerator handler = null;
		// TODO use reflection to instantiate handler
		return handler.answer(question, candidates);
	}


}
