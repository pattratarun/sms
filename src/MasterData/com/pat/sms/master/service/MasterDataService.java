package com.pat.sms.master.service;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pat.sms.master.v1.reqresp.LabelValueBean;
import com.pat.sms.master.v1.reqresp.SearchField;

public interface MasterDataService {
	
	Map<String,String> getAllConstants();

	List<LabelValueBean> getSchools();

	String generateID(String user);

	void resetAllCaches();

	Map<String, List<String>> getDropdown();

	List<SearchField> getSearchFeilds(String searcName) throws JsonMappingException, JsonProcessingException;
	
}
