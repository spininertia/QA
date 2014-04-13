package edu.cmu.nlp.dev;

import java.util.Collection;
import java.util.Iterator;

import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.stanford.nlp.trees.TypedDependency;

public class DependencyTest {
	public static void main(String[] args) {
		
		String rawQ = "Mary is loved by John";
		Question question = Annotator.annotateQuestion(rawQ);
//		for (Chunk token : question.getTokens()) {
//			System.out.println(token.getNe());
//		}
		
		System.out.println(question.getDependency());
		Collection<TypedDependency> dependencies = question.getDependency().typedDependencies();
		Iterator<TypedDependency> iterator = dependencies.iterator();
		while (iterator.hasNext()) {
			TypedDependency dp = iterator.next();
			System.out.println(dp);
			System.out.println(dp.gov().value());
			System.out.println(dp.dep());
			System.out.println(dp.reln());
			System.out.println();
		}
	}
}	
