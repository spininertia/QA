package edu.cmu.nlp.qc;

import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
/**
 *  
 * @author songmin
 * @return QuestionType 
 *
 */
//	public enum QuestionType {
//		YESNO, 
//		WHO, WHERE, WHEN, WHICH, WHY, WHOSE, 
//		WHAT, WHAT_HAPPEN,
//		HOW_OFTEN, HOW_LONG, HOW_MUCH, HOW_FAR 
//	}

public class SimpleQuestionClassifier {
	
	// question pattern 
	private static final String yesnoPattern 		= "SQ";
	
	private static final String whPattern 			= "SBARQ";
	
	private static final String whoPattern 			= "^who\\b.*";
	private static final String wherePattern		= "^where\\b.*";
	private static final String whenPattern 		= "^when\\b.*";
	private static final String whichPattern 		= "^which\\b.*";
	private static final String whyPattern	 		= "^why\\b.*";
	private static final String whosePattern 		= "^whose\\b.*";
	
	private static final String whatPattern 		= "^what\\b.*";
	private static final String whathappendPattern 	= "^what happen.*";
	
	private static final String howoffenPattern 	= "^how often.*";
	private static final String howlongPattern 		= "^how long.*";
	private static final String howmuchPattern		= "^how much.*";
	private static final String howfarPattern 		= "^how far.*";  
	
	
	
	public QuestionType classifyQuestion(Question question) {
		
		// SQ - Inverted yes/no question, or main clause of a wh-question, following the wh-phrase in SBARQ.
		// SBARQ - Direct question introduced by a wh-word or a wh-phrase. Indirect questions and relative clauses 
		// should be bracketed as SBAR, not SBARQ.

		String sent = question.getSentence().toLowerCase();
		if(sent.matches(whoPattern)) {
			return QuestionType.WHO;
		}else if (sent.matches(wherePattern)) {
			return QuestionType.WHERE;
		}else if(sent.matches(whenPattern)) {
			return QuestionType.WHEN;
		}else if(sent.matches(whichPattern)) {
			return QuestionType.WHICH;
		}else if(sent.matches(whyPattern)) {
			return QuestionType.WHY;
		}else if(sent.matches(whosePattern)) {
			return QuestionType.WHOSE;
		}else if(sent.matches(whathappendPattern)) {
			return QuestionType.WHAT_HAPPEN;
		}else if(sent.matches(whatPattern)) {
			return QuestionType.WHAT;
		}else if(sent.matches(howoffenPattern)) {
			return QuestionType.HOW_OFTEN;
		}else if(sent.matches(howlongPattern)) {
			return QuestionType.HOW_LONG;
		}else if(sent.matches(howmuchPattern)) {
			return QuestionType.HOW_MUCH;
		}else if(sent.matches(howfarPattern)) {
			return QuestionType.HOW_FAR;
		}else if (question.getParsingTree().firstChild().value().equals(yesnoPattern)) {
			return QuestionType.YESNO;
		}
		return QuestionType.UNKNOWN;
	}
	
	public static void main(String[] args) {
		String s = "how far is with your name?";
		Question ques = Annotator.annotateQuestion(s);
		System.out.println(new SimpleQuestionClassifier().classifyQuestion(ques));
	}
	
}