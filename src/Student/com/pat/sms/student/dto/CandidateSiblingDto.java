package com.pat.sms.student.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pat.sms.util.CommonUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateSiblingDto {
	
	private UUID uid;
	private String uidStr;
    private String fName;
    private String lName;
    private String mName;
    private String relation;
    private Date dob;
    private Integer age;
    private String gender;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    
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
