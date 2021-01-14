package com.pat.sms.user.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.pat.sms.model.user.User;
import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.dto.UserRoleDto;

/**
 * Created by Tarun Pattra
 */
@Service
public interface UserService {
	
	UserDto findUserByUsername(String userName);
	
	UserRoleDto findRoleById(String roleId);
	
	Set<PriviledgeDto> findPriviledgesById(List<String> priviledgesIds);
	
	UserDto signup(UserDto userDto, String userName);
	
	boolean deleteUserById(String username);
	
	UserDto changePassword(UserDto userDto, String newPassword);

	UserDto modifyUserProfile(UserDto userDto, String userName);
	
	List<UserRoleDto> getAllRoles();
	
	List<PriviledgeDto> getAllPriviledge();
	
	UserRoleDto createUserRole(String roleName, String status, String userName, List<String> priviledges);
	
	PriviledgeDto createPriviledge(String priviledgeName, String priviledgeId, String userName);

	String createLoginLog(String userName);

	void logoutLog(String logUid);

	User createUser(UserDto userDto, String userName);
	
}