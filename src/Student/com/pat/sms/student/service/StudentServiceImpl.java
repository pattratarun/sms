package com.pat.sms.student.service;

import static com.pat.sms.exception.EntityType.STUDENT;
import static com.pat.sms.exception.ExceptionType.ENTITY_EXCEPTION;
import static com.pat.sms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.pat.sms.util.DropDownKeys.KEY_DEFAULT_PASS;
import static com.pat.sms.util.StringConstants.DEFAULT_PROP;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.pat.sms.exception.EntityType;
import com.pat.sms.exception.ExceptionType;
import com.pat.sms.exception.SMSException;
import com.pat.sms.master.service.MasterDataService;
import com.pat.sms.model.student.EducationHistory;
import com.pat.sms.model.student.Guardian;
import com.pat.sms.model.student.School;
import com.pat.sms.model.student.Student;
import com.pat.sms.model.student.StudentDocument;
import com.pat.sms.model.student.StudentInfo;
import com.pat.sms.model.student.StudentNotification;
import com.pat.sms.model.student.StudentSibling;
import com.pat.sms.repository.student.SchoolRepository;
import com.pat.sms.repository.student.StudentRepository;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.search.SearchSetting;
import com.pat.sms.search.SearchUtil;
import com.pat.sms.student.dto.CandidateDocumentDto;
import com.pat.sms.student.dto.CandidateEducationHistoryDto;
import com.pat.sms.student.dto.CandidateGuardianDto;
import com.pat.sms.student.dto.CandidateInfoDto;
import com.pat.sms.student.dto.CandidateNotificationDto;
import com.pat.sms.student.dto.CandidateSiblingDto;
import com.pat.sms.student.dto.StudentDto;
import com.pat.sms.student.dto.mapper.StudentMapper;
import com.pat.sms.student.v1.reqresp.StudentSearchResult;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.service.UserService;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra.
 */
