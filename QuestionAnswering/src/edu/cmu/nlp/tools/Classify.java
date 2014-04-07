package edu.cmu.nlp.tools;

import java.io.PrintWriter;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.qc.FeatureExtraction;
import edu.cmu.nlp.qc.TrainClassifier;
import edu.cmu.nlp.util.Configuration;
import edu.cmu.nlp.util.Printer;

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
	}

	public void initializeClassifier() {
	}

	public void outputModel(String path) {
		Printer.outputModel(path, this.classifier);
	}

	public void classify() {
		Printer pr = new Printer(Configuration.getQuestionClassPath());
		try {
			this.classifier.setOptions(weka.core.Utils
					.splitOptions(this.options));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			this.classifier.buildClassifier(this.trainSet);
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
	
	public void classifyTest(){
		try {
			this.testSet = new DataSource(Configuration.getTempPath()).getDataSet();
			if (this.testSet.classIndex() == -1)
				this.testSet.setClassIndex(this.testSet.numAttributes() - 1);
			for (int i = 0; i < this.testSet.numInstances(); ++i) {
				int pred = (int) this.classifier.classifyInstance(this.testSet
						.instance(i));
				String predClass = testSet.classAttribute().value(pred);
				System.out.println(pred + "\t" + predClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int classifyWithLoadModel(Question question){
		//Printer pr = new Printer(Configuration.getQuestionClassPath());
		int pred = 0;
		try {
			Printer pTest = new Printer(Configuration.getTempPath());
			TrainClassifier.addHead(pTest);
			
			String vec = FeatureExtraction.extractFeatures(question);
			pTest.writeLine(vec.replace(' ', ',') + ",?");
			pTest.close();
			this.testSet = new DataSource(Configuration.getTempPath()).getDataSet();
			if (this.testSet.classIndex() == -1)
				this.testSet.setClassIndex(this.testSet.numAttributes() - 1);

			pred = (int) this.classifier.classifyInstance(this.testSet
					.instance(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pred;
	}
	
	public void classifyToSee() {
		try {
			this.classifier.buildClassifier(this.trainSet);
			Printer.outputModel(Configuration.getQuestionClassifierModel(), this.classifier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}