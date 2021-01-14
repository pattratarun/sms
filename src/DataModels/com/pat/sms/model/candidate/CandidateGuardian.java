package com.pat.sms.model.candidate;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(
        name = "candidate_guardian"
)
public class CandidateGuardian {
	@Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;
	
	
    private Boolean isPrimary;
    
    @Column(length = 100)
    private String fName;
    
    @Column(length = 100)
    private String lName;
    
    @Column(length = 100)
    private String mName;
    
    @Column(length = 50)
    private String relation;
    
    @Temporal(value = TemporalType.DATE)
    private Date dob;
    
    @Column(length = 5)
    private Integer age;
    
    @Column(length = 15)
    private String gender;
    
    @Column(length = 150)
    private String email;
    
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
    private String phone1;
    
    @Column(length = 20)
    private String phone2;
    
    @Column(length = 50)
    private String aadharId;
    
    @Column(length = 20)
    private String panId;
    
    @Column(length = 50)
    private String qualification;
    
    @Column(precision=15, scale=2)
    private Double income;
    
    
    private Boolean isGovtEmp;
    
    @Column(length = 25)
    private String designation;
    
    @Column(length = 50)
    private String organization;
    
    @Column(length = 300)
    private String officeAddress;
    
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
    
    @Column(length = 50)
    private String maritalStatus;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate")
    private Candidate candidate;
    
}
