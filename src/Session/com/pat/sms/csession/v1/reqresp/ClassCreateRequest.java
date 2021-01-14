package com.pat.sms.csession.v1.reqresp;

import javax.validation.constraints.NotNull;

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
public class ClassCreateRequest {

	private String uidStr;
	private String classId;
	
	@NotNull
    private String sessionYear;
	
	@NotNull
    private String sessionClass;
    private String section;
    private String remarks;
    private String sections;
    private String classes;
    

}
