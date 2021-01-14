package com.pat.sms.model.candidate;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.pat.sms.model.user.User;

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
@Table(name = "candidate", 
		indexes = { @Index(name = "idx_admissionNum", columnList = "admission_num", unique = true),
		@Index(name = "idx_studentId", columnList = "student_id", unique = true) })
public class Candidate {
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
	@Column(name="student_id", length = 25)
    private String studentId;
	
	@Column(name="admission_num", length = 25)
    private String admissionNum;
	
	@Column(length = 100)
    private String fName;
	
	@Column(length = 100)
	private String lName;
	
	@Column(length = 100)
    private String mName;
	
	@Column(length = 15)
    private String status;
	
	@Column(length = 15)
    private String examStatus;
	
	@Temporal(value = TemporalType.DATE)
    private Date dob;
	
	@Column(length = 3)
    private Integer age;
	
	@Column(length = 15)
    private String gender;
	
	@Column(length = 15)
    private String bloodGroup;
	
	
    private Boolean onlyChild;
	private Boolean isHostller;
    
	@Column(length = 150)
	private String email;
	
	@Temporal(value = TemporalType.DATE)
    private Date startDay;
    
	@Temporal(value = TemporalType.DATE)
    private Date endDay;
    
    @Column(length = 50)
    private String admissionCat1;
    
    @Column(length = 50)
    private String admissionCat2;
    
    @Column(length = 50)
    private String admissionCat3;
    
    @Column(length = 700)
    private String admissionRemarks;
    
    @Column(length = 500)
    private String address1;
    
    @Column(length = 500)
    private String address2;
    
    @Column(length = 50)
    private String city;
    
    @Column(length = 50)
    private String state;
    
    @Column(length = 50)
    private String country;
    
    @Column(length = 50)
    private String nationality;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 150)
    private String rfId;
    
    @Column(length = 150)
    private String additionalId1;
    
    @Column(length = 150)
    private String additionalId2;
    
    @Column(length = 15)
    private String siblingId;
    
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
    
    @Column(length = 300)
    private String profileImage;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CandidateEducationHistory> history;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CandidateGuardian> guardians;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CandidateInfo> studentInfos;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CandidateDocument> documents;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CandidateSibling> siblings;
    
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<CandidateNotification> notifications;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    
}
