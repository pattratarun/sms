package com.pat.sms.repository.user;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.user.User;

/**
 * Created by Tarun Pattra
 */
public interface UserRepository extends CrudRepository<User, UUID> {
	
	User findByUsername(String username);
}
