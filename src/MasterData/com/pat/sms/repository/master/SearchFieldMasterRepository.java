package com.pat.sms.repository.master;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.common.SearchFieldMaster;

/**
 * Created by Tarun Pattra
 */
public interface SearchFieldMasterRepository extends CrudRepository<SearchFieldMaster, UUID> {
	
	SearchFieldMaster findBySearchScreen(String searchScreen);
	
}
