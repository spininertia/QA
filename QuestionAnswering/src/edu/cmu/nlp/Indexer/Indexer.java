package edu.cmu.nlp.Indexer;

import java.util.*;

import edu.cmu.nlp.annotator.*;
import edu.cmu.nlp.util.*;
import edu.cmu.lti.oaqa.core.provider.solr.SolrWrapper;

import org.apache.solr.schema.IndexSchema;
import org.apache.solr.common.SolrInputDocument;

public class Indexer {

	private static SolrWrapper wrapper;
	private static IndexSchema indexSchema;

	static {
		try {
			wrapper = new SolrWrapper(Configuration.getSolrServerUrl()
					+ Configuration.getSolrCoreName());
			indexSchema = SolrUtils.getIndexSchema(
					Configuration.getSolrServerUrl(),
					Configuration.getSolrCoreName(),
					Configuration.getSolrSchemaName());
			indexSchema.getFieldTypes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Index Sentence, NE, NP, VP, Coref, Synonyms
	 */
	public static void indexSentence(Sentence sent) {
		HashMap<String, Object> indexMap = new HashMap<String, Object>();
		int id = sent.getId();
		String text = sent.getSentence();
		List<Chunk> tokens = sent.getTokens();
		List<String> nps = sent.getNounPhrases();
		List<String> vps = sent.getVerbPhrases();
		List<Pair> coref = sent.getCoref();
		List<String> synonyms = sent.getSynonyms();
		indexMap.put("id", id);
		indexMap.put("text", text);
		indexMap.put("np", nps);
		indexMap.put("vp", vps);
		indexMap.put("synonym", synonyms);
		indexMap.put("ne", Util.extractNEs(tokens));
		indexMap.put("coref", Util.extractCoref(coref));
		try {
			SolrInputDocument solrInpDoc = wrapper.buildSolrDocument(indexMap);
			String docXML = wrapper.convertSolrDocInXML(solrInpDoc);

			wrapper.indexDocument(docXML);
			wrapper.getServer().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	/*
	 * public static void process() {
	 * 
	 * try { // Index each sentence HashMap<String, Object> indexMap = new
	 * HashMap<String, Object>(); for (int i = 0; i < 20; ++i) { int id = 1;
	 * String sentId = id + "_" + i; String sentText = "text" + i;
	 * indexMap.put("docid", id); indexMap.put("id", sentId);
	 * indexMap.put("text", sentText);
	 * 
	 * System.out.println(sentId); ArrayList<String> nnList = new
	 * ArrayList<String>(); indexMap.put("nounphrases", nnList);
	 * 
	 * ArrayList<String> neList = new ArrayList<String>();
	 * indexMap.put("namedentities", neList);
	 * 
	 * ArrayList<String> coList = new ArrayList<String>();
	 * indexMap.put("correference", coList);
	 * 
	 * ArrayList<String> depList = new ArrayList<String>();
	 * indexMap.put("dependencies", depList);
	 * 
	 * ArrayList<String> synonymList = new ArrayList<String>();
	 * indexMap.put("synonyms", synonymList);
	 * 
	 * SolrInputDocument solrInpDoc = wrapper .buildSolrDocument(indexMap);
	 * String docXML = wrapper.convertSolrDocInXML(solrInpDoc);
	 * 
	 * wrapper.indexDocument(docXML); wrapper.getServer().commit(); }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */
}
