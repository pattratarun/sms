package com.pat.sms.csession.service;

import static com.pat.sms.enums.ExamStatusEnum.NONE;
import static com.pat.sms.exception.EntityType.CLASSs;
import static com.pat.sms.exception.EntityType.STUDENTSECTION;
import static com.pat.sms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.pat.sms.util.StringConstants.COMMA;
import static com.pat.sms.util.StringConstants.DEFAULT_PROP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pat.sms.csession.dto.ClassSessionDto;
import com.pat.sms.csession.dto.ClassSessionStudentDto;
import com.pat.sms.csession.v1.reqresp.ClassSessionSearchResult;
import com.pat.sms.csession.v1.reqresp.ClassSessionStudentSearchResult;
import com.pat.sms.exception.EntityType;
import com.pat.sms.exception.ExceptionType;
import com.pat.sms.exception.SMSException;
import com.pat.sms.model.clsession.ClassSession;
import com.pat.sms.model.clsession.ClassSessionStudent;
import com.pat.sms.model.student.Student;
import com.pat.sms.repository.csession.ClassSessionRepository;
import com.pat.sms.repository.csession.ClassSessionStudentRepository;
import com.pat.sms.repository.student.StudentRepository;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.search.SearchUtil;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra.
 */

@Component
@Transactional
public class ClassSessionServiceImpl implements ClassSessionService {

	//private static final Logger LOGGER = LoggerFactory.getLogger(ClassSessionServiceImpl.class);
	
	@Autowired
	ClassSessionRepository classRepository;
	
	@Autowired
	ClassSessionStudentRepository clsStudentRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<List<ClassSessionDto>> createNewClass(ClassSessionDto classSDto, String userName) {
		
		String[] sectionsAry  = classSDto.getSections().split(COMMA);
		String[] classAry  = classSDto.getClasses().split(COMMA);
		

		List<List<ClassSession>> classMetric = new ArrayList<>();
		for(String cls: classAry) {
			List<ClassSession> clsList = new ArrayList<>();
			for(String sec: sectionsAry) {
				clsList.add(getClassSessionObj(classSDto, userName, sec, cls));
			}
			classMetric.add(clsList);
		}
		
		List<List<ClassSessionDto>> classDtoMetric = new ArrayList<>();
		for(List<ClassSession> s: classMetric) {
			if(!CommonUtil.isEmpty(s)) {
				classRepository.saveAll(s);
			}
			classDtoMetric.add(toClsSessionDto(s));
		}
		return classDtoMetric;
	}

	private ClassSession getClassSessionObj(ClassSessionDto classSDto, String userName, String section,String cls) {
		return new ClassSession()
				.setClassId(classSDto.getSessionYear()+cls+section)
				.setCreatedBy(userName)
				.setModifiedBy(userName)
				.setRemarks(classSDto.getRemarks())
				.setSection(section)
				.setSessionClass(cls)
				.setSessionYear(classSDto.getSessionYear());
		
	}
	
	private List<ClassSessionDto> toClsSessionDto(List<ClassSession> clsList) {
		return clsList.stream().map(p -> modelMapper.map(p, ClassSessionDto.class))
												.collect(Collectors.toList());
        
	}
	
	
	@Override
	public List<ClassSessionStudentDto> addStudentToClass(String classId, List<String> studentList, String userName){
		
		ClassSession  classS = classRepository.findByclassId(classId);
		Set<ClassSessionStudent> classSess = new HashSet<>();
		long rol =0;
		if(!CommonUtil.isEmpty(classS)) {
			for(String s: studentList) {
				Student stud = studentRepository.findByStudentId(s);
				if(!CommonUtil.isEmpty(stud)) {
					classSess.add(getClassSessionStudentObj(classS, stud, userName, ++rol));
				}
			}
			
			if(!CommonUtil.isEmpty(classSess)) {
				clsStudentRepository.saveAll(classSess);
			}
			
			classS.setClassStudents(classSess);
			
			return toClsSessStudentDto(classSess);
		}
		throw exception(CLASSs, ENTITY_NOT_FOUND, classId);
	}
	
