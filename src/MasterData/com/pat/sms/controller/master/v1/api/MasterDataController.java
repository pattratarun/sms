package com.pat.sms.controller.master.v1.api;

import static com.pat.sms.util.DropDownKeys.KEY_ADMISSION_CAT1;
import static com.pat.sms.util.DropDownKeys.KEY_ADMISSION_CAT2;
import static com.pat.sms.util.DropDownKeys.KEY_ADMISSION_CAT3;
import static com.pat.sms.util.DropDownKeys.KEY_CLASS_LEVEL;
import static com.pat.sms.util.DropDownKeys.KEY_CLASS_SECTIONS;
import static com.pat.sms.util.DropDownKeys.KEY_DEPARTMENT;
import static com.pat.sms.util.DropDownKeys.KEY_DESIGNATION;
import static com.pat.sms.util.DropDownKeys.KEY_DOCUMENT_TYPE;
import static com.pat.sms.util.DropDownKeys.KEY_INFO_TYPE;
import static com.pat.sms.util.DropDownKeys.KEY_JOB_ROLE;
import static com.pat.sms.util.DropDownKeys.KEY_MONTHS;
import static com.pat.sms.util.DropDownKeys.KEY_NOTIFICATION_MODE;
import static com.pat.sms.util.DropDownKeys.KEY_RELATIONS;
import static com.pat.sms.util.StringConstants.COLON;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.pat.sms.dto.response.Response;
import com.pat.sms.master.service.MasterDataService;
import com.pat.sms.master.v1.reqresp.LabelValueBean;
import com.pat.sms.master.v1.reqresp.SearchField;
import com.pat.sms.student.service.StudentService;
import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserRoleDto;
import com.pat.sms.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Created by Tarun Pattra.
 */

@RestController
@RequestMapping("/api/v1/master")
@Api(value = "sms-application", tags="Master Data Controller")
@SuppressWarnings("rawtypes")
public class MasterDataController {
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MasterDataService masteService;
	
	@GetMapping("/cat1")
	@ApiOperation(value ="Admission Category1", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> getAdmissionCat1() {
		List<String> catList =masteService.getDropdown().get(KEY_ADMISSION_CAT1);
		List<LabelValueBean> cat = catList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(cat),HttpStatus.OK);
    }
    
    @GetMapping("/cat2")
	@ApiOperation(value ="Admission Category2", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> getAdmissionCat2() {
    	List<String> catList =masteService.getDropdown().get(KEY_ADMISSION_CAT2);
		List<LabelValueBean> cat = catList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(cat),HttpStatus.OK);
    }
    
    @GetMapping("/cat3")
	@ApiOperation(value ="Admission Category3", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> getAdmissionCat3() {
    	List<String> catList =masteService.getDropdown().get(KEY_ADMISSION_CAT3);
		List<LabelValueBean> cat = catList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(cat),HttpStatus.OK);
    }

