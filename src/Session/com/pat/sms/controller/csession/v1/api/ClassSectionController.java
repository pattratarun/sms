package com.pat.sms.controller.csession.v1.api;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pat.sms.csession.dto.ClassSessionDto;
import com.pat.sms.csession.dto.ClassSessionStudentDto;
import com.pat.sms.csession.service.ClassSessionService;
import com.pat.sms.csession.v1.helper.ClassSessionHelper;
import com.pat.sms.csession.v1.reqresp.ClassCreateRequest;
import com.pat.sms.csession.v1.reqresp.StudentInClassRequest;
import com.pat.sms.csession.v1.reqresp.StudentToClassRequest;
import com.pat.sms.dto.response.Response;
import com.pat.sms.search.SearchReqDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Created by Tarun Pattra.
 */
@RestController
@RequestMapping("/api/v1/clss")
@Api(value = "sms-application", tags="Class Session Mangement Controller")
@SuppressWarnings("rawtypes")
public class ClassSectionController {

	@Autowired
	ClassSessionService classService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/s")
	@ApiOperation(value = "Class Search", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> searchClasses(@RequestBody SearchReqDto reqDto) {
		return new ResponseEntity<>(Response.ok().setPayload(classService.classSessionSearch(reqDto)), HttpStatus.OK);
	}
	
	@PostMapping("/student/s")
	@ApiOperation(value = "Class Student Search", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> searchClassesStudent(@RequestBody SearchReqDto reqDto) {
		return new ResponseEntity<>(Response.ok().setPayload(classService.classSessionStudentSearch(reqDto)), HttpStatus.OK);
	}
	
	
	@PostMapping("/")
	@ApiOperation(value = "Create new classes", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> createNewClass(@RequestBody ClassCreateRequest createRequest) {
		ClassSessionDto classDto = ClassSessionHelper.validateAndCopy(createRequest, modelMapper);
		List<List<ClassSessionDto>> classList =  classService.createNewClass(classDto, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(classList), HttpStatus.OK);
	}
	
	
	@PostMapping("/student")
	@ApiOperation(value = "Add students in a class", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> addStudentToClass(@RequestBody StudentToClassRequest createRequest) {
		List<ClassSessionStudentDto> clsStudent =  classService.addStudentToClass(createRequest.getClassId(), 
				createRequest.getStudendIds(), getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(clsStudent), HttpStatus.OK);
	}
	
	@PutMapping("/student")
	@ApiOperation(value = "Edit students session in a class", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> editStudentInClass(@RequestBody StudentInClassRequest request) {
		ClassSessionStudentDto classStudentreq = ClassSessionHelper.validateAndCopy(request, modelMapper);
		ClassSessionStudentDto clsStudent =  classService.editStudentToClass(classStudentreq, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(clsStudent), HttpStatus.OK);
	}
	
	@GetMapping("/{clssessid}/student")
	@ApiOperation(value = "All students in a class", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> getAllStudentsFromAClass(@PathVariable("clssessid") @Valid String clsSessionId) {
		List<ClassSessionStudentDto> clsStudents =  classService.getAllStudentsFromAClass(clsSessionId);
		return new ResponseEntity<>(Response.ok().setPayload(clsStudents), HttpStatus.OK);
	}
	
	@DeleteMapping("/student")
	@ApiOperation(value = "Delete students from a class", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> deleteStudentToClass(@RequestBody StudentToClassRequest createRequest) {
		classService.deleteStudentFromClass(createRequest.getClassId(), createRequest.getStudendIds());
		return new ResponseEntity<>(Response.ok().setPayload("Delete successfully"), HttpStatus.OK);
	}
	
	@PutMapping("/move")
	@ApiOperation(value = "Move students to different section", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> addStudentToClass(@RequestParam String studentId, @RequestParam String oldClassId, 
			@RequestParam String newClassId ) {
		ClassSessionStudentDto clsStudent =  classService.changeStudentClassSection(studentId, oldClassId, newClassId, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(clsStudent), HttpStatus.OK);
	}
	
	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
