package edu.cmu.nlp.main.ask;


import edu.cmu.nlp.annotator.Annotator;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;

public class PostProcess {

	public static boolean isSQ(String str){
		Annotation document = Annotator.AnnotateDoc(str);
		CoreMap sentence = Annotator.getSentences(document).get(0);
		return Annotator.parseSentence(sentence).firstChild().value().equals("SQ");
	}

	public static void main(String[] args){
		System.out.println(PostProcess.isSQ("Is he a boy?"));
		System.out.println(PostProcess.isSQ("Who is John?"));
	}
}