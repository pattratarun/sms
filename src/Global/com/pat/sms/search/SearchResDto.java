package com.pat.sms.search;

import java.util.List;

import lombok.Data;

@Data
public class SearchResDto {
	private List<Object> result;
	private int totalPages;
	private int pageIndex;
	private long totalRecords;
}
