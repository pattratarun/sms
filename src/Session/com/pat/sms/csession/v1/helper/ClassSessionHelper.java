package com.pat.sms.csession.v1.helper;

import org.modelmapper.ModelMapper;

import com.pat.sms.csession.dto.ClassSessionDto;
import com.pat.sms.csession.dto.ClassSessionStudentDto;
import com.pat.sms.csession.v1.reqresp.ClassCreateRequest;
import com.pat.sms.csession.v1.reqresp.StudentInClassRequest;

public class ClassSessionHelper {

	/**
	 * 
	 * @param userSignupRequest
	 * @param userService
	 * @return
	 */
	public static ClassSessionDto validateAndCopy(ClassCreateRequest createRequest, 
			ModelMapper modelMapper) {
		ClassSessionDto clsSession = modelMapper.map(createRequest, ClassSessionDto.class);
		return clsSession;
	}
	
	
	/**
	 * 
	 * @param userSignupRequest
	 * @param userService
	 * @return
	 */
	public static ClassSessionStudentDto validateAndCopy(StudentInClassRequest request, 
			ModelMapper modelMapper) {
		ClassSessionStudentDto clsSession = modelMapper.map(request, ClassSessionStudentDto.class);
		return clsSession;
	}
	
	
}