	private ClassSessionStudent getClassSessionStudentObj(ClassSession  classS, Student stud, String userName, long rol) {
		return new ClassSessionStudent().setClassSession(classS)
				.setStudent(stud)
				.setClassId(classS.getClassId())
				.setStudentId(stud.getStudentId())
				.setCreatedBy(userName)
				.setModifiedBy(userName)
				.setExamStatus(NONE.name())
				.setMonitor(false)
				.setRollNumber(rol)
				.setSessionClass(classS.getSessionClass())
				.setRemarks("");
				
	}
	
	
	private List<ClassSessionStudentDto> toClsSessStudentDto(Set<ClassSessionStudent> clsList) {
		return clsList.stream().map(p -> modelMapper.map(p, ClassSessionStudentDto.class))
												.collect(Collectors.toList());
        
	} 
	
	
	@Override
	public void deleteStudentFromClass(String classId, List<String> studentList){
		
		ClassSession  classS = classRepository.findByclassId(classId);
		Set<ClassSessionStudent> classSessToDel = new HashSet<>();
		if(!CommonUtil.isEmpty(classS)) {
			
			if(!CommonUtil.isEmpty(classS.getClassStudents())) {
				
				for(ClassSessionStudent x: classS.getClassStudents()) {
					for(String s: studentList) {
						if(x.getStudentId().equals(s)) {
							classSessToDel.add(x);
							break;
						}
					}
				}
			}
			
			if(!CommonUtil.isEmpty(classSessToDel)) {
				clsStudentRepository.deleteAll(classSessToDel);
			}
			
		}
		throw exception(CLASSs, ENTITY_NOT_FOUND, classId);
	}
	
	
	@Override
	public ClassSessionStudentDto changeStudentClassSection(String studentId, String oldClassId, String newClassId, String userName) {
		
		ClassSessionStudent studCls = clsStudentRepository.findByClassIdAndStudentId(oldClassId, studentId);
		
		if(studCls !=null) {
			ClassSession  nclassS = classRepository.findByclassId(newClassId);
			if(nclassS !=null) {
				studCls.setClassSession(nclassS).setModifiedBy(userName).setClassId(nclassS.getClassId());
				clsStudentRepository.save(studCls);
				return modelMapper.map(studCls, ClassSessionStudentDto.class);
			}
			throw exception(CLASSs, ENTITY_NOT_FOUND, newClassId);
		}
		throw exception(STUDENTSECTION, ENTITY_NOT_FOUND, studentId);
	}
	
	
	@Override
	public ClassSessionStudentDto editStudentToClass(ClassSessionStudentDto classStudentDto, String userName){
		
		Optional<ClassSessionStudent>  classS = clsStudentRepository.findById(UUID.fromString(classStudentDto.getUidStr()));
		if(classS.isPresent()) {
			classS.get()
			.setModifiedBy(userName)
			.setMonitor(classStudentDto.getMonitor())
			.setStatus(classStudentDto.getStatus())
			.setRemarks(classStudentDto.getRemarks())
			.setExamStatus(classStudentDto.getExamStatus())
			.setRollNumber(classStudentDto.getRollNumber());
		}
		throw exception(STUDENTSECTION, ENTITY_NOT_FOUND, classStudentDto.getStudentId());
	}
	
	
	@Override
	public List<ClassSessionStudentDto> getAllStudentsFromAClass(String classSessionId){
		
		Optional<Set<ClassSessionStudent>>  classS = Optional.ofNullable(clsStudentRepository.findByClassId(classSessionId));
		if(classS.isPresent()) {
			return toClsSessStudentDto(classS.get());
		}
		throw exception(STUDENTSECTION, ENTITY_NOT_FOUND, classSessionId);
	}
	
	
	
	@Override
	public SearchResDto classSessionSearch(SearchReqDto reqDto) {
		PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
				Sort.by(SearchUtil.getOrders(reqDto.getSorts(), DEFAULT_PROP)));
		Page<ClassSession> page = classRepository.findAll(SearchUtil.createSpec(reqDto.getQuery()), pageRequest);
		Function<ClassSession, ClassSessionSearchResult> mapper = (s) -> SearchUtil.createCopyObject(s, ClassSessionSearchResult::new);
		return SearchUtil.prepareResponseForSearch(page, mapper);
	}
	
	
	@Override
	public SearchResDto classSessionStudentSearch(SearchReqDto reqDto) {
		PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
				Sort.by(SearchUtil.getOrders(reqDto.getSorts(), DEFAULT_PROP)));
		Page<ClassSessionStudent> page = clsStudentRepository.findAll(SearchUtil.createSpec(reqDto.getQuery()), pageRequest);
		Function<ClassSessionStudent, ClassSessionStudentSearchResult> mapper = (s) -> SearchUtil.createCopyObject(s, ClassSessionStudentSearchResult::new);
		return SearchUtil.prepareResponseForSearch(page, mapper);
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
