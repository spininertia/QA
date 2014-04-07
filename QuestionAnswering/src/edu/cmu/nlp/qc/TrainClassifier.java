package edu.cmu.nlp.qc;

import java.io.*;
import java.util.*;

import edu.cmu.nlp.annotator.*;
import edu.cmu.nlp.util.*;

public class TrainClassifier {

	public static void addHead(Printer printer) {
		printer.writeLine("@relation question-binary-vector");
		printer.writeLine("");
		for (int i = 0; i < Configuration.getFeatureNumber(); ++i) {
			printer.writeLine("@attribute feature" + i + " {0, 1}");
		}
		printer.writeLine("@attribute class {"
				+ Util.generateVector(0, 50, ", ") + "}");
		printer.writeLine("");
		printer.writeLine("@data");
	}

	public static void translateTrainingSet2BinaryVector(String inpath,
			String outpath) {
		Scanner sc = null;
		Printer printer = new Printer(outpath);
		addHead(printer);
		try {
			sc = new Scanner(new File(inpath));
			int id = 0;
			while (sc.hasNextLine()) {
				String buffer = sc.nextLine();
				String[] strs = buffer.split("  *|		*");
				String label = strs[0];
				if(!QuestionTaxonomy.deepCatIdx.containsKey(label)){
					continue;
				}
				String quesStr = Util.cancatenate(strs, 1, strs.length);
				Question ques = Annotator.annotateQuestion(quesStr);
				String vec = FeatureExtraction.extractFeatures(ques);
				printer.writeLine(vec.replace(' ', ',') + ","
						+ QuestionTaxonomy.deepCatIdx.get(label));
				System.out.println((id++) + "\t" + label + "\t:\t" + quesStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanData(String inpath){
		Scanner sc = null;
		try {
			sc = new Scanner(new File(inpath));
			int id = 1;
			while (sc.hasNextLine()) {
				String buffer = sc.nextLine();
				String[] strs = buffer.split("  *|		*");
				String label = strs[0];
				if(!QuestionTaxonomy.deepCatIdx.containsKey(label)){
					System.out.println(label + "\t" + id);
				}
				++ id;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//TrainClassifier.cleanData(Configuration.getQuestionTrainPath());
		//TrainClassifier.translateTrainingSet2BinaryVector(
		//		Configuration.getQuestionTrainPath(),
		//		Configuration.getQuestionTrainVectorPath());
		TrainClassifier.translateTrainingSet2BinaryVector(
				Configuration.getQuestionTestPath(),
				Configuration.getQuestionTestVectorPath());
	}
}
