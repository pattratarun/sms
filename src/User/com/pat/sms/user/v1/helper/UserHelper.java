package com.pat.sms.user.v1.helper;

import java.util.Set;

import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.dto.UserRoleDto;
import com.pat.sms.user.service.UserService;
import com.pat.sms.user.v1.reqresp.UserSignupRequest;

public class UserHelper {

	/**
	 * 
	 * @param userSignupRequest
	 * @param userService
	 * @return
	 */
	public static UserDto registerUser(UserSignupRequest userSignupRequest, UserService userService, String userName) {

		//UserRoleDto role = userService.findRoleById(userSignupRequest.getRoleid());
		//Set<PriviledgeDto> priviledges = userService.findPriviledgesById(userSignupRequest.getPrivileges());

		UserDto userDto = new UserDto().setUsername(userSignupRequest.getUsername())
				.setPassword(userSignupRequest.getPassword())
				.setAdmin(userSignupRequest.isAdmin())
				.setStatus(userSignupRequest.getStatus())
				.setRoleId(userSignupRequest.getRoleid())
				.setPriviledgesIds(userSignupRequest.getPrivileges());

		return userService.signup(userDto, userName);
	}
	
	
	/**
	 * 
	 * @param userSignupRequest
	 * @param userService
	 * @return
	 */
	public static UserDto modifyUser(UserSignupRequest userSignupRequest, UserService userService, String userName) {

		UserDto existingUser = userService.findUserByUsername(userSignupRequest.getUsername());
		
		if(existingUser != null) {
			UserRoleDto role = userService.findRoleById(userSignupRequest.getRoleid());
			Set<PriviledgeDto> priviledges = userService.findPriviledgesById(userSignupRequest.getPrivileges());

			UserDto userDto = existingUser.setUsername(userSignupRequest.getUsername())
					.setPassword(userSignupRequest.getPassword()).setAdmin(userSignupRequest.isAdmin())
					.setStatus(userSignupRequest.getStatus()).setRole(role).setPriviledges(priviledges);
			
			return userService.modifyUserProfile(userDto, userName);
		}
		
		return null;
		
	}

}
