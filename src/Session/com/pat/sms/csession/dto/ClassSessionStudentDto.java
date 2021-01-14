package com.pat.sms.csession.dto;

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
public class ClassSessionStudentDto {

	private UUID uid;
	private String uidStr;
	private Long rollNumber;
    private String sessionClass;
    private String status;
    private Boolean monitor;
    private String remarks;
    private String examStatus;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
	private String classId;
	private String studentId;
    
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
