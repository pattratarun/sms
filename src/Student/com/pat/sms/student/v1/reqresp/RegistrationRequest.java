package com.pat.sms.student.v1.reqresp;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pat.sms.student.dto.CandidateDocumentDto;
import com.pat.sms.student.dto.CandidateEducationHistoryDto;
import com.pat.sms.student.dto.CandidateGuardianDto;
import com.pat.sms.student.dto.CandidateInfoDto;
import com.pat.sms.student.dto.CandidateNotificationDto;
import com.pat.sms.student.dto.CandidateSiblingDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationRequest {
	
	private UUID uid;
	
	private String uidStr;
	
	private String studentId;
	
    private String admissionNum;
	
    private String fName;
	
	private String lName;
	
    private String mName;
	
    private String status;
    
    private String examStatus;
	
    private Date dob;
	
    private Integer age;
	
    private String gender;
	
    private String bloodGroup;
	
	
    private Boolean onlyChild;
	private Boolean isHostller;
    
	private String email;
	
    private Date startDay;
    
    private Date endDay;
    
    private String admissionCat1;
    
    private String admissionCat2;
    
    private String admissionCat3;

    private String admissionRemarks;
    
    private String address1;
    
    private String address2;
    
    private String city;
    
    private String state;
    
    private String country;
    
    private String nationality;
    
    private String phone;
    
    private String rfId;
    
    private String additionalId1;
    
    private String additionalId2;
    
    private String siblingId;
    
    private String profileImage;
    
    private boolean userCreate;
    
    private Set<CandidateEducationHistoryDto> historyIdSet;

	private Set<CandidateGuardianDto> guardiansIdSet;

	private Set<CandidateInfoDto> candidateInfosIdSet;

	private Set<CandidateDocumentDto> documentsIdSet;

	private Set<CandidateSiblingDto> siblingsIdSet;

	private Set<CandidateNotificationDto> notificationsIdSet;
}
