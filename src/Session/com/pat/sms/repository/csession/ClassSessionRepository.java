package com.pat.sms.repository.csession;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.pat.sms.model.clsession.ClassSession;

/**
 * Created by Tarun Pattra
 */
public interface ClassSessionRepository extends JpaRepository<ClassSession, UUID>, JpaSpecificationExecutor<ClassSession>  {
	
	ClassSession findByclassId(String classId);

}
