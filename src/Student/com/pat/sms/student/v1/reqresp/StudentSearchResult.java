package com.pat.sms.student.v1.reqresp;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class StudentSearchResult {
	
	private String studentID;
    private String admissionNum;
    private String schoolId;
    private String wing;
    private String fName;
	private String lName;
    private String mName;
    private String status;
    private Date dob;
    private Integer age;
    private String gender;
    private String bloodGroup;
    private Boolean onlyChild;
	private Boolean isHostller;
    private String rfId;
    private String additionalId1;
    private String additionalId2;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    

}
