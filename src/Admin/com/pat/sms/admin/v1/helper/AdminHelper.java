package com.pat.sms.admin.v1.helper;

import org.modelmapper.ModelMapper;

import com.pat.sms.admin.v1.reqresp.SchoolReqResp;
import com.pat.sms.student.dto.SchoolDto;

public class AdminHelper {
	
	public static SchoolDto validateAndCopy(SchoolReqResp createRequest, 
			ModelMapper modelMapper) {
		SchoolDto schooDto = modelMapper.map(createRequest, SchoolDto.class);
		return schooDto;
	}

}
