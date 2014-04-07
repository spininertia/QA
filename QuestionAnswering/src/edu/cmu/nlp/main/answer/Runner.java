package edu.cmu.nlp.main.answer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import edu.cmu.nlp.Indexer.Indexer;
import edu.cmu.nlp.Indexer.Searcher;
import edu.cmu.nlp.annotator.Annotator;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.annotator.Sentence;
import edu.cmu.nlp.qc.QC;
import edu.cmu.nlp.qc.QuestionTaxonomy;
import edu.cmu.nlp.util.Util;

public class Runner {

	private String rawArticle;
	private List<String> rawQuestions;

	private Map<Integer, Sentence> sentences;

	public Runner(String articleFile, String questionFile) {
		try {
			this.rawArticle = loadArticle(articleFile);
			this.rawQuestions = loadQustions(questionFile);
		} catch (IOException e) {
			System.err.println("Error loading article/questions!");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static String loadArticle(String articleFile) throws IOException {
		File file = new File(articleFile);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];

		fis.read(data);
		fis.close();

		return new String(data, "UTF-8");
	}

	public static List<String> loadQustions(String questionFile) throws FileNotFoundException {
		List<String> list = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(questionFile));

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			list.add(line);
		}

		return list;
	}
	
	public static boolean sameNESet(List<String> qNEs, List<String> cNEs) {
		for (String ne : cNEs) {
			if (!qNEs.contains(ne)) {
				return false;
			}
		}

		return true;
	}

	public void annotateDoc() {
		this.sentences = Annotator.annotateDoc(rawArticle);
	}

	public Question annotateQuestion(String question) {
		return Annotator.annotateQuestion(question);
	}

	public void indexSentences() {
		Iterator<Entry<Integer, Sentence>> iter = sentences.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Integer, Sentence> e = iter.next();
			Indexer.indexSentence(e.getValue());
		}
	}

	public void classifyQuestion(Question question) {
		question.setType(QC.classifyQuestion(question));
	}

	public List<Sentence> searchCandidateSentences(Question question) {
		List<Sentence> candidates = new ArrayList<Sentence>();
		String solrQuery = Searcher.formSolrQuery(question);
		SolrDocumentList results = Searcher.search(solrQuery);

		if (results != null) {
			for (SolrDocument doc : results) {
				candidates.add(sentences.get(Integer.valueOf(doc.get("id").toString())));
			}
		}

		return candidates;
	}

	public String answerQuestion(Question question, List<Sentence> candidates) {
		String answer = "";

		if (question.getType().equals(QuestionTaxonomy.YES_OR_NO)) {
			if (candidates.size() == 0) {
				answer = "No";
			} else {
				List<String> questionNes = Util.extractNEs(question.getTokens());
				Sentence candidate = candidates.get(0);

				List<String> candidateNes = Util.extractNEs(candidate.getTokens());

				if (!sameNESet(questionNes, candidateNes)) {
					answer = "No";
				} else {
					if (question.containsNegation() == candidate.containsNegation()) {
						answer = "Yes";
					} else {
						answer = "No";
					}
				}
			}
		} else {
			Sentence candidate = candidates.get(0);
			answer = candidate.getSentence();
		}

		return answer;
	}

	public void presentResult(String answer) {
		System.out.println(answer);
	}

	public void run() {
		annotateDoc();
		indexSentences();

		for (String rawQuestion : this.rawQuestions) {
			Question question = annotateQuestion(rawQuestion);
			List<Sentence> candidates = searchCandidateSentences(question);
			classifyQuestion(question);
			String answer = answerQuestion(question, candidates);
			presentResult(answer);
		}
	}

	

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: Java -jar Answer.jar <ArticleFile> <QuestionFile>");
			System.exit(-1);
		}
		
		String articleFile = args[0];
		String questionFile = args[1];
		
		Runner runner = new Runner(articleFile, questionFile);
		runner.run();
	}

}
