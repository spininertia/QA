package edu.cmu.nlp.qc;

import java.util.regex.Pattern;

import edu.cmu.nlp.annotator.*;
import edu.cmu.nlp.tools.MySMO;

public class QC {

	private static MySMO classifier = null;
	
	static{
		classifier = new MySMO();
		classifier.initializeClassifierWithModel();
	}
	
	public static String patternMatch(String question) {
		
		question = question.toLowerCase();
		
		if(Pattern.matches("what\\s+(is|are)\\s+(a|an|the)?\\s*\\S+(\\s+\\S+)?", question))
			return QuestionTaxonomy.DESC_definition;
		if(Pattern.matches("what\\s+(do|does).+mean", question))
			return QuestionTaxonomy.DESC_definition;
		if(Pattern.matches("what\\s+(is|are).+(composed|made|made\\s+out)\\s+of", question))
			return QuestionTaxonomy.ENTY_substance;
		if(Pattern.matches("what\\s+does.+do", question))
			return QuestionTaxonomy.DESC_desc;
		if(Pattern.matches("what\\s+do\\s+you\\s+call.+", question))
			return QuestionTaxonomy.ENTY_term;
		if(Pattern.matches("what\\s+(cause|causes).+", question))
			return QuestionTaxonomy.DESC_reason;
		if(Pattern.matches("what\\s+(is|are).+used\\s+for", question))
			return QuestionTaxonomy.DESC_reason;
		if(Pattern.matches("what\\s+(do|does).+stand\\s+for", question))
			return QuestionTaxonomy.ABBR_exp;
		return null;
	}

	public static String classifyQuestion(Question question) {

		String questionClass = "";

		if (question.getParsingTree().firstChild().value().equals("SQ")) {
			questionClass = QuestionTaxonomy.YES_OR_NO;
		} else {
			// classify the questions
			String cate = patternMatch(question.getSentence());
			if (cate != null)
				return cate;
			// ...
			int pred = classifier.classifyWithLoadModel(question);
			questionClass = "NULL";
			if(QuestionTaxonomy.deepIdxCat.containsKey(pred))
				questionClass = QuestionTaxonomy.deepIdxCat.get(pred);
		}

		return questionClass;
	}
	
	public static void main(String[] args){
		String str = "what is the bird?";
		Question ques = Annotator.annotateQuestion(str);
		System.out.println(QC.classifyQuestion(ques));
	}
}
