package com.pat.sms.student.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Order;

import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.student.dto.StudentDto;

/**
 * Created by Tarun Pattra.
 */
public interface StudentService {
	
	StudentDto getStudentById(String studentID);

	StudentDto addNewStudent(StudentDto student, String userName, boolean createUser);

	Map<String, Object> getStudentListWithPagination(String searchText, int page, int size, int orp,
			List<Order> orders);

	SearchResDto searchStu(SearchReqDto reqDto);

	StudentDto updateExistingStudent(StudentDto studentD, String userName);

	void updateProfilePicture(String imgUrl, String studentId, String userName);
	
}
