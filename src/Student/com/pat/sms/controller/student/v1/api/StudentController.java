package com.pat.sms.controller.student.v1.api;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pat.sms.amazon.service.AmazonClient;
import com.pat.sms.dto.response.Response;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.student.dto.StudentDto;
import com.pat.sms.student.service.StudentService;
import com.pat.sms.student.v1.helper.StudentHelper;
import com.pat.sms.student.v1.reqresp.StudentCreateRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Created by Tarun Pattra.
 */

@RestController
@RequestMapping("/api/v1/student")
@Api(value = "sms-application", tags="Student Mangement Controller")
@SuppressWarnings("rawtypes")
public class StudentController {
	
	@Autowired
    private AmazonClient amazonClient;

	@Autowired
	StudentService studentService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/{studentid}")
	@ApiOperation(value = "Student Details", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> getStudentDetails(@PathVariable("studentid") @Valid String studentId) {
		return new ResponseEntity<>(Response.ok().setPayload(studentService.getStudentById(studentId)), HttpStatus.OK);
	}

	@PostMapping("/s")
	@ApiOperation(value = "Student Search", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> searchStudent(@RequestBody SearchReqDto reqDto) {
		return new ResponseEntity<>(Response.ok().setPayload(studentService.searchStu(reqDto)), HttpStatus.OK);
	}

	/*
	 * @GetMapping("/s")
	 * 
	 * @ApiOperation(value ="Student Search", authorizations = {@Authorization(value
	 * = "apiKey")}) public ResponseEntity<Response> studentSearch(
	 * 
	 * @RequestParam(required = false) String searchText,
	 * 
	 * @RequestParam(defaultValue = "0") int oprt,
	 * 
	 * @RequestParam(defaultValue = "1") int page,
	 * 
	 * @RequestParam(defaultValue = "3") int size,
	 * 
	 * @RequestParam(defaultValue = "studentID,desc") String[] sort) { List<Order>
	 * orders = new ArrayList<>(); if (sort[0].contains(",")) { // will sort more
	 * than 2 fields // sortOrder="field, direction" for (String sortOrder : sort) {
	 * String[] _sort = sortOrder.split(","); orders.add(new
	 * Order(getSortDirection(_sort[1]), _sort[0])); } } else { // sort=[field,
	 * direction] orders.add(new Order(getSortDirection(sort[1]), sort[0])); }
	 * 
	 * return new ResponseEntity<>(Response.ok().setPayload(studentService.
	 * getStudentListWithPagination(searchText, page, size, oprt,
	 * orders)),HttpStatus.OK); }
	 */

	@PostMapping("/")
	@ApiOperation(value = "Add new student", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> addNewStudent(@RequestBody StudentCreateRequest createRequest) {
		StudentDto studentDto = StudentHelper.validateAndCopy(createRequest, modelMapper);
		studentDto = studentService.addNewStudent(studentDto, getUserName(), createRequest.isCreateUser());
		return new ResponseEntity<>(Response.ok().setPayload(studentDto), HttpStatus.OK);
	}
	
    @PostMapping("/{studentid}/upload")
    @ApiOperation(value = "Upload new picture", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file, 
    		@PathVariable("studentid") @Valid String studentId) {
    	String imgUrl = this.amazonClient.uploadFile(file);
    	studentService.updateProfilePicture(imgUrl, studentId, getUserName());
    	return new ResponseEntity<>(Response.ok().setPayload(imgUrl), HttpStatus.OK);
    }
    
    @PostMapping("/{studentid}/change")
    @ApiOperation(value = "Change picture", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Response> changeFile(@RequestPart(value = "file") MultipartFile file, 
    		@RequestPart(value = "url") String fileUrl, 
    		@PathVariable("studentid") @Valid String studentId) {
    	String imgUrl =null;
    	 if("deleted".equals( amazonClient.deleteFileFromS3Bucket(fileUrl))) {
    		 imgUrl = this.amazonClient.uploadFile(file);
    	     studentService.updateProfilePicture(imgUrl, studentId, getUserName());
        }
    	return new ResponseEntity<>(Response.ok().setPayload(imgUrl), HttpStatus.OK);
    }

    @DeleteMapping("/{studentid}/delete")
    @ApiOperation(value = "Remove picture", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Response> deleteFile(@RequestPart(value = "url") String fileUrl, 
    		@PathVariable("studentid") @Valid String studentId) {
        if("deleted".equals( amazonClient.deleteFileFromS3Bucket(fileUrl))) {
        	 studentService.updateProfilePicture(null, studentId, getUserName()); 	
        }
    	return new ResponseEntity<>(Response.ok().setPayload("deleted"), HttpStatus.OK);
    }

	/*
	 * private Sort.Direction getSortDirection(String direction) { if
	 * (direction.equals("asc")) { return Sort.Direction.ASC; } else if
	 * (direction.equals("desc")) { return Sort.Direction.DESC; } return
	 * Sort.Direction.ASC; }
	 */
	
	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
