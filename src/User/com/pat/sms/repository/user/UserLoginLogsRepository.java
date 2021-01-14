package com.pat.sms.repository.user;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.user.UserLoginLogs;

/**
 * Created by Tarun Pattra
 */
public interface UserLoginLogsRepository extends CrudRepository<UserLoginLogs, UUID> {
	
}
