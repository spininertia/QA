package edu.cmu.nlp.tools;

import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.util.Configuration;
import weka.classifiers.functions.SMO;

import java.io.*;

/**
 * Description: Naive Bayes Classifier
 */
public class MySMO extends Classify {

	public void initializeClassifier() {
		this.classifier = new SMO();
	}

	public void initializeClassifierWithModel() {
		try {
			this.classifier = (SMO) (new ObjectInputStream(new FileInputStream(
					Configuration.getQuestionClassifierModel()))).readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MySMO classifier = new MySMO();
		classifier.initializeClassifierWithModel();
		//classifier.classifyTest();
		String ques = "Who sent an email to Stanford University?";
		Question q = Annotator.annotateQuestion(ques);
		classifier.classifyWithLoadModel(q);
		/*
		String ques = "Who sent an email to Stanford University?";
		Question q = Annotator.annotateQuestion(ques);
		MySMO classifier = new MySMO();
		classifier.initializeClassifierWithModel();
		int[] vec = Util.String2Arr(FeatureExtraction.extractFeatures(q));
		for(int i : vec)
			System.out.print(i + " ");
		classifier.classifyWithLoadModel(vec);
		*/
	}
}