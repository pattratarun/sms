package com.pat.sms.csession.service;

import java.util.List;

import com.pat.sms.csession.dto.ClassSessionDto;
import com.pat.sms.csession.dto.ClassSessionStudentDto;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;

/**
 * Created by Tarun Pattra.
 */
public interface ClassSessionService {

	List<List<ClassSessionDto>> createNewClass(ClassSessionDto classSDto, String userName);

	List<ClassSessionStudentDto> addStudentToClass(String classId, List<String> studentList, String userName);

	void deleteStudentFromClass(String classId, List<String> studentList);

	ClassSessionStudentDto changeStudentClassSection(String studentId, String oldClassId, String newClassId,
			String userName);

	ClassSessionStudentDto editStudentToClass(ClassSessionStudentDto classStudentDto, String userName);

	List<ClassSessionStudentDto> getAllStudentsFromAClass(String classSessionId);

	SearchResDto classSessionSearch(SearchReqDto reqDto);

	SearchResDto classSessionStudentSearch(SearchReqDto reqDto);
	
}
