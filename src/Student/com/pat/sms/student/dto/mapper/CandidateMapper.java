package com.pat.sms.student.dto.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.pat.sms.model.candidate.Candidate;
import com.pat.sms.model.candidate.CandidateDocument;
import com.pat.sms.model.candidate.CandidateEducationHistory;
import com.pat.sms.model.candidate.CandidateGuardian;
import com.pat.sms.model.candidate.CandidateInfo;
import com.pat.sms.model.candidate.CandidateNotification;
import com.pat.sms.model.candidate.CandidateSibling;
import com.pat.sms.model.student.EducationHistory;
import com.pat.sms.model.student.Guardian;
import com.pat.sms.model.student.Student;
import com.pat.sms.model.student.StudentDocument;
import com.pat.sms.model.student.StudentInfo;
import com.pat.sms.model.student.StudentNotification;
import com.pat.sms.model.student.StudentSibling;
import com.pat.sms.student.dto.CandidateDocumentDto;
import com.pat.sms.student.dto.CandidateDto;
import com.pat.sms.student.dto.CandidateEducationHistoryDto;
import com.pat.sms.student.dto.CandidateGuardianDto;
import com.pat.sms.student.dto.CandidateInfoDto;
import com.pat.sms.student.dto.CandidateNotificationDto;
import com.pat.sms.student.dto.CandidateSiblingDto;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra.
 */
@Component
public class CandidateMapper {
	
	private CandidateMapper() {}

	public static CandidateDto toCandidateDto(Candidate candidate, ModelMapper modelMapper) {
		CandidateDto candidateD  = modelMapper.map(candidate, CandidateDto.class);
		candidateD.setHistoryIdSet(populateHistory(candidate.getHistory(), modelMapper))
				.setCandidateInfosIdSet(populateInfos(candidate.getStudentInfos(), modelMapper))
				.setDocumentsIdSet(populateDocs(candidate.getDocuments(), modelMapper))
				.setSiblingsIdSet(populateSibling(candidate.getSiblings(), modelMapper))
				.setNotificationsIdSet(populateNotification(candidate.getNotifications(), modelMapper))
				.setGuardiansIdSet(populateGuardian(candidate.getGuardians(), modelMapper));
		return candidateD;
	}
	
	
	private static Set<CandidateGuardianDto> populateGuardian(Set<CandidateGuardian> guardians, ModelMapper modelMapper) {
		if(CommonUtil.isEmpty(guardians)) return new HashSet<>();
		Set<CandidateGuardianDto> guardianSet =new HashSet<>();
		for(CandidateGuardian h:guardians) {
			guardianSet.add(modelMapper.map(h, CandidateGuardianDto.class));
		}
		return guardianSet;
	}
	
	private static Set<CandidateEducationHistoryDto> populateHistory(Set<CandidateEducationHistory> histories, 
			ModelMapper modelMapper) {
		if(CommonUtil.isEmpty(histories)) return new HashSet<>();
		
		Set<CandidateEducationHistoryDto> historySet =new HashSet<>();
		for(CandidateEducationHistory h:histories) {
			historySet.add(modelMapper.map(h, CandidateEducationHistoryDto.class));
		}
		return historySet;
	}
	
	private static Set<CandidateInfoDto> populateInfos(Set<CandidateInfo> infos, ModelMapper modelMapper) {
		if(CommonUtil.isEmpty(infos)) return new HashSet<>();
		
		Set<CandidateInfoDto> infoSet =new HashSet<>();
		for(CandidateInfo h: infos) {
			infoSet.add(modelMapper.map(h, CandidateInfoDto.class));
		}
		return infoSet;
	}
	
	private static Set<CandidateDocumentDto> populateDocs(Set<CandidateDocument> docs, ModelMapper modelMapper) {
		if(CommonUtil.isEmpty(docs)) return new HashSet<>();
		Set<CandidateDocumentDto> docSet =new HashSet<>();
		for(CandidateDocument h: docs) {
			docSet.add(modelMapper.map(h, CandidateDocumentDto.class));
		}
		return docSet;
	}
	
	private static Set<CandidateSiblingDto> populateSibling(Set<CandidateSibling> sibls, ModelMapper modelMapper) {
		if(CommonUtil.isEmpty(sibls)) return new HashSet<>();
		Set<CandidateSiblingDto> siblSet =new HashSet<>();
		for(CandidateSibling h: sibls) {
			siblSet.add(modelMapper.map(h, CandidateSiblingDto.class));
		}
		return siblSet;
	}
	
