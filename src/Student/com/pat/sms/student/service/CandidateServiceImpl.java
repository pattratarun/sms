package com.pat.sms.student.service;

import static com.pat.sms.exception.EntityType.CANDIDATE;
import static com.pat.sms.exception.EntityType.STUDENT;
import static com.pat.sms.exception.ExceptionType.ENTITY_EXCEPTION;
import static com.pat.sms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.pat.sms.util.DropDownKeys.KEY_DEFAULT_PASS;
import static com.pat.sms.util.DropDownKeys.KEY_EMAIL_ID;
import static com.pat.sms.util.StringConstants.DEFAULT_PROP;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pat.sms.enums.StudentStatusEnum;
import com.pat.sms.exception.EntityType;
import com.pat.sms.exception.ErrorObject;
import com.pat.sms.exception.ExceptionType;
import com.pat.sms.exception.SMSException;
import com.pat.sms.master.service.MasterDataService;
import com.pat.sms.model.candidate.Candidate;
import com.pat.sms.model.candidate.CandidateDocument;
import com.pat.sms.model.candidate.CandidateEducationHistory;
import com.pat.sms.model.candidate.CandidateGuardian;
import com.pat.sms.model.candidate.CandidateInfo;
import com.pat.sms.model.candidate.CandidateNotification;
import com.pat.sms.model.candidate.CandidateSibling;
import com.pat.sms.model.student.Student;
import com.pat.sms.model.user.User;
import com.pat.sms.repository.student.CandidateRepository;
import com.pat.sms.repository.student.StudentRepository;
import com.pat.sms.repository.user.UserRepository;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.search.SearchUtil;
import com.pat.sms.student.dto.CandidateDocumentDto;
import com.pat.sms.student.dto.CandidateDto;
import com.pat.sms.student.dto.CandidateEducationHistoryDto;
import com.pat.sms.student.dto.CandidateGuardianDto;
import com.pat.sms.student.dto.CandidateInfoDto;
import com.pat.sms.student.dto.CandidateNotificationDto;
import com.pat.sms.student.dto.CandidateSiblingDto;
import com.pat.sms.student.dto.mapper.CandidateMapper;
import com.pat.sms.student.v1.reqresp.CandidateSearchResult;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.service.UserService;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra.
 */

@Component
@Transactional
public class CandidateServiceImpl implements CandidateService {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(CandidateServiceImpl.class);
	
	@Autowired
	CandidateRepository candidateRepository;
	
	@Autowired
	MasterDataService masterconstant;

	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
    private ModelMapper modelMapper;

	@Override
	public CandidateDto getCandidateDetails(String studentID) {
		Optional<Candidate> candidate	 = Optional.ofNullable(candidateRepository.findByStudentId(studentID));
        if (candidate.isPresent()) {
             return  CandidateMapper.toCandidateDto(candidate.get(), modelMapper);
        }
        throw exception(CANDIDATE, ENTITY_NOT_FOUND, studentID);
	}
	
