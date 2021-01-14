package com.pat.sms.controller.student.v1.api;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pat.sms.amazon.service.AmazonClient;
import com.pat.sms.dto.response.Response;
import com.pat.sms.exception.ErrorObject;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.student.dto.CandidateDto;
import com.pat.sms.student.service.CandidateService;
import com.pat.sms.student.v1.helper.RegistrationHelper;
import com.pat.sms.student.v1.reqresp.RegistrationRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Created by Tarun Pattra.
 */

@RestController
@RequestMapping("/api/v1/candidate")
@Api(value = "sms-application", tags="Student Registration Controller")
@SuppressWarnings("rawtypes")
public class StudentRegistrationController {
	
	@Autowired
    private AmazonClient amazonClient;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CandidateService candidateService;

	@PostMapping("/s")
	@ApiOperation(value = "Candidate Search", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> searchCandidate(@RequestBody SearchReqDto reqDto) {
		return new ResponseEntity<>(Response.ok().setPayload(candidateService.searchCandidate(reqDto)), HttpStatus.OK);
	}
	
	@GetMapping("/{studentid}")
	@ApiOperation(value = "Candidate details", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> getCandidate(@PathVariable("studentid") @Valid String studentId) {
		return new ResponseEntity<>(Response.ok().setPayload(candidateService.getCandidateDetails(studentId)), HttpStatus.OK);
	}
	
	@DeleteMapping("/{studentid}")
	@ApiOperation(value = "Delete Candidate", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> deleteCandite(@PathVariable("studentid") @Valid String studentId) {
		return new ResponseEntity<>(Response.ok().setPayload(candidateService.deleteCandidate(studentId)), HttpStatus.OK);
	}

	@PostMapping("/")
	@ApiOperation(value = "Add Candidate", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> registerCandidate(@RequestBody RegistrationRequest candRequest) {
		CandidateDto candidateDto = RegistrationHelper.validateAndCopy(candRequest, modelMapper);
		candidateDto = candidateService.registerNewCandidate(candidateDto, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(candidateDto), HttpStatus.OK);
	}

	@PutMapping("/")
	@ApiOperation(value = "Update candidate", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> modifyCandidate(@RequestBody RegistrationRequest candRequest) {
		CandidateDto candidateDto = RegistrationHelper.validateAndCopy(candRequest, modelMapper);
		candidateDto = candidateService.updateExistingCandidate(candidateDto, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(candidateDto), HttpStatus.OK);
	}
	
	
	@PutMapping("/move")
	@ApiOperation(value = "Convert candidate to student", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> candidateToStudent(@RequestBody List<String> candidateIds) {
		List<ErrorObject> resp = candidateService.candidateToStudent(candidateIds, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(resp), HttpStatus.OK);
	}
	
	@PostMapping("/{studentid}/upload")
    @ApiOperation(value = "Upload new picture", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file, 
    		@PathVariable("studentid") @Valid String studentId) {
    	String imgUrl = this.amazonClient.uploadFile(file);
    	candidateService.updateProfilePicture(imgUrl, studentId, getUserName());
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
    		 candidateService.updateProfilePicture(imgUrl, studentId, getUserName());
        }
    	return new ResponseEntity<>(Response.ok().setPayload(imgUrl), HttpStatus.OK);
    }

    @DeleteMapping("/{studentid}/delete")
    @ApiOperation(value = "Remove picture", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Response> deleteFile(@RequestPart(value = "url") String fileUrl, 
    		@PathVariable("studentid") @Valid String studentId) {
        if("deleted".equals( amazonClient.deleteFileFromS3Bucket(fileUrl))) {
        	candidateService.updateProfilePicture(null, studentId, getUserName()); 	
        }
    	return new ResponseEntity<>(Response.ok().setPayload("deleted"), HttpStatus.OK);
    }
	
	
	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
