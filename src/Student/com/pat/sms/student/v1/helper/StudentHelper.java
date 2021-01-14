package com.pat.sms.student.v1.helper;

import java.util.Date;

import org.modelmapper.ModelMapper;

import com.pat.sms.model.student.Student;
import com.pat.sms.student.dto.StudentDto;
import com.pat.sms.student.v1.reqresp.StudentCreateRequest;
import com.pat.sms.util.CommonUtil;

public class StudentHelper {

	/**
	 * 
	 * @param userSignupRequest
	 * @param userService
	 * @return
	 */
	public static StudentDto validateAndCopy(StudentCreateRequest createRequest, 
			ModelMapper modelMapper) {

		StudentDto student = modelMapper.map(createRequest, StudentDto.class);
		student.setFName(CommonUtil.stringToCamel(student.getFName()))
		.setMName(CommonUtil.stringToCamel(student.getMName()))
		.setLName(CommonUtil.stringToCamel(student.getLName()));
		
		return student;
	}
	
	
	public static Student prepareStudentObject(StudentDto studentDto, Student student) {
		student.setAdditionalId1(studentDto.getAdditionalId1())
		.setAdditionalId2(studentDto.getAdditionalId2())
		.setAddress1(studentDto.getAddress1())
		.setAddress2(studentDto.getAddress2())
		.setAdmissionCat1(studentDto.getAdmissionCat1())
		.setAdmissionCat2(studentDto.getAdmissionCat2())
		.setAdmissionCat3(studentDto.getAdmissionCat3())
		.setAdmissionNum(studentDto.getAdmissionNum())
		.setAdmissionRemarks(studentDto.getAdmissionRemarks())
		.setAge(studentDto.getAge())
		.setBloodGroup(studentDto.getBloodGroup())
		.setCity(studentDto.getCity())
		.setCountry(studentDto.getCountry())
		.setDob(studentDto.getDob())
		.setEndDay(studentDto.getEndDay())
		.setFName(studentDto.getFName())
		.setGender(studentDto.getGender())
		.setIsHostller(studentDto.getIsHostller())
		.setLName(studentDto.getLName())
		.setMName(studentDto.getMName())
		.setModifiedBy("")
		.setModifiedDate(new Date())
		.setNationality(studentDto.getNationality())
		.setOnlyChild(studentDto.getOnlyChild())
		.setPhone(studentDto.getPhone())
		.setProfileImage(studentDto.getProfileImage())
		.setRfId(studentDto.getRfId())
		.setSiblingId(studentDto.getSiblingId())
		.setStartDay(studentDto.getStartDay())
		.setState(studentDto.getState())
		.setStatus(studentDto.getStatus())
		.setWing(studentDto.getWing());
		
		return student;
	}

}