@Component
@Transactional
public class StudentServiceImpl implements StudentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	UserService userService;

	@Autowired
	MasterDataService masterconstant;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	SchoolRepository schoolrepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StudentDto getStudentById(String studentID) {
		Optional<Student> student = Optional.ofNullable(studentRepository.findByStudentId(studentID));
		if (student.isPresent()) {
			return StudentMapper.toStudentDto(student.get());
		}
		throw exception(STUDENT, ENTITY_NOT_FOUND, studentID);
	}


	@Override
	public StudentDto addNewStudent(StudentDto student, String userName, boolean createUser) {
		try {
			Student newStudent = modelMapper.map(student, Student.class);
			StudentDto newStu = null;

			newStudent.setCreatedBy(userName).setModifiedBy(userName)
					.setStudentId(student.getStudentId())
					.setEmail(student.getEmail())
					.setAdmissionNum(student.getAdmissionNum())
					.setHistory(populateHistory(student.getHistoryIdSet(), userName, newStudent))
					.setStudentInfos(populateInfos(student.getInfosIdSet(), userName, newStudent))
					.setDocuments(populateDocs(student.getDocumentsIdSet(), userName, newStudent))
					.setSiblings(populateSibling(student.getSiblingsIdSet(), userName, newStudent))
					.setNotifications(populateNotification(student.getNotificationsIdSet(), userName, newStudent))
					.setGuardians(populateGuardian(student.getGuardiansIdSet(), userName, newStudent));

			if (!CommonUtil.isEmpty(student.getSchoolIdStr())) {
				Optional<School> school = schoolrepository.findById(UUID.fromString(student.getSchoolIdStr()));
				if (school.isPresent()) {
					newStudent.setSchool(school.get());
				}
			}

			if (createUser) {
				UserDto newUser = new UserDto().setUsername(newStudent.getEmail())
						.setPassword(masterconstant.getAllConstants().get(KEY_DEFAULT_PASS)).setAdmin(false)
						.setEntityType(EntityType.STUDENT).setStatus("active");
				newStudent.setUser(userService.createUser(newUser, userName));
			}
			
			studentRepository.save(newStudent);
			newStu = StudentMapper.toStudentDto(newStudent);
			return newStu;
		} catch (Exception e) {
			throw exception(STUDENT, ExceptionType.ENTITY_EXCEPTION, student.getFName());
		}
	}

	private Set<Guardian> populateGuardian(Set<CandidateGuardianDto> guardians, String userName, Student newStudent) {
		Set<Guardian> guardianSet = new HashSet<>();
		for (CandidateGuardianDto h : guardians) {
			guardianSet.add(modelMapper.map(h, Guardian.class)
					.setUid(CommonUtil.strToUUID(h.getUidStr()))
					.setCreatedBy(userName).setModifiedBy(userName)
					.setStudent(newStudent)
					.setFName(CommonUtil.stringToCamel(h.getFName()))
					.setMName(CommonUtil.stringToCamel(h.getMName()))
					.setLName(CommonUtil.stringToCamel(h.getLName())));
		}
		return guardianSet;
	}

	private Set<EducationHistory> populateHistory(Set<CandidateEducationHistoryDto> histories, String userName, Student newStudent) {
		Set<EducationHistory> historySet = new HashSet<>();
		for (CandidateEducationHistoryDto h : histories) {
			historySet.add(modelMapper.map(h, EducationHistory.class)
					.setUid(CommonUtil.strToUUID(h.getUidStr()))
					.setCreatedBy(userName).setModifiedBy(userName)
					.setStudent(newStudent));
		}
		return historySet;
	}

	private Set<StudentInfo> populateInfos(Set<CandidateInfoDto> infos, String userName, Student newStudent) {
		Set<StudentInfo> infoSet = new HashSet<>();
		for (CandidateInfoDto h : infos) {
			infoSet.add(modelMapper.map(h, StudentInfo.class)
					.setUid(CommonUtil.strToUUID(h.getUidStr()))
					.setCreatedBy(userName).setModifiedBy(userName)
					.setStudent(newStudent));
		}
		return infoSet;
	}

	private Set<StudentDocument> populateDocs(Set<CandidateDocumentDto> docs, String userName, Student newStudent) {
		Set<StudentDocument> docSet = new HashSet<>();
		for (CandidateDocumentDto h : docs) {
			docSet.add(modelMapper.map(h, StudentDocument.class)
					.setUid(CommonUtil.strToUUID(h.getUidStr()))
					.setCreatedBy(userName).setModifiedBy(userName)
					.setStudent(newStudent));
		}
		return docSet;
	}

	private Set<StudentSibling> populateSibling(Set<CandidateSiblingDto> sibls, String userName, Student newStudent) {
		Set<StudentSibling> siblSet = new HashSet<>();
		for (CandidateSiblingDto h : sibls) {
			siblSet.add(modelMapper.map(h, StudentSibling.class)
					.setUid(CommonUtil.strToUUID(h.getUidStr()))
					.setCreatedBy(userName).setModifiedBy(userName)
					.setStudent(newStudent)
					.setFName(CommonUtil.stringToCamel(h.getFName()))
					.setMName(CommonUtil.stringToCamel(h.getMName()))
					.setLName(CommonUtil.stringToCamel(h.getLName())));
		}
		return siblSet;
	}

	private Set<StudentNotification> populateNotification(Set<CandidateNotificationDto> nofis, String userName,
			Student newStudent) {
		Set<StudentNotification> notifSet = new HashSet<>();
		for (CandidateNotificationDto h : nofis) {
			notifSet.add(modelMapper.map(h, StudentNotification.class)
					.setUid(CommonUtil.strToUUID(h.getUidStr()))
					.setCreatedBy(userName).setModifiedBy(userName).setStudent(newStudent));
		}
		return notifSet;
	}
	
	
	@Override
	public Map<String, Object> getStudentListWithPagination(String searchText, int page, int size, int orp,
			List<Order> orders) {

		Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

		Page<Student> pageTuts;
		if (searchText == null) {
			pageTuts = studentRepository.findAll(pagingSort);
		}
		else if (orp==1) {
			pageTuts = studentRepository.findByStudentIdIgnoreCase(searchText, pagingSort);
		}else {
			searchText = SearchSetting.getSearchStringFrom(searchText, orp);
			pageTuts = studentRepository.findByStudentIdLikeIgnoreCase(searchText, pagingSort);
		}

		List<StudentSearchResult> students = pageTuts.getContent().stream()
                .map(p -> modelMapper.map(p, StudentSearchResult.class))
                .collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("students", students);
		response.put("currentPage", pageTuts.getNumber());
		response.put("totalItems", pageTuts.getTotalElements());
		response.put("totalPages", pageTuts.getTotalPages());

		return response;

	}
	
	@Override
	public SearchResDto searchStu(SearchReqDto reqDto) {
		PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
				Sort.by(SearchUtil.getOrders(reqDto.getSorts(), DEFAULT_PROP)));
		Page<Student> page = studentRepository.findAll(SearchUtil.createSpec(reqDto.getQuery()), pageRequest);
		Function<Student, StudentSearchResult> mapper = (s) -> SearchUtil.createCopyObject(s, StudentSearchResult::new);
		return SearchUtil.prepareResponseForSearch(page, mapper);
	}
	
	
	@Override
	public StudentDto updateExistingStudent(StudentDto studentD, String userName) {

		try {

			Optional<Student> existingStudent = studentRepository.findById(UUID.fromString(studentD.getUidStr()));

			if (existingStudent.isPresent()) {
				populateStudent(studentD, existingStudent.get(), userName);
				studentRepository.save(existingStudent.get());
			}

			return StudentMapper.toStudentDto(existingStudent.get());
		} catch (Exception e) {
			LOGGER.error("updateExistingStudent:",e);
			throw exception(STUDENT, ENTITY_EXCEPTION, studentD.getFName());
		}
	}

