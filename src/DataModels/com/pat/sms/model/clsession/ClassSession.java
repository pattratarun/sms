package com.pat.sms.model.clsession;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "class_session",
indexes = @Index(name = "idx_classId", columnList = "class_id", unique = true)
	)
public class ClassSession {
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
	@Column(length = 25)
    private String sessionYear;
	
	@Column(name="class_id",length = 35)
	private String classId;
	
	@Column(length = 15)
    private String sessionClass;
	
	@Column(length = 5)
    private String section;
	
	@Column(length = 500)
    private String remarks;
	
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
    
    @OneToMany(mappedBy = "classSession", fetch=FetchType.LAZY)
    private Set<ClassSessionStudent> classStudents;
    
}
