package edu.cmu.nlp.util;

public class Configuration {

	//Configuration for wordnet part
	private static String wordnetDatabasePath = "./files/dict/";
	private static String wordnetPathForSim = "./files";
	
	//Configuration for solr part
	private static int TOP_SEARCH_RESULTS = 10;
	private static String solrServerUrl = "http://localhost:8983/solr";
	private static String solrSchemaName = "11611-project";
	private static String solrCoreName = "";

	//QC
	private static int hypernymLevel = 3;
	private static String questionGeneralDescPath = "files/qc/question.general.desc";
	private static String questionDeepDescPath = "files/qc/question.deep.desc";
	private static String questionTrainPath = "files/qc/training_set.label";
	private static String questionTestPath = "files/qc/test_set.label";
	private static String questionTrainVectorPath = "files/qc/training_set_vector.arff";
	private static String questionTestVectorPath = "files/qc/test_set_vector.arff";
	private static String questionClassifierModel = "files/model/SMO.model";
	private static int featureNumber = 8742;
	private static String questionClassPath = "result/question_class";
	private static String smoOption = "weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0";

	//other
	private static String wordListPath = "files/wordlist";
	private static String pronounListPath = "files/pronoun";
	private static String tempPath = "files/temp/temp.arff";
	
	public static String getWordnetDatabasePath() {
		return wordnetDatabasePath;
	}

	public static void setWordnetDatabasePath(String wordnetDatabasePath) {
		Configuration.wordnetDatabasePath = wordnetDatabasePath;
	}

	public static String getWordnetPathForSim() {
		return wordnetPathForSim;
	}

	public static void setWordnetPathForSim(String wordnetPathForSim) {
		Configuration.wordnetPathForSim = wordnetPathForSim;
	}

	public static int getTOP_SEARCH_RESULTS() {
		return TOP_SEARCH_RESULTS;
	}

	public static void setTOP_SEARCH_RESULTS(int tOP_SEARCH_RESULTS) {
		TOP_SEARCH_RESULTS = tOP_SEARCH_RESULTS;
	}

	public static String getSolrServerUrl() {
		return solrServerUrl;
	}

	public static void setSolrServerUrl(String solrServerUrl) {
		Configuration.solrServerUrl = solrServerUrl;
	}

	public static String getSolrSchemaName() {
		return solrSchemaName;
	}

	public static void setSolrSchemaName(String solrSchemaName) {
		Configuration.solrSchemaName = solrSchemaName;
	}

	public static String getSolrCoreName() {
		return solrCoreName;
	}

	public static void setSolrCoreName(String solrCoreName) {
		Configuration.solrCoreName = solrCoreName;
	}

	public static int getHypernymLevel() {
		return hypernymLevel;
	}

	public static void setHypernymLevel(int hypernymLevel) {
		Configuration.hypernymLevel = hypernymLevel;
	}

	public static String getQuestionGeneralDescPath() {
		return questionGeneralDescPath;
	}

	public static void setQuestionGeneralDescPath(
			String questionGeneralDescPath) {
		Configuration.questionGeneralDescPath = questionGeneralDescPath;
	}

	public static String getQuestionDeepDescPath() {
		return questionDeepDescPath;
	}

	public static void setQuestionDeepDescPath(String questionDeepDescPath) {
		Configuration.questionDeepDescPath = questionDeepDescPath;
	}

	public static String getWordListPath() {
		return wordListPath;
	}

	public static void setWordListPath(String wordListPath) {
		Configuration.wordListPath = wordListPath;
	}

	public static String getQuestionClassPath() {
		return questionClassPath;
	}

	public static void setQuestionClassPath(String questionClassPath) {
		Configuration.questionClassPath = questionClassPath;
	}

	public static String getQuestionTrainPath() {
		return questionTrainPath;
	}

	public static void setQuestionTrainPath(String questionTrainPath) {
		Configuration.questionTrainPath = questionTrainPath;
	}

	public static String getQuestionTestPath() {
		return questionTestPath;
	}

	public static void setQuestionTestPath(String questionTestPath) {
		Configuration.questionTestPath = questionTestPath;
	}

	public static String getSmoOption() {
		return smoOption;
	}

	public static void setSmoOption(String smoOption) {
		Configuration.smoOption = smoOption;
	}

	public static String getPronounListPath() {
		return pronounListPath;
	}

	public static void setPronounListPath(String pronounListPath) {
		Configuration.pronounListPath = pronounListPath;
	}

	public static int getFeatureNumber() {
		return featureNumber;
	}

	public static void setFeatureNumber(int featureNumber) {
		Configuration.featureNumber = featureNumber;
	}

	public static String getQuestionTrainVectorPath() {
		return questionTrainVectorPath;
	}

	public static void setQuestionTrainVectorPath(
			String questionTrainVectorPath) {
		Configuration.questionTrainVectorPath = questionTrainVectorPath;
	}

	public static String getQuestionTestVectorPath() {
		return questionTestVectorPath;
	}

	public static void setQuestionTestVectorPath(String questionTestVectorPath) {
		Configuration.questionTestVectorPath = questionTestVectorPath;
	}

	public static String getQuestionClassifierModel() {
		return questionClassifierModel;
	}

	public static void setQuestionClassifierModel(
			String questionClassifierModel) {
		Configuration.questionClassifierModel = questionClassifierModel;
	}

	public static String getTempPath() {
		return tempPath;
	}

	public static void setTempPath(String tempPath) {
		Configuration.tempPath = tempPath;
	}
}