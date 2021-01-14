package com.pat.sms.repository.master;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.common.ApplicationConstant;

/**
 * Created by Tarun Pattra
 */
public interface ApplicationConstantRepository extends CrudRepository<ApplicationConstant, UUID> {
}
