package com.pat.sms.repository.master;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.common.DropdownMaster;

/**
 * Created by Tarun Pattra
 */
public interface DropdownRepository extends CrudRepository<DropdownMaster, UUID> {
}
