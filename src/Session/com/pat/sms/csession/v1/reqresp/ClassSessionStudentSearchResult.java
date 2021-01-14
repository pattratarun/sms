package com.pat.sms.csession.v1.reqresp;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pat.sms.util.CommonUtil;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Data
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassSessionStudentSearchResult {
	
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
