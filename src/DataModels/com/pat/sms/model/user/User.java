package com.pat.sms.model.user;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.pat.sms.model.candidate.Candidate;
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
@Table(name = "user",
indexes = @Index(name = "idx_username", columnList = "username", unique = true))
public class User {

	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
	@Column(length = 150)
	private String username;
	
	@Column(length = 100)
	private String password;
	
	@Column(length = 15)
	private String status;
	
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
	@JoinColumn(name = "role")
	private UserRole role;
	
	private Boolean isAdmin;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_privilege", joinColumns = { @JoinColumn(name = "user_uid") }, inverseJoinColumns = {
			@JoinColumn(name = "privilege_id") })
	private Collection<PrivilegeMaster> priviledges;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Student student;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
	private Candidate candidate;
	
	/*
	@OneToOne(mappedBy = "user")
	private Guardian guardian;*/
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<UserLoginLogs> logs;
}
