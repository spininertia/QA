package edu.cmu.nlp.Indexer;

import java.io.*;
import java.net.*;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.core.SolrConfig;
import org.apache.solr.schema.IndexSchema;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SolrUtils {

	public static IndexSchema getIndexSchema(String serverBaseUrl,
			String coreName, String schemaName) throws MalformedURLException,
			IOException, ParserConfigurationException, SAXException {

		SolrConfig solrConfig = getSolrConfig(serverBaseUrl, coreName,
				schemaName);
		URLConnection urlConn = new URL(
				serverBaseUrl
						+ coreName
						+ "/admin/file/?contentType=text/xml;charset=utf-8&file=schema.xml")
				.openConnection();
		urlConn.connect();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(
				urlConn.getInputStream(), "UTF-8"));
		String text = "";
		String str = "";
		while ((str = bfr.readLine()) != null) {
			text += str.trim() + " ";
		}
		bfr.close();
		bfr = null;
		InputSource inpSrc = new InputSource(new StringReader(text));
		IndexSchema indexSchema = new IndexSchema(solrConfig, schemaName,
				inpSrc);
		return indexSchema;
	}

	public static SolrConfig getSolrConfig(String serverBaseUrl,
			String coreName, String schemaName) throws MalformedURLException,
			IOException, ParserConfigurationException, SAXException {

		URLConnection urlConn = new URL(
				serverBaseUrl
						+ coreName
						+ "/admin/file/?contentType=text/xml;charset=utf-8&file=solrconfig.xml")
				.openConnection();
		urlConn.connect();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(
				urlConn.getInputStream(), "UTF-8"));
		String text = "";
		String str = "";
		while ((str = bfr.readLine()) != null) {
			text += str.trim() + " ";
		}
		bfr.close();
		bfr = null;
		InputSource inpSrc = new InputSource(new StringReader(text.toString()));
		SolrConfig solrConfig = new SolrConfig(schemaName, inpSrc);
		return solrConfig;
	}
}