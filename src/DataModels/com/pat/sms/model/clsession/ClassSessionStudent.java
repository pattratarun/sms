package com.pat.sms.model.clsession;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.pat.sms.model.student.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@DynamicUpdate
@Table(name = "class_session_student", 
indexes = @Index(name = "idx_studentID", columnList = "class_id,student_Id", unique = true)
	)
public class ClassSessionStudent {
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
    private Long rollNumber;
	
	@Column(length = 50)
    private String sessionClass;
	
    private Boolean monitor;
    
    @Column(length = 500)
    private String remarks;
    
    @Column(length = 15)
    private String status;
    
    @Column(length = 15)
    private String examStatus;
    
    
    @Column(name="class_id",length = 35)
	private String classId;
    
    @Column(name="student_Id", length = 25)
	private String studentId;
    
    
    @Column(length = 100)
    private String createdBy;
    
    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Column(length = 100)
    private String modifiedBy;
    
    @UpdateTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date modifiedDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classSession")
    private ClassSession classSession;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="student")
    private Student student;
}