	private static Set<CandidateNotificationDto> populateNotification(Set<CandidateNotification> nofis, 
			ModelMapper modelMapper) {
		if(CommonUtil.isEmpty(nofis)) return new HashSet<>();
		Set<CandidateNotificationDto> notifSet =new HashSet<>();
		for(CandidateNotification h: nofis) {
			notifSet.add(modelMapper.map(h, CandidateNotificationDto.class));
		}
		return notifSet;
	}

	
	
	public static Set<CandidateGuardian> populateGuardian(Set<CandidateGuardianDto> guardians, String userName, 
			Candidate candidate, ModelMapper modelMapper, List<String> warningErrorMessages) {
		if(CommonUtil.isEmpty(guardians)) return new HashSet<>();
		Set<CandidateGuardian> guardianSet =new HashSet<>();
		boolean isPrimaryFlag=false;
		for(CandidateGuardianDto h:guardians) {
			if(h.getIsPrimary()) {
				isPrimaryFlag=true;
			}
			guardianSet.add(modelMapper.map(h, CandidateGuardian.class)
					.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));
		}
		
		if(!isPrimaryFlag) {
			warningErrorMessages.add("E~Atleast one guardian should have primary");
		}
		
		
		return guardianSet;
	}
	
	public static Set<CandidateEducationHistory> populateHistory(Set<CandidateEducationHistoryDto> histories, String userName, 
			Candidate candidate, ModelMapper modelMapper, List<String> warningErrorMessages) {
		if(CommonUtil.isEmpty(histories)) return new HashSet<>();
		Set<CandidateEducationHistory> historySet =new HashSet<>();
		for(CandidateEducationHistoryDto h:histories) {
			historySet.add(modelMapper.map(h, CandidateEducationHistory.class)
					.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));
		}
		return historySet;
	}
	
	public static Set<CandidateInfo> populateInfos(Set<CandidateInfoDto> infos, String userName, 
			Candidate candidate, ModelMapper modelMapper, List<String> warningErrorMessages) {
		if(CommonUtil.isEmpty(infos)) return new HashSet<>();
		Set<CandidateInfo> infoSet =new HashSet<>();
		for(CandidateInfoDto h: infos) {
			infoSet.add(modelMapper.map(h, CandidateInfo.class)
					.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));
		}
		return infoSet;
	}
	
	public static Set<CandidateDocument> populateDocs(Set<CandidateDocumentDto> docs, String userName, 
			Candidate candidate, ModelMapper modelMapper, List<String> warningErrorMessages) {
		if(CommonUtil.isEmpty(docs)) return new HashSet<>();
		Set<CandidateDocument> docSet =new HashSet<>();
		for(CandidateDocumentDto h: docs) {
			docSet.add(modelMapper.map(h, CandidateDocument.class)
					.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));
		}
		return docSet;
	}
	
	public static Set<CandidateSibling> populateSibling(Set<CandidateSiblingDto> sibls, String userName, 
			Candidate candidate, ModelMapper modelMapper, List<String> warningErrorMessages) {
		if(CommonUtil.isEmpty(sibls)) return new HashSet<>();
		Set<CandidateSibling> siblSet =new HashSet<>();
		for(CandidateSiblingDto h: sibls) {
			siblSet.add(modelMapper.map(h, CandidateSibling.class)
					.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));
		}
		return siblSet;
	}
	
	public static Set<CandidateNotification> populateNotification(Set<CandidateNotificationDto> nofis, String userName, 
			Candidate candidate, ModelMapper modelMapper, List<String> warningErrorMessages) {
		if(CommonUtil.isEmpty(nofis)) return new HashSet<>();
		Set<CandidateNotification> notifSet =new HashSet<>();
		for(CandidateNotificationDto h: nofis) {
			notifSet.add(modelMapper.map(h, CandidateNotification.class)
					.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));
		}
		return notifSet;
	}
	
	

	public static void populateCandidateToStudent(Candidate candidate, Student student,String userName,  ModelMapper modelMapper) {
		
		student.setModifiedBy(userName)
		.setAdditionalId1(candidate.getAdditionalId1())
		.setAdditionalId2(candidate.getAdditionalId2())
		.setAddress1(candidate.getAddress1())
		.setAddress2(candidate.getAddress2())
		.setAdmissionCat1(candidate.getAdmissionCat1())
		.setAdmissionCat2(candidate.getAdmissionCat2())
		.setAdmissionCat3(candidate.getAdmissionCat3())
		.setAdmissionNum(candidate.getAdmissionNum())
		.setAdmissionRemarks(candidate.getAdmissionNum())
		.setAge(candidate.getAge())
		.setBloodGroup(candidate.getBloodGroup())
		.setCity(candidate.getCity())
		.setCountry(candidate.getCountry())
		.setDob(candidate.getDob())
		.setEmail(candidate.getEmail())
		.setEndDay(candidate.getEndDay())
		.setFName(candidate.getFName())
		.setGender(candidate.getGender())
		.setIsHostller(candidate.getIsHostller())
		.setLName(candidate.getLName())
		.setMName(candidate.getMName())
		.setNationality(candidate.getNationality())
		.setOnlyChild(candidate.getOnlyChild())
		.setPhone(candidate.getPhone())
		.setProfileImage(candidate.getProfileImage())
		.setRfId(candidate.getRfId())
		.setSiblingId(candidate.getSiblingId())
		.setStartDay(candidate.getStartDay())
		.setState(candidate.getState())
		.setStatus(candidate.getStatus())
		.setStudentId(candidate.getStudentId());
		
		
		populateHistoryToStudent(candidate,student, userName, modelMapper);
		populateInfosToStudent(candidate,student, userName, modelMapper);
		populateDocsToStudent(candidate,student, userName, modelMapper);
		populateSiblingToStudent(candidate,student, userName, modelMapper);
		populateNotificationToStudent(candidate,student, userName, modelMapper);
		populateGuardianToStudent(candidate,student, userName, modelMapper);
		
	}
	
	
	private static void populateHistoryToStudent(Candidate candidate, Student student,String userName, ModelMapper modelMapper) {
		Set<EducationHistory> history = new HashSet<>();
		for(CandidateEducationHistory a: candidate.getHistory()) {
			history.add(modelMapper.map(a, EducationHistory.class)
						.setCreatedBy(userName).setModifiedBy(userName).setStudent(student).setUid(null));	
		}
		student.setHistory(history);
	}
	
	private static void populateInfosToStudent(Candidate candidate, Student student,String userName, ModelMapper modelMapper) {
		Set<StudentInfo> infos = new HashSet<>();
		for (CandidateInfo a : candidate.getStudentInfos()) {
			infos.add(modelMapper.map(a, StudentInfo.class).setCreatedBy(userName)
						.setModifiedBy(userName).setStudent(student).setUid(null));
		}
		student.setStudentInfos(infos);
	}
	
	private static void populateDocsToStudent(Candidate candidate, Student student,String userName, ModelMapper modelMapper) {
		Set<StudentDocument> docs = new HashSet<>(); 
		for (CandidateDocument a : candidate.getDocuments()) {
			docs.add(modelMapper.map(a, StudentDocument.class).setCreatedBy(userName)
						.setModifiedBy(userName).setStudent(student).setUid(null));
		}
		student.setDocuments(docs);
	}
	
	private static void populateSiblingToStudent(Candidate candidate, Student student,String userName, ModelMapper modelMapper) {
		Set<StudentSibling> sibl = new HashSet<>();
		for (CandidateSibling a : candidate.getSiblings()) {
			sibl.add(modelMapper.map(a, StudentSibling.class).setCreatedBy(userName)
						.setModifiedBy(userName).setStudent(student).setUid(null));
		}
		student.setSiblings(sibl);
	}
	
	
	private static void populateNotificationToStudent(Candidate candidate, Student student,String userName, ModelMapper modelMapper) {
		Set<StudentNotification> noti = new HashSet<>();
		for (CandidateNotification a : candidate.getNotifications()) {
			noti.add(modelMapper.map(a, StudentNotification.class).setCreatedBy(userName)
						.setModifiedBy(userName).setStudent(student).setUid(null));
		}
		student.setNotifications(noti);
	}
	
	private static void populateGuardianToStudent(Candidate candidate, Student student,String userName, ModelMapper modelMapper) {
		Set<Guardian> guard = new HashSet<>();
		for (CandidateGuardian a : candidate.getGuardians()) {
			guard.add(modelMapper.map(a, Guardian.class).setCreatedBy(userName)
						.setModifiedBy(userName).setStudent(student).setUid(null));
		}
		student.setGuardians(guard);
	}
}
