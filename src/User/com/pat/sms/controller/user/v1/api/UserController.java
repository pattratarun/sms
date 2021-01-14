package com.pat.sms.controller.user.v1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pat.sms.dto.response.Response;
import com.pat.sms.session.context.UserContext;
import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.dto.UserRoleDto;
import com.pat.sms.user.service.UserService;
import com.pat.sms.user.v1.helper.UserHelper;
import com.pat.sms.user.v1.reqresp.PriviledgeCreateRequest;
import com.pat.sms.user.v1.reqresp.RoleCreateRequest;
import com.pat.sms.user.v1.reqresp.UserSignupRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * Created by Tarun Pattra.
 */

@RestController
@RequestMapping("/api/v1/admin/user")
@Api(value = "sms-application", tags="User Controller")
@SuppressWarnings("rawtypes")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	/**
	 * Add New User
	 * @param userReqData
	 * @return
	 */
	@PostMapping("/")
	@ApiOperation(value ="Add/Create User", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> getUserDetails(@RequestBody UserSignupRequest userReqData) {
		UserDto userResp  = UserHelper.registerUser(userReqData, userService, getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(userResp),HttpStatus.OK);
    }
	
	/**
	 * Update existing user
	 * @param userReqData
	 * @return
	 */
	@PutMapping("/{username}")
	@ApiOperation(value ="Modify User", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> modifyUserDetails(@RequestBody UserSignupRequest userReqData) {
		UserDto userResp  = UserHelper.modifyUser(userReqData, userService, getUserName());
		UserContext.setCurrentUser(userResp);
		return new ResponseEntity<>(Response.ok().setPayload(userResp),HttpStatus.OK);
    }
	
	
	/**
	 * Delete user
	 * @param userName
	 * @return
	 */
	@DeleteMapping("/{username}")
	@ApiOperation(value ="Delete User", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<Response> deleteUser(@PathVariable("username") String userName) {
		boolean isDelete = userService.deleteUserById(userName);

		if (isDelete)
			return new ResponseEntity<>(Response.ok().setPayload("").setMetadata("Delete successfull"),
					HttpStatus.OK);
		else
			return new ResponseEntity<>(Response.notFound().setErrors("Delete unsucessfull"),
					HttpStatus.OK);
	}
	
	
	/**
	 * Add new role
	 * @param userReqData
	 * @return
	 */
	@PostMapping("/role")
	@ApiOperation(value ="Add/Create User Role", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> addUserRole(@RequestBody RoleCreateRequest roleReqData) {
		UserRoleDto userRoleResp  = userService.createUserRole(roleReqData.getRoleName(), 
				roleReqData.getStatus(), getUserName(), roleReqData.getPriviledgeStr());
		return new ResponseEntity<>(Response.ok().setPayload(userRoleResp),HttpStatus.OK);
    }
	
	
	/**
	 * Add new Priviledge
	 * @param userReqData
	 * @return
	 */
	@PostMapping("/priviledge")
	@ApiOperation(value ="Add/Create User Priviledge", authorizations = {@Authorization(value = "apiKey")})
    public  ResponseEntity<Response> addUserPriviledge(@RequestBody PriviledgeCreateRequest privReqData) {
		PriviledgeDto priviledgeResp  = userService.createPriviledge(privReqData.getPriviledgeName(), 
				privReqData.getPriviledgeID(), getUserName());
		return new ResponseEntity<>(Response.ok().setPayload(priviledgeResp),HttpStatus.OK);
    }
	
	private String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
}