private void populateStudent(StudentDto studentDto, Student student, String userName) {
		
	student.setModifiedBy(userName)
		.setAdditionalId1(studentDto.getAdditionalId1())
		.setAdditionalId2(studentDto.getAdditionalId2())
		.setAddress1(studentDto.getAddress1())
		.setAddress2(studentDto.getAddress2())
		.setAdmissionCat1(studentDto.getAdmissionCat1())
		.setAdmissionCat2(studentDto.getAdmissionCat2())
		.setAdmissionCat3(studentDto.getAdmissionCat3())
		.setAdmissionNum(studentDto.getAdmissionNum())
		.setAdmissionRemarks(studentDto.getAdmissionNum())
		.setAge(studentDto.getAge())
		.setBloodGroup(studentDto.getBloodGroup())
		.setCity(studentDto.getCity())
		.setCountry(studentDto.getCountry())
		.setDob(studentDto.getDob())
		.setEmail(studentDto.getEmail())
		.setEndDay(studentDto.getEndDay())
		.setFName(studentDto.getFName())
		.setGender(studentDto.getGender())
		.setIsHostller(studentDto.getIsHostller())
		.setLName(studentDto.getLName())
		.setMName(studentDto.getMName())
		.setNationality(studentDto.getNationality())
		.setOnlyChild(studentDto.getOnlyChild())
		.setPhone(studentDto.getPhone())
		.setProfileImage(studentDto.getProfileImage())
		.setRfId(studentDto.getRfId())
		.setSiblingId(studentDto.getSiblingId())
		.setStartDay(studentDto.getStartDay())
		.setState(studentDto.getState())
		.setStatus(studentDto.getStatus())
		.setStudentId(studentDto.getStudentId());
		
		
		populateHistory(studentDto,student, userName);
		populateInfos(studentDto,student, userName);
		populateDocs(studentDto,student, userName);
		populateSibling(studentDto,student, userName);
		populateNotification(studentDto,student, userName);
		populateGuardian(studentDto,student, userName);
		
	}

