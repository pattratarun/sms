package com.pat.sms.search;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class SearchCriteria {

	private boolean clauseAnd;
	private SearchParamTypeEnum type;
	private String key;
	private String operation;
	private String value;

	public SearchCriteria(String clause, String type, String key, String operation, String value) {
		this.clauseAnd = StringUtils.isEmpty(clause);
		this.type = SearchParamTypeEnum.valueOfType(type);
		this.key = key;
		this.operation = operation;
		this.value = value;
	}

}
