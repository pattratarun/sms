package com.pat.sms.repository.student;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pat.sms.model.student.Student;

/**
 * Created by Tarun Pattra
 */
public interface StudentRepository extends JpaRepository<Student, UUID>, JpaSpecificationExecutor<Student>  {
	
	Student findByStudentId(String studentId);
	
	@Query("SELECT count(*) FROM Student t WHERE t.fName like concat(:f,'%') and t.lName like concat(:l,'%')")
	Integer findAllByName(@Param("f") char fName, @Param("l") char lName);
	
	Page<Student> findAll(Pageable pageable);
	Page<Student> findByStudentIdIgnoreCase(String title, Pageable pageable);
	Page<Student> findByStudentIdLikeIgnoreCase(String title, Pageable pageable);

}
