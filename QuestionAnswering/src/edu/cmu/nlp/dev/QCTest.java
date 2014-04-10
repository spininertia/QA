package edu.cmu.nlp.dev;

import java.io.FileNotFoundException;
import java.util.List;

import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.main.answer.Runner;

public class QCTest {
	public static void main(String[] args) {
		String questionFile = "data/test.txt";
		List<String> rawQ = null;
		try {
			rawQ = Runner.loadQustions(questionFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (String que : rawQ) {
			Question question = Annotator.annotateQuestion(que);
			System.out.println(que);
			System.out.println(question.getDependency());
		}
	}
}
