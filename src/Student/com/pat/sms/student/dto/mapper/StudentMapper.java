package com.pat.sms.student.dto.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.pat.sms.model.student.EducationHistory;
import com.pat.sms.model.student.Guardian;
import com.pat.sms.model.student.Student;
import com.pat.sms.model.student.StudentDocument;
import com.pat.sms.model.student.StudentInfo;
import com.pat.sms.model.student.StudentNotification;
import com.pat.sms.model.student.StudentSibling;
import com.pat.sms.student.dto.CandidateDocumentDto;
import com.pat.sms.student.dto.CandidateEducationHistoryDto;
import com.pat.sms.student.dto.CandidateGuardianDto;
import com.pat.sms.student.dto.CandidateInfoDto;
import com.pat.sms.student.dto.CandidateNotificationDto;
import com.pat.sms.student.dto.CandidateSiblingDto;
import com.pat.sms.student.dto.StudentDto;

/**
 * Created by Tarun Pattra.
 */
@Component
public class StudentMapper {

	public static StudentDto toStudentDto(Student student) {
		return new StudentDto()
				.setUid(student.getUid())
				.setUidStr(student.getUid().toString())
				.setStudentId(student.getStudentId())
				.setAdditionalId1(student.getAdditionalId1())
				.setAdditionalId2(student.getAdditionalId2())
				.setAddress1(student.getAddress1())
				.setAddress2(student.getAddress2())
				.setAdmissionCat1(student.getAdmissionCat1())
				.setAdmissionCat2(student.getAdmissionCat2())
				.setAdmissionCat3(student.getAdmissionCat3())
				.setAdmissionNum(student.getAdmissionNum())
				.setAdmissionRemarks(student.getAdmissionRemarks())
				.setAge(student.getAge())
				.setBloodGroup(student.getBloodGroup())
				.setCity(student.getCity())
				.setCountry(student.getCountry())
				.setDob(student.getDob())
				.setEmail(student.getEmail())
				.setEndDay(student.getEndDay())
				.setFName(student.getFName())
				.setGender(student.getGender())
				.setIsHostller(student.getIsHostller())
				.setLName(student.getLName()).setMName(student.getMName())
				.setNationality(student.getNationality())
				.setOnlyChild(student.getOnlyChild())
				.setPhone(student.getPhone())
				.setProfileImage(student.getProfileImage())
				.setRfId(student.getRfId())
				.setSiblingId(student.getSiblingId())
				.setStartDay(student.getStartDay())
				.setState(student.getState())
				.setStatus(student.getStatus())
				.setHistoryIdSet(populateHistory(student.getHistory()))
				.setInfosIdSet(populateInfos(student.getStudentInfos()))
				.setDocumentsIdSet(populateDocs(student.getDocuments()))
				.setSiblingsIdSet(populateSibling(student.getSiblings()))
				.setNotificationsIdSet(populateNotification(student.getNotifications()))
				.setGuardiansIdSet(populateGuardian(student.getGuardians()));
	}
	
	
	private static Set<CandidateGuardianDto> populateGuardian(Set<Guardian> guardians) {
		Set<CandidateGuardianDto> guardianSet =new HashSet<>();
		for(Guardian h:guardians) {
			guardianSet.add(new CandidateGuardianDto()
					.setUid(h.getUid())
					.setUidStr(h.getUid().toString())
					.setAadharId(h.getAadharId())
					.setAddress1(h.getAddress1())
					.setAddress2(h.getAddress2())
					.setAge(h.getAge())
					.setCity(h.getCity())
					.setCountry(h.getCountry())
					.setDesignation(h.getDesignation())
					.setDob(h.getDob())
					.setEmail(h.getEmail())
					.setFName(h.getFName())
					.setMName(h.getMName())
					.setLName(h.getLName())
					.setGender(h.getGender())
					.setIncome(h.getIncome())
					.setIsGovtEmp(h.getIsGovtEmp())
					.setIsPrimary(h.getIsPrimary())
					.setNationality(h.getNationality())
					.setOfficeAddress(h.getOfficeAddress())
					.setOrganization(h.getOrganization())
					.setPanId(h.getPanId())
					.setPhone1(h.getPhone1())
					.setPhone2(h.getPhone2())
					.setQualification(h.getQualification())
					.setRelation(h.getRelation())
					.setState(h.getState())
					);
		}
		return guardianSet;
	}
	
	private static Set<CandidateEducationHistoryDto> populateHistory(Set<EducationHistory> histories) {
		Set<CandidateEducationHistoryDto> historySet =new HashSet<>();
		for(EducationHistory h:histories) {
			historySet.add(new CandidateEducationHistoryDto()
					.setUid(h.getUid())
					.setUidStr(h.getUid().toString())
					.setEndingYear(h.getEndingYear())
					.setInstitutionName(h.getInstitutionName())
					.setLastYearMarks(h.getLastYearMarks())
					.setStartingYear(h.getStartingYear())
					);
		}
		return historySet;
	}
	
	private static Set<CandidateInfoDto> populateInfos(Set<StudentInfo> infos) {
		Set<CandidateInfoDto> infoSet =new HashSet<>();
		for(StudentInfo h: infos) {
			infoSet.add(new CandidateInfoDto()
					.setUid(h.getUid())
					.setUidStr(h.getUid().toString())
					.setInfoType(h.getInfoType()) 
					.setInfoValue1(h.getInfoValue1())
					.setInfoValue2(h.getInfoValue2())
					);
		}
		return infoSet;
	}
	
	private static Set<CandidateDocumentDto> populateDocs(Set<StudentDocument> docs) {
		Set<CandidateDocumentDto> docSet =new HashSet<>();
		for(StudentDocument h: docs) {
			docSet.add(new CandidateDocumentDto()
					.setUid(h.getUid())
					.setUidStr(h.getUid().toString())
					.setDocumentType(h.getDocumentType())
					.setDocumentData(h.getDocumentData()));
		}
		return docSet;
	}
	
	private static Set<CandidateSiblingDto> populateSibling(Set<StudentSibling> sibls) {
		Set<CandidateSiblingDto> siblSet =new HashSet<>();
		for(StudentSibling h: sibls) {
			siblSet.add(new CandidateSiblingDto()
					.setUid(h.getUid())
					.setUidStr(h.getUid().toString())
					.setFName(h.getFName())
					.setMName(h.getMName())
					.setLName(h.getLName())
					.setAge(h.getAge())
					.setDob(h.getDob())
					.setGender(h.getGender())
					.setRelation(h.getRelation()));
		}
		return siblSet;
	}
	
	private static Set<CandidateNotificationDto> populateNotification(Set<StudentNotification> nofis) {
		Set<CandidateNotificationDto> notifSet =new HashSet<>();
		for(StudentNotification h: nofis) {
			notifSet.add(new CandidateNotificationDto()
					.setUid(h.getUid())
					.setUidStr(h.getUid().toString())
					.setNotificationMode(h.getNotificationMode())
					.setStatus(h.getStatus())
					.setValue(h.getValue())
					);
		}
		return notifSet;
	}

}
