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
public class CandidateEducationHistoryDto {

	private UUID uid;
	private String uidStr;
    private String institutionName;
    private Date startingYear;
    private Date endingYear;
    private Double lastYearMarks;
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
