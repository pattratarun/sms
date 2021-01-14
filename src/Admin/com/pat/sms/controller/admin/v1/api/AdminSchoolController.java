package com.pat.sms.controller.admin.v1.api;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pat.sms.admin.service.AdminService;
import com.pat.sms.admin.v1.helper.AdminHelper;
import com.pat.sms.admin.v1.reqresp.SchoolReqResp;
import com.pat.sms.dto.response.Response;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.student.dto.SchoolDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Created by Tarun Pattra.
 */

@RestController
@RequestMapping("/api/v1/admin/school")
@Api(value = "sms-application", tags="Admin School Controller")
@SuppressWarnings("rawtypes")
public class AdminSchoolController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
    ModelMapper modelMapper;
	
	
	@GetMapping("/{schooluid}")
	@ApiOperation(value ="School Details", authorizations = {@Authorization(value = "apiKey")})
    public  Response getStudentDetails(@PathVariable("schooluid") @Valid String schooluid) {
    	if(schooluid !=null) {
    		SchoolDto schoolD = adminService.getSchoolDetails(schooluid);
    		return Response.ok().setPayload(schoolD);	
    	}
    	
    	return Response.badRequest().setPayload("Invalid Schooluid");	
    }
    
    @PostMapping("/s")
    @ApiOperation(value ="School Search", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<Response> searchStudent(@RequestBody SearchReqDto reqDto){
    	return new ResponseEntity<>(Response.ok().setPayload(adminService.searchSchool(reqDto)),HttpStatus.OK);
	}
    
    
    @PostMapping("/")
   	@ApiOperation(value ="Add new school", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<Response> createNewSchool(@RequestBody SchoolReqResp createRequest) {
			String userName = "tarun";
			SchoolDto schooDto = AdminHelper.validateAndCopy(createRequest, modelMapper);
			schooDto = adminService.addNewSchool(schooDto, userName);
			return new ResponseEntity<>(Response.ok().setPayload(schooDto),HttpStatus.OK);
	}
    
    @PutMapping("/")
   	@ApiOperation(value ="Edit school details", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<Response> modifyExistingSchool(@RequestBody SchoolReqResp createRequest) {
			String userName = "tarun";
			SchoolDto schooDto = AdminHelper.validateAndCopy(createRequest, modelMapper);
			schooDto = adminService.updateSchool(schooDto, userName);
			return new ResponseEntity<>(Response.ok().setPayload(schooDto),HttpStatus.OK);
	}
    
}