    @GetMapping("/school")
	@ApiOperation(value ="School List", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> getSchoolList() {
    	List<LabelValueBean> schoolList =masteService.getSchools();
    	return new ResponseEntity<>(Response.ok().setPayload(schoolList),HttpStatus.OK);
    }
    
    @GetMapping("/userroles")
   	@ApiOperation(value ="User Role List", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getUserRoleList() {
    	List<UserRoleDto> roles = 	userService.getAllRoles();
    	return new ResponseEntity<>(Response.ok().setPayload(roles),HttpStatus.OK);
    }
    
    @GetMapping("/priviledges")
   	@ApiOperation(value ="User Priviledge List", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getUserPriviledgeList() {
    	List<PriviledgeDto> priviledgeList = 	userService.getAllPriviledge();
    	return new ResponseEntity<>(Response.ok().setPayload(priviledgeList),HttpStatus.OK);
    }
    
    @GetMapping("/addinfos")
   	@ApiOperation(value ="Student Additional Info Type", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getInfoList() {
    	List<String> infoList =masteService.getDropdown().get(KEY_INFO_TYPE);
		List<LabelValueBean> info = infoList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(info),HttpStatus.OK);
    }
    
    @GetMapping("/relations")
   	@ApiOperation(value ="Relations", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getRelationList() {
    	List<String> relList =masteService.getDropdown().get(KEY_RELATIONS);
		List<LabelValueBean> rel = relList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(rel),HttpStatus.OK);
    }
    
    @GetMapping("/documenttypes")
   	@ApiOperation(value ="Document Types", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getDocumentTypeList() {
    	List<String> docTypeList =masteService.getDropdown().get(KEY_DOCUMENT_TYPE);
		List<LabelValueBean> doc = docTypeList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(doc),HttpStatus.OK);
    }
    
    @GetMapping("/notificationmodes")
   	@ApiOperation(value ="Notification Mode", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getNotificationModeList() {
    	List<String> notiList =masteService.getDropdown().get(KEY_NOTIFICATION_MODE);
		List<LabelValueBean> noti = notiList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(noti),HttpStatus.OK);
    }
    
    @GetMapping("/departments")
   	@ApiOperation(value ="Department", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getDepartmentList() {
    	List<String> depList =masteService.getDropdown().get(KEY_DEPARTMENT);
		List<LabelValueBean> dep = depList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(dep),HttpStatus.OK);
    }
    
    @GetMapping("/jobroles")
   	@ApiOperation(value ="Job Role", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getJobRoleList() {
    	List<String> rolList =masteService.getDropdown().get(KEY_JOB_ROLE);
		List<LabelValueBean> jobRole = rolList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(jobRole),HttpStatus.OK);
    }
    
    @GetMapping("/designations")
   	@ApiOperation(value ="Designation", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getDesignationList() {
    	List<String> designList =masteService.getDropdown().get(KEY_DESIGNATION);
		List<LabelValueBean> desig = designList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(desig),HttpStatus.OK);
    }
    
    @GetMapping("/months")
   	@ApiOperation(value ="Months", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getMonthList() {
    	List<String> monthList =masteService.getDropdown().get(KEY_MONTHS);
		 List<LabelValueBean> months = monthList.stream().map(v -> new LabelValueBean(v.split(COLON)[0], v.split(COLON)[1]))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(months),HttpStatus.OK);
    }
    
    @GetMapping("/cache/reset")
   	@ApiOperation(value ="Application Constant Reset Cache", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> resetCache() {
    	masteService.resetAllCaches();
    	return new ResponseEntity<>(Response.ok().setPayload("Reset Successful"),HttpStatus.OK);
    }
    
    
    @GetMapping("/searchfields/{name}")
   	@ApiOperation(value ="", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response>  getSearchFields(@PathVariable("name") @Valid String searchName) 
    		   throws JsonMappingException, JsonProcessingException {
    	   List<SearchField> fieldList = masteService.getSearchFeilds(searchName);
    	return new ResponseEntity<>(Response.ok().setPayload(fieldList),HttpStatus.OK);
    }
    
    @GetMapping("/classes")
   	@ApiOperation(value ="Designation", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getClasses() {
    	List<String> classList =masteService.getDropdown().get(KEY_CLASS_LEVEL);
		List<LabelValueBean> clas = classList.stream().map(v -> new LabelValueBean(v.split(COLON)[0], v.split(COLON)[1]))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(clas),HttpStatus.OK);
    }
    
    @GetMapping("/sections")
   	@ApiOperation(value ="Designation", authorizations = {@Authorization(value = "apiKey")})
       public  ResponseEntity<Response> getSections() {
    	List<String> sectionList =masteService.getDropdown().get(KEY_CLASS_SECTIONS);
		List<LabelValueBean> secs = sectionList.stream().map(v -> new LabelValueBean(v,v))
				.collect(Collectors.toList());
    	return new ResponseEntity<>(Response.ok().setPayload(secs),HttpStatus.OK);
    }
}
