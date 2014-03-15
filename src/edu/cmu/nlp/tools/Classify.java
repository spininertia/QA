package edu.cmu.nlp.tools;

import edu.cmu.nlp.util.Configuration;
import edu.cmu.nlp.util.Printer;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Classify {

	protected Instances trainSet = null;
	protected Instances testSet = null;
	protected String options = "";
	protected Classifier classifier = null;

	public void initialize(String train, String test, String options) {
		try {
			this.trainSet = new DataSource(train).getDataSet();
			if (this.trainSet.classIndex() == -1)
				this.trainSet.setClassIndex(this.trainSet.numAttributes() - 1);
			this.testSet = new DataSource(test).getDataSet();
			if (this.testSet.classIndex() == -1)
				this.testSet.setClassIndex(this.testSet.numAttributes() - 1);
			this.options = options;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			this.classifier.setOptions(weka.core.Utils
					.splitOptions(this.options));
			this.classifier.buildClassifier(this.trainSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initializeClassifier() {
	}
	
	public void outputModel(String path) {
		Printer.outputModel(path, this.classifier);
	}

	public void classify() {
		Printer pr = new Printer(Configuration.getQuestionClassPath());
		try {
			testSet.setClassIndex(testSet.numAttributes() - 1);
			for (int i = 0; i < this.testSet.numInstances(); ++i) {
				int pred = (int) this.classifier.classifyInstance(this.testSet
						.instance(i));
				String predClass = testSet.classAttribute().value(pred);
				pr.writeLine(pred + "\t" + predClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