private void populateHistory(StudentDto studentDto, Student student, String userName) {
	for(EducationHistory s: student.getHistory()) {
		for(CandidateEducationHistoryDto a: studentDto.getHistoryIdSet()) {
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
	
	for(CandidateEducationHistoryDto a: studentDto.getHistoryIdSet()) {
		if(CommonUtil.isEmpty(a.getUidStr())) {
			student.getHistory().add(modelMapper.map(a, EducationHistory.class)
					.setCreatedBy(userName).setModifiedBy(userName).setStudent(student));	
		}
	}
}


private void populateInfos(StudentDto studentDto, Student student, String userName) {
	for(StudentInfo s: student.getStudentInfos()) {
		for(CandidateInfoDto a: studentDto.getInfosIdSet()) {
			if(s.getUid().toString().equals(a.getUidStr())) {
				s.setInfoType(a.getInfoType())
				.setInfoValue1(a.getInfoValue1())
				.setInfoValue2(a.getInfoValue2())
				.setModifiedBy(userName);
				break;
			}
		}
	}
	
	for (CandidateInfoDto a : studentDto.getInfosIdSet()) {
		if (CommonUtil.isEmpty(a.getUidStr()))
			student.getStudentInfos().add(modelMapper.map(a, StudentInfo.class).setCreatedBy(userName)
					.setModifiedBy(userName).setStudent(student));
	}
}

private void populateDocs(StudentDto studentDto, Student student, String userName) {
	for(StudentDocument s: student.getDocuments()) {
		for(CandidateDocumentDto a: studentDto.getDocumentsIdSet()) {
			if(s.getUid().toString().equals(a.getUidStr())) {
				s.setDocumentData(a.getDocumentData())
				.setDocumentType(a.getDocumentType())
				.setModifiedBy(userName);
				break;
			}
		}
	}
	
	for (CandidateDocumentDto a : studentDto.getDocumentsIdSet()) {
		if (CommonUtil.isEmpty(a.getUidStr()))
			student.getDocuments().add(modelMapper.map(a, StudentDocument.class).setCreatedBy(userName)
					.setModifiedBy(userName).setStudent(student));
	}
}

private void populateSibling(StudentDto studentDto, Student student, String userName) {
	for(StudentSibling s: student.getSiblings()) {
		for(CandidateSiblingDto a: studentDto.getSiblingsIdSet()) {
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
	
	for (CandidateSiblingDto a : studentDto.getSiblingsIdSet()) {
		if (CommonUtil.isEmpty(a.getUidStr()))
			student.getSiblings().add(modelMapper.map(a, StudentSibling.class).setCreatedBy(userName)
					.setModifiedBy(userName).setStudent(student));
	}
}


private void populateNotification(StudentDto studentDto, Student student, String userName) {
	for(StudentNotification s: student.getNotifications()) {
		for(CandidateNotificationDto a: studentDto.getNotificationsIdSet()) {
			if(s.getUid().toString().equals(a.getUidStr())) {
				s.setNotificationMode(a.getNotificationMode())
				.setValue(a.getValue())
				.setStatus(a.getStatus())
				.setModifiedBy(userName);
				break;
			}
		}
	}
	
	for (CandidateNotificationDto a : studentDto.getNotificationsIdSet()) {
		if (CommonUtil.isEmpty(a.getUidStr()))
			student.getNotifications().add(modelMapper.map(a, StudentNotification.class).setCreatedBy(userName)
					.setModifiedBy(userName).setStudent(student));
	}
}

private void populateGuardian(StudentDto studentDto, Student student, String userName) {
	for(Guardian s: student.getGuardians()) {
		for(CandidateGuardianDto a: studentDto.getGuardiansIdSet()) {
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
	
	for (CandidateGuardianDto a : studentDto.getGuardiansIdSet()) {
		if (CommonUtil.isEmpty(a.getUidStr()))
			student.getGuardians().add(modelMapper.map(a, Guardian.class).setCreatedBy(userName)
					.setModifiedBy(userName).setStudent(student));
	}
}

	@Override
	public void updateProfilePicture(String imgUrl, String studentId, String userName) {
		Optional<Student> student = Optional.ofNullable(studentRepository.findByStudentId(studentId));
		if (student.isPresent()) {
			student.get().setProfileImage(imgUrl).setModifiedBy(userName);
		}
		throw exception(STUDENT, ENTITY_NOT_FOUND, studentId);
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
