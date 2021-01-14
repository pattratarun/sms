package com.pat.sms.repository.student;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pat.sms.model.student.School;

/**
 * Created by Tarun Pattra
 */
public interface SchoolRepository extends JpaRepository<School, UUID>, JpaSpecificationExecutor<School>  {
	Page<School> findAll(Pageable pageable);
}
