package com.pat.sms.search;

import static com.pat.sms.util.StringConstants.DEFAULT_PAGE_INDEX;
import static com.pat.sms.util.StringConstants.DEFAULT_PAGE_SIZE;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchReqDto {
	private int pageIndex = DEFAULT_PAGE_INDEX;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private String query;
	private List<String> sorts = new ArrayList<>();
}
