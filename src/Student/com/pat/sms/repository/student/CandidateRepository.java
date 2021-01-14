package com.pat.sms.repository.student;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.pat.sms.model.candidate.Candidate;

/**
 * Created by Tarun Pattra
 */
public interface CandidateRepository extends CrudRepository<Candidate, UUID> , JpaSpecificationExecutor<Candidate>  {
	
	Candidate findByAdmissionNum(String admissionNum);
	Candidate findByStudentId(String studentId);
	Page<Candidate> findAll(Pageable pageable);

}