	@Override
	public boolean deleteCandidate(String studentId) {
		Optional<Candidate> candidate	 = Optional.ofNullable(candidateRepository.findByStudentId(studentId));
        if (candidate.isPresent()) {
        	candidateRepository.delete(candidate.get());
        }
        
        throw exception(CANDIDATE, ENTITY_NOT_FOUND, studentId);
	}
	
	
	@Override
	public SearchResDto searchCandidate(SearchReqDto reqDto) {
		PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
				Sort.by(SearchUtil.getOrders(reqDto.getSorts(), DEFAULT_PROP)));
		Page<Candidate> page = candidateRepository.findAll(SearchUtil.createSpec(reqDto.getQuery()), pageRequest);
		Function<Candidate, CandidateSearchResult> mapper = (s) -> SearchUtil.createCopyObject(s, CandidateSearchResult::new);
		return SearchUtil.prepareResponseForSearch(page, mapper);
	}
	
	
	private String generateStudentID(CandidateDto candidate) {
		char fNamechar = candidate.getFName().charAt(0);
		char lNamechar = candidate.getLName().charAt(0);
		String finalStudentID = null;
		do {
			finalStudentID = new StringBuilder().append(fNamechar).append(lNamechar)
					.append(masterconstant.generateID(EntityType.STUDENT.name()))
					.append(CommonUtil.getNumberFormat(ThreadLocalRandom.current().nextInt(1000),4))
					.toString();	
		}while(finalStudentID !=null && !CommonUtil.isEmpty(studentRepository.findByStudentId(finalStudentID)));
		

		return finalStudentID.toUpperCase();
	}
	


	@Override
	public CandidateDto registerNewCandidate(CandidateDto candidate, String userName) {
		List<String> warningErrorMessage= new ArrayList<>();
		try {
			modelMapper.getConfiguration().setAmbiguityIgnored(true);
			Candidate newCandidate = modelMapper.map(candidate, Candidate.class);
			String studentid =generateStudentID(candidate);
			String email = studentid + "@" + masterconstant.getAllConstants().get(KEY_EMAIL_ID);
			
			newCandidate
			.setCreatedBy(userName)
			.setModifiedBy(userName)
			.setStudentId(studentid)
			.setEmail(email.toLowerCase())
			.setAdmissionNum(candidate.getAdmissionNum())
			.setGuardians(CandidateMapper.populateGuardian(candidate.getGuardiansIdSet(), userName,newCandidate,modelMapper,warningErrorMessage))
			.setHistory(CandidateMapper.populateHistory(candidate.getHistoryIdSet(), userName,newCandidate,modelMapper,warningErrorMessage))
			.setStudentInfos(CandidateMapper.populateInfos(candidate.getCandidateInfosIdSet(), userName,newCandidate,modelMapper,warningErrorMessage))
			.setDocuments(CandidateMapper.populateDocs(candidate.getDocumentsIdSet(), userName,newCandidate,modelMapper,warningErrorMessage))
			.setSiblings(CandidateMapper.populateSibling(candidate.getSiblingsIdSet(), userName,newCandidate,modelMapper,warningErrorMessage))
			.setNotifications(CandidateMapper.populateNotification(candidate.getNotificationsIdSet(), userName,newCandidate,modelMapper,warningErrorMessage));
			
			if(!CommonUtil.isEmpty(warningErrorMessage)) {
				throw exception(CANDIDATE, ENTITY_EXCEPTION, candidate.getFName());	
			}
			
			if (candidate.isUserCreate()) {
				UserDto newUser = new UserDto().setUsername(email)
						.setPassword(masterconstant.getAllConstants().get(KEY_DEFAULT_PASS)).setAdmin(false)
						.setEntityType(STUDENT).setStatus("active");
				newCandidate.setUser(userService.createUser(newUser, userName));
			}
			
			candidateRepository.save(newCandidate);

			return  CandidateMapper.toCandidateDto(newCandidate, modelMapper);
		} catch (Exception e) {
			LOGGER.error("registerNewCandidate:",e);
			throw exception(CANDIDATE, ENTITY_EXCEPTION, candidate.getFName());
		}
	}

	
	@Override
	public CandidateDto updateExistingCandidate(CandidateDto candidate, String userName) {

		try {

			Optional<Candidate> existingCandidate = candidateRepository
					.findById(UUID.fromString(candidate.getUidStr()));

			if (existingCandidate.isPresent()) {
				populateCandidate(candidate, existingCandidate.get(), userName);
				candidateRepository.save(existingCandidate.get());
			}

			return CandidateMapper.toCandidateDto(existingCandidate.get(), modelMapper);
		} catch (Exception e) {
			LOGGER.error("updateExistingCandidate:",e);
			throw exception(CANDIDATE, ENTITY_EXCEPTION, candidate.getFName());
		}
	}
	
	
	private void populateCandidate(CandidateDto candidateDto, Candidate candidate, String userName) {
		
		candidate.setModifiedBy(userName)
		.setAdditionalId1(candidateDto.getAdditionalId1())
		.setAdditionalId2(candidateDto.getAdditionalId2())
		.setAddress1(candidateDto.getAddress1())
		.setAddress2(candidateDto.getAddress2())
		.setAdmissionCat1(candidateDto.getAdmissionCat1())
		.setAdmissionCat2(candidateDto.getAdmissionCat2())
		.setAdmissionCat3(candidateDto.getAdmissionCat3())
		.setAdmissionNum(candidateDto.getAdmissionNum())
		.setAdmissionRemarks(candidateDto.getAdmissionNum())
		.setAge(candidateDto.getAge())
		.setBloodGroup(candidateDto.getBloodGroup())
		.setCity(candidateDto.getCity())
		.setCountry(candidateDto.getCountry())
		.setDob(candidateDto.getDob())
		.setEmail(candidateDto.getEmail())
		.setEndDay(candidateDto.getEndDay())
		.setExamStatus(candidateDto.getExamStatus())
		.setFName(candidateDto.getFName())
		.setGender(candidateDto.getGender())
		.setIsHostller(candidateDto.getIsHostller())
		.setLName(candidateDto.getLName())
		.setMName(candidateDto.getMName())
		.setNationality(candidateDto.getNationality())
		.setOnlyChild(candidateDto.getOnlyChild())
		.setPhone(candidateDto.getPhone())
		.setProfileImage(candidateDto.getProfileImage())
		.setRfId(candidateDto.getRfId())
		.setSiblingId(candidateDto.getSiblingId())
		.setStartDay(candidateDto.getStartDay())
		.setState(candidateDto.getState())
		.setStatus(candidateDto.getStatus())
		.setStudentId(candidateDto.getStudentId());
		
		
		populateHistory(candidateDto,candidate, userName);
		populateInfos(candidateDto,candidate, userName);
		populateDocs(candidateDto,candidate, userName);
		populateSibling(candidateDto,candidate, userName);
		populateNotification(candidateDto,candidate, userName);
		populateGuardian(candidateDto,candidate, userName);
		
	}
	
	
	private void populateHistory(CandidateDto candidateDto, Candidate candidate, String userName) {
		for(CandidateEducationHistory s: candidate.getHistory()) {
			for(CandidateEducationHistoryDto a: candidateDto.getHistoryIdSet()) {
				if(s.getUid().toString().equals(a.getUidStr())) {
					s.setInstitutionName(a.getInstitutionName())
					.setEndingYear(a.getEndingYear())
					.setLastYearMarks(a.getLastYearMarks())
					.setModifiedBy(userName)
					.setStartingYear(a.getStartingYear());
					break;
				}
			}
		}
		
		for(CandidateEducationHistoryDto a: candidateDto.getHistoryIdSet()) {
			if(CommonUtil.isEmpty(a.getUidStr())) {
				candidate.getHistory().add(modelMapper.map(a, CandidateEducationHistory.class)
						.setCreatedBy(userName).setModifiedBy(userName).setCandidate(candidate));	
			}
		}
	}
	
	
	private void populateInfos(CandidateDto candidateDto, Candidate candidate, String userName) {
		for(CandidateInfo s: candidate.getStudentInfos()) {
			for(CandidateInfoDto a: candidateDto.getCandidateInfosIdSet()) {
				if(s.getUid().toString().equals(a.getUidStr())) {
					s.setInfoType(a.getInfoType())
					.setInfoValue1(a.getInfoValue1())
					.setInfoValue2(a.getInfoValue2())
					.setModifiedBy(userName);
					break;
				}
			}
		}
		
		for (CandidateInfoDto a : candidateDto.getCandidateInfosIdSet()) {
			if (CommonUtil.isEmpty(a.getUidStr()))
				candidate.getStudentInfos().add(modelMapper.map(a, CandidateInfo.class).setCreatedBy(userName)
						.setModifiedBy(userName).setCandidate(candidate));
		}
	}
	
	private void populateDocs(CandidateDto candidateDto, Candidate candidate, String userName) {
		for(CandidateDocument s: candidate.getDocuments()) {
			for(CandidateDocumentDto a: candidateDto.getDocumentsIdSet()) {
				if(s.getUid().toString().equals(a.getUidStr())) {
					s.setDocumentData(a.getDocumentData())
					.setDocumentType(a.getDocumentType())
					.setModifiedBy(userName);
					break;
				}
			}
		}
		
		for (CandidateDocumentDto a : candidateDto.getDocumentsIdSet()) {
			if (CommonUtil.isEmpty(a.getUidStr()))
				candidate.getDocuments().add(modelMapper.map(a, CandidateDocument.class).setCreatedBy(userName)
						.setModifiedBy(userName).setCandidate(candidate));
		}
	}
	
	private void populateSibling(CandidateDto candidateDto, Candidate candidate, String userName) {
		for(CandidateSibling s: candidate.getSiblings()) {
			for(CandidateSiblingDto a: candidateDto.getSiblingsIdSet()) {
				if(s.getUid().toString().equals(a.getUidStr())) {
					s.setAge(a.getAge())
					.setDob(a.getDob())
					.setFName(a.getFName())
					.setGender(a.getGender())
					.setLName(a.getLName())
					.setMName(a.getMName())
					.setRelation(a.getRelation())
					.setModifiedBy(userName);
				}
			}
		}
		
		for (CandidateSiblingDto a : candidateDto.getSiblingsIdSet()) {
			if (CommonUtil.isEmpty(a.getUidStr()))
				candidate.getSiblings().add(modelMapper.map(a, CandidateSibling.class).setCreatedBy(userName)
						.setModifiedBy(userName).setCandidate(candidate));
		}
	}
	
	
	private void populateNotification(CandidateDto candidateDto, Candidate candidate, String userName) {
		for(CandidateNotification s: candidate.getNotifications()) {
			for(CandidateNotificationDto a: candidateDto.getNotificationsIdSet()) {
				if(s.getUid().toString().equals(a.getUidStr())) {
					s.setNotificationMode(a.getNotificationMode())
					.setValue(a.getValue())
					.setStatus(a.getStatus())
					.setModifiedBy(userName);
					break;
				}
			}
		}
		
		for (CandidateNotificationDto a : candidateDto.getNotificationsIdSet()) {
			if (CommonUtil.isEmpty(a.getUidStr()))
				candidate.getNotifications().add(modelMapper.map(a, CandidateNotification.class).setCreatedBy(userName)
						.setModifiedBy(userName).setCandidate(candidate));
		}
	}
	
	private void populateGuardian(CandidateDto candidateDto, Candidate candidate, String userName) {
		for(CandidateGuardian s: candidate.getGuardians()) {
			for(CandidateGuardianDto a: candidateDto.getGuardiansIdSet()) {
				if(s.getUid().toString().equals(a.getUidStr())) {
					s.setAadharId(a.getAadharId())
					.setAddress1(a.getAddress1())
					.setAddress2(a.getAddress2())
					.setAge(a.getAge())
					.setCity(a.getCity())
					.setCountry(a.getCountry())
					.setDesignation(a.getDesignation())
					.setDob(a.getDob())
					.setEmail(a.getEmail())
					.setFName(a.getFName())
					.setGender(a.getGender())
					.setIncome(a.getIncome())
					.setIsGovtEmp(a.getIsGovtEmp())
					.setIsPrimary(a.getIsPrimary())
					.setLName(a.getLName())
					.setMName(a.getMName())
					.setNationality(a.getNationality())
					.setOfficeAddress(a.getOfficeAddress())
					.setOrganization(a.getOrganization())
					.setPanId(a.getPanId())
					.setPhone1(a.getPhone1())
					.setPhone2(a.getPhone2())
					.setProfileImage(a.getProfileImage())
					.setQualification(a.getQualification())
					.setRelation(a.getRelation())
					.setState(a.getState())
					.setMaritalStatus(a.getMaritalStatus())
					.setModifiedBy(userName);
					break;
				}
			}
		}
		
		for (CandidateGuardianDto a : candidateDto.getGuardiansIdSet()) {
			if (CommonUtil.isEmpty(a.getUidStr()))
				candidate.getGuardians().add(modelMapper.map(a, CandidateGuardian.class).setCreatedBy(userName)
						.setModifiedBy(userName).setCandidate(candidate));
		}
	}
	
	
	@Override
	public List<ErrorObject> candidateToStudent(List<String> candidateIds, String userName) {
		List<ErrorObject> errCandidateList = new ArrayList<>();
		List<Student> studentsToSave = new ArrayList<>();
		List<Candidate> cadidateToUpdate = new ArrayList<>();
		for (String id : candidateIds) {
			Optional<Candidate> candidate = Optional.ofNullable(candidateRepository.findByStudentId(id));
			if (candidate.isPresent()) {
				if (StudentStatusEnum.PAID.name().equals(candidate.get().getStatus())
						&& StudentStatusEnum.PASS.name().equals(candidate.get().getExamStatus())) {
					modelMapper.getConfiguration().setAmbiguityIgnored(true);
					Student student = new Student();
					CandidateMapper.populateCandidateToStudent(candidate.get(), student, userName, modelMapper);
					if(!CommonUtil.isEmpty(candidate.get().getUser())) {
						User user = userRepository.findByUsername(candidate.get().getUser().getUsername());
						student.setUser(user);	
					}
					studentsToSave.add(student);
					candidate.get().setStatus(StudentStatusEnum.MOVED.name()).setModifiedBy(userName);
					cadidateToUpdate.add(candidate.get());
				} else {
					errCandidateList.add(new ErrorObject(id, true, "Status is not valid"));
				}
				
				
			} else {
				errCandidateList.add(new ErrorObject(id, true, "Candidate not found"));
			}
		}

		List<Student> studentsSaved = null;
		if (!CommonUtil.isEmpty(studentsToSave)) {
			studentsSaved = studentRepository.saveAll(studentsToSave);
		}
		
		if (!CommonUtil.isEmpty(cadidateToUpdate)) {
			candidateRepository.saveAll(cadidateToUpdate);
		}
		
		

		if (!CommonUtil.isEmpty(studentsSaved)) {
			Set<String> ids = studentsSaved.stream().map(Student::getStudentId).collect(Collectors.toSet());
			List<Student> notSaved = studentsToSave.stream().filter(stud -> !ids.contains(stud.getStudentId()))
					.collect(Collectors.toList());
			List<ErrorObject> info = notSaved.stream()
					.map(v -> new ErrorObject(v.getStudentId(), true, "Unable to save")).collect(Collectors.toList());
			errCandidateList.addAll(info);
		}
		

		return errCandidateList;
	}
	
	@Override
	public void updateProfilePicture(String imgUrl, String studentId, String userName) {
		Optional<Candidate> candidate = Optional.ofNullable(candidateRepository.findByStudentId(studentId));
		if (candidate.isPresent()) {
			candidate.get().setProfileImage(imgUrl).setModifiedBy(userName);
		}
		throw exception(CANDIDATE, ENTITY_NOT_FOUND, studentId);
	}
	
	
	 /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return SMSException.throwException(entityType, exceptionType, args);
    }
    
}
