package com.pat.sms.csession.v1.reqresp;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class StudentInClassRequest {

	private UUID uid;
	private String uidStr;
    private long rollNumber;
    private String sessionClass;
    private boolean monitor;
    private String remarks;
    private String examStatus;
	private String classId;
	private String studentId;

}
