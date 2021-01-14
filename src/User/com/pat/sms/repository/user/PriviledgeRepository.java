package com.pat.sms.repository.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.user.PrivilegeMaster;

/**
 * Created by Tarun Pattra
 */
public interface PriviledgeRepository extends CrudRepository<PrivilegeMaster, UUID> {
	@Override
	List<PrivilegeMaster> findAll();
	
	List<PrivilegeMaster> findAllByPriviledgeIdIn(List<String> ids);
	
	PrivilegeMaster findByPriviledgeId(String ids);
}
