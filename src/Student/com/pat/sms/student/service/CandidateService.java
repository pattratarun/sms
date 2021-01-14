package com.pat.sms.student.service;

import java.util.List;

import com.pat.sms.exception.ErrorObject;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.student.dto.CandidateDto;

/**
 * Created by Tarun Pattra.
 */
public interface CandidateService {
	
	CandidateDto getCandidateDetails(String admissionNumber);
	
	CandidateDto registerNewCandidate(CandidateDto candidate, String userName);

	SearchResDto searchCandidate(SearchReqDto reqDto);

	CandidateDto updateExistingCandidate(CandidateDto candidate, String userName);

	boolean deleteCandidate(String studentId);

	List<ErrorObject> candidateToStudent(List<String> candidateIds, String userName);

	void updateProfilePicture(String imgUrl, String studentId, String userName);
	
}
