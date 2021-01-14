package com.pat.sms.student.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pat.sms.util.CommonUtil;

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
public class CandidateGuardianDto {
	
	private UUID uid;
	private String uidStr;
    private Boolean isPrimary;
    private String fName;
    private String lName;
    private String mName;
    private String relation;
    private Date dob;
    private Integer age;
    private String gender;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String nationality;
    private String phone1;
    private String phone2;
    private String aadharId;
    private String panId;
    private String qualification;
    private Double income;
    private Boolean isGovtEmp;
    private String designation;
    private String organization;
    private String officeAddress;
    private String profileImage;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String maritalStatus;

    public String getModifiedDateStr() {
    	return CommonUtil.convertDateToStringWithTime(modifiedDate);
    }
    
    public String getCreatedDateStr() {
    	return CommonUtil.convertDateToStringWithTime(createdDate);
    	
    }
    
    public String getUidStr() {
		this.uidStr = uid != null ? uid.toString() : null;
		return uidStr;
	}
    
}
