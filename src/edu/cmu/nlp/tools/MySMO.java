package edu.cmu.nlp.tools;

import edu.cmu.nlp.util.Configuration;
import weka.classifiers.functions.SMO;

/**
 * Description: Naive Bayes Classifier
 */
public class MySMO extends Classify {

	public void initializeClassifier() {
		this.classifier = new SMO();
	}

	public static void main(String[] args) {
		Classify classifier = new MySMO();
		classifier.initialize(Configuration.getQuestionTrainPath(),
				Configuration.getQuestionTestPath(),
				Configuration.getSmoOption());
		classifier.initializeClassifier();
		classifier.classify();
	}
}
