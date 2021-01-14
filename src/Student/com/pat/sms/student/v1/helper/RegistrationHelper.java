package com.pat.sms.student.v1.helper;

import org.modelmapper.ModelMapper;

import com.pat.sms.student.dto.CandidateDto;
import com.pat.sms.student.v1.reqresp.RegistrationRequest;

public class RegistrationHelper {

	/**
	 * 
	 * @param userSignupRequest
	 * @param userService
	 * @return
	 */
	public static CandidateDto validateAndCopy(RegistrationRequest candRequest, 
			ModelMapper modelMapper) {

		CandidateDto candidate = modelMapper.map(candRequest, CandidateDto.class);

		return candidate;
	}

}
