package com.pat.sms.repository.csession;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pat.sms.model.clsession.ClassSessionStudent;

/**
 * Created by Tarun Pattra
 */
public interface ClassSessionStudentRepository extends JpaRepository<ClassSessionStudent, UUID>, JpaSpecificationExecutor<ClassSessionStudent>  {
	
	ClassSessionStudent findByClassIdAndStudentId(String classId, String studentId);
	
	Set<ClassSessionStudent> findByClassId(String classId);
	
}
