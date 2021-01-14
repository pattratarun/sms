package com.pat.sms.repository.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.user.UserRole;

/**
 * Created by Tarun Pattra
 */
public interface UserRoleRepository extends CrudRepository<UserRole, UUID> {
	@Override
	List<UserRole> findAll();
	
	UserRole findByRoleName(String roleName);
}
