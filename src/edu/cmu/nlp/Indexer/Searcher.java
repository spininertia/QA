package edu.cmu.nlp.Indexer;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.nlp.annotator.Question;
import edu.cmu.nlp.util.Chunk;
import edu.cmu.nlp.util.Configuration;

public class Searcher {

	private static SolrWrapper wrapper;

	static {
		try {
			wrapper = new SolrWrapper(Configuration.getSolrServerUrl() + Configuration.getSolrCoreName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String formSolrQuery(Question question) {

		StringBuilder solrQuery = new StringBuilder();

		//0: Text, 1: NE, 2: NP, 3: VP, 4: Coref, 5: Synonyms
		int weights[] = { 1, 1, 1, 1, 1, 1};

		solrQuery.append("Text:(" + question.getSentence() + ")^" + weights[0] + " ");
		
		StringBuilder nes = new StringBuilder();
		for(Chunk chunk : question.getTokens()){
			nes.append(chunk.getNe() + " ");
		}
		solrQuery.append("NE:(" + nes.toString().trim() + ")^" + weights[1] + " ");
		
		StringBuilder nps = new StringBuilder();
		for(String np : question.getNounPhrases()){
			nps.append(np + " ");
		}
		solrQuery.append("NP:(" + nps.toString().trim() + ")^" + weights[2] + " ");
		
		StringBuilder vps = new StringBuilder();
		for(String vp : question.getVerbPhrases()){
			nps.append(vp + " ");
		}
		solrQuery.append("VP:(" + vps.toString().trim() + ")^" + weights[3] + " ");
	
		solrQuery.append("Coref:(" + question.getSentence() + ")^" + weights[4] + " ");
		solrQuery.append("Synonyms:(" + question.getSentence() + ")^" + weights[5] + " ");		

		return solrQuery.toString().trim();
	}

	public static void main(String[] args) throws MalformedURLException,
			SolrServerException {
		Question ques = new Question();
		String solrQuery = Searcher.formSolrQuery(ques);
		SolrDocumentList results = wrapper.runQuery(solrQuery,
				Configuration.getTOP_SEARCH_RESULTS());
		System.out.println("results size:" + results.size());
		for (int j = 0; j < results.size(); j++) {
			SolrDocument doc = results.get(j);
			String sentId = doc.get("id").toString();
			//String docId = doc.get("docid").toString();
			String sentence = doc.get("text").toString();
			double relScore = Double.parseDouble(doc.get("score").toString());
			System.out.println(sentId + ":\t" + relScore + "\t" + sentence);
		}
	}
}
