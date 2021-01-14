package com.pat.sms.admin.service;

import org.springframework.stereotype.Service;

import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.student.dto.SchoolDto;

/**
 * Created by Tarun Pattra
 */
@Service
public interface AdminService {

	SchoolDto getSchoolDetails(String schooluid);

	SearchResDto searchSchool(SearchReqDto reqDto);

	SchoolDto addNewSchool(SchoolDto school, String userName);

	SchoolDto updateSchool(SchoolDto schoolD, String userName);
	
	
}