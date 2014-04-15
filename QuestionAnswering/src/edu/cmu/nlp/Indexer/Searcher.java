package edu.cmu.nlp.Indexer;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;
import edu.cmu.nlp.annotator.Question;
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

		solrQuery.append(question.getSentence() + " ");
//		solrQuery.append("text:\"" + question.getSentence() + "\"^" + weights[0] + " ");
//		
//		StringBuilder nes = new StringBuilder();
//		for(Chunk chunk : question.getTokens()){
//			if(!chunk.getNe().equals("0"))
//				nes.append(chunk.getWord() + " ");
//		}
//		solrQuery.append("ne:\"" + nes.toString().trim() + "\"^" + weights[1] + " ");
//		
//		StringBuilder nps = new StringBuilder();
//		for(String np : question.getNounPhrases()){
//			nps.append(np + " ");
//		}
//		solrQuery.append("np:\"" + nps.toString().trim() + "\"^" + weights[2] + " ");
//		
//		StringBuilder vps = new StringBuilder();
//		for(String vp : question.getVerbPhrases()){
//			nps.append(vp + " ");
//		}
//		solrQuery.append("vp:\"" + vps.toString().trim() + "\"^" + weights[3] + " ");
//	
//		solrQuery.append("coref:\"" + question.getSentence() + "\"^" + weights[4] + " ");
//		solrQuery.append("synonym:\"" + question.getSentence() + "\"^" + weights[5] + " ");		

		return solrQuery.toString().trim();
	}
	
	public static void clearIndex() throws SolrServerException, IOException {
		SolrServer solrServer = new HttpSolrServer(Configuration.getSolrServerUrl() + Configuration.getSolrCoreName());
		solrServer.deleteByQuery("*:*");
	}

	public static SolrDocumentList search(String query){
		SolrDocumentList result = null;
		try{
			result = wrapper.runQuery(query, Configuration.getTOP_SEARCH_RESULTS());
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public static void printSolrResult(SolrDocumentList docList){
		System.out.println("results size:" + docList.size());
		for (int j = 0; j < docList.size(); j++) {
			SolrDocument doc = docList.get(j);
			String sentId = doc.get("id").toString();
			//String docId = doc.get("docid").toString();
			String sentence = doc.get("text").toString();
			double relScore = Double.parseDouble(doc.get("score").toString());
			System.out.println(sentId + ":\t" + relScore + "\t" + sentence);
		}
	}
	
	public static void main(String[] args) throws MalformedURLException,
			SolrServerException {
		Question ques = new Question();
		String solrQuery = Searcher.formSolrQuery(ques);
		SolrDocumentList results = Searcher.search(solrQuery);
		Searcher.printSolrResult(results);
	}
}
