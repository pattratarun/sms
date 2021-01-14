package com.pat.sms.student.dto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pat.sms.user.dto.UserDto;

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
public class StudentDto {
	
	private UUID uid;
	
	private String uidStr;
	
	private String studentId;
	
	private String wing;

	private String admissionNum;

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

	private Set<CandidateEducationHistoryDto> historyIdSet;

	private Set<CandidateGuardianDto> guardiansIdSet;

	private Set<CandidateInfoDto> InfosIdSet;

	private Set<CandidateDocumentDto> documentsIdSet;

	private Set<CandidateSiblingDto> siblingsIdSet;

	private Set<CandidateNotificationDto> notificationsIdSet;
	
	private UserDto user;

	public String getFullName() {
		return fName != null ? fName.concat(" ").concat(lName) : "";
	}
	
	private String schoolIdStr;
	
}
