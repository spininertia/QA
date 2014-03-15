package edu.cmu.nlp.qc;

import java.util.*;
import java.io.*;

import edu.cmu.nlp.util.Configuration;

public class QuestionTaxonomy {

	public static String YES_OR_NO = "YES_OR_NO";

	// general
	public static String ABBR = "ABBR";
	public static String ENTY = "ENTY";
	public static String DESC = "DESC";
	public static String HUM = "HUM";
	public static String LOC = "LOC";
	public static String NUM = "NUM";

	// specific
	public static String ABBR_abb = "ABBR:abb";
	public static String ABBR_exp = "ABBR:exp";

	public static String ENTY_animal = "ENTY:animal";
	public static String ENTY_body = "ENTY:body";
	public static String ENTY_color = "ENTY:color";
	public static String ENTY_creative = "ENTY:cremat";
	public static String ENTY_currency = "ENTY:currency";
	public static String ENTY_dismed = "ENTY:dismed";
	public static String ENTY_event = "ENTY:event";
	public static String ENTY_food = "ENTY:food";
	public static String ENTY_instrument = "ENTY:instrument";
	public static String ENTY_lang = "ENTY:lang";
	public static String ENTY_letter = "ENTY:letter";
	public static String ENTY_other = "ENTY:other";
	public static String ENTY_plant = "ENTY:plant";
	public static String ENTY_product = "ENTY:product";
	public static String ENTY_religion = "ENTY:religion";
	public static String ENTY_sport = "ENTY:sport";
	public static String ENTY_substance = "ENTY:substance";
	public static String ENTY_symbol = "ENTY:symbol";
	public static String ENTY_technique = "ENTY:techmeth";
	public static String ENTY_term = "ENTY:termeq";
	public static String ENTY_vehicle = "ENTY:veh";
	public static String ENTY_word = "ENTY:word";

	public static String DESC_definition = "DESC:def";
	public static String DESC_desc = "DESC.desc";
	public static String DESC_manner = "DESC.manner";
	public static String DESC_reason = "DESC.reason";

	public static String HUM_group = "HUM:gr";
	public static String HUM_individual = "HUM:ind";
	public static String HUM_title = "HUM:title";
	public static String HUM_desc = "HUM:desc";

	public static String LOC_city = "LOC:city";
	public static String LOC_country = "LOC:country";
	public static String LOC_mountain = "LOC:mount";
	public static String LOC_other = "LOC:other";
	public static String LOC_state = "LOC:state";

	public static String NUM_code = "NUM:code";
	public static String NUM_count = "NUM:count";
	public static String NUM_date = "NUM:date";
	public static String NUM_distance = "NUM:dist";
	public static String NUM_money = "NUM:money";
	public static String NUM_order = "NUM:ord";
	public static String NUM_other = "NUM:other";
	public static String NUM_period = "NUM:peroid";
	public static String NUM_percent = "NUM:perc";
	public static String NUM_speed = "NUM:speed";
	public static String NUM_temp = "NUM:temp";
	public static String NUM_size = "NUM:size";
	public static String NUM_weight = "NUM:weight";

	public static HashMap<String, List<String>> generalDesc = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> generalCatIdx = new HashMap<String, Integer>();
	public static HashMap<String, List<String>> deepDesc = new HashMap<String, List<String>>();
	public static HashMap<String, Integer> deepCatIdx = new HashMap<String, Integer>();
	
	static {
		try {
			Scanner sc = new Scanner(new FileInputStream(Configuration.getQuestionGeneralDescPath()));
			int idx = 0;
			while(sc.hasNextLine()){
				String[] tokens = sc.nextLine().split("\\s+");
				generalCatIdx.put(tokens[0], idx ++);
				generalDesc.put(tokens[0], new ArrayList<String>());
				for(int i = 1; i < tokens.length; ++ i){
					generalDesc.get(tokens[0]).add(tokens[i]);
				}
			}
			
			String general = "";
			idx = 0;
			sc = new Scanner(new FileInputStream(Configuration.getQuestionDeepDescPath()));
			while(sc.hasNextLine()){
				String buffer = sc.nextLine();
				if(!buffer.startsWith(" ")){
					general = buffer.trim();
					continue;
				}
				buffer = buffer.trim();
				String[] tokens = buffer.split("\\s+");
				deepCatIdx.put(general + ":" + tokens[0], idx ++);
				deepDesc.put(general + ":" + tokens[0], new ArrayList<String>());
				for(int i = 1; i < tokens.length; ++ i){
					deepDesc.get(general + ":" + tokens[0]).add(tokens[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		System.out.println(QuestionTaxonomy.deepDesc);
		System.out.println(QuestionTaxonomy.generalDesc);
	}
}
