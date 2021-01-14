package com.pat.sms.admin.service;

import static com.pat.sms.exception.EntityType.SCHOOL;
import static com.pat.sms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.pat.sms.util.StringConstants.DEFAULT_PROP;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.pat.sms.admin.v1.reqresp.SchoolSearchResult;
import com.pat.sms.exception.EntityType;
import com.pat.sms.exception.ExceptionType;
import com.pat.sms.exception.SMSException;
import com.pat.sms.model.student.School;
import com.pat.sms.repository.student.SchoolRepository;
import com.pat.sms.search.SearchReqDto;
import com.pat.sms.search.SearchResDto;
import com.pat.sms.search.SearchUtil;
import com.pat.sms.student.dto.SchoolDto;

@Component
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	SchoolRepository schoolRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public SchoolDto getSchoolDetails(String schooluid) {
		Optional<School> school =  schoolRepository.findById(UUID.fromString(schooluid));
		if(school.isPresent()) {
			return modelMapper.map(school.get(), SchoolDto.class).setUidStr(school.get().getUid().toString()) ;
		}
		throw exception(SCHOOL, ENTITY_NOT_FOUND, schooluid);	
	}
	
	@Override
	public SearchResDto searchSchool(SearchReqDto reqDto) {
		PageRequest pageRequest = PageRequest.of(reqDto.getPageIndex(), reqDto.getPageSize(),
				Sort.by(SearchUtil.getOrders(reqDto.getSorts(), DEFAULT_PROP)));
		Page<School> page = schoolRepository.findAll(SearchUtil.createSpec(reqDto.getQuery()), pageRequest);
		Function<School, SchoolSearchResult> mapper = (s) -> SearchUtil.createCopyObject(s, SchoolSearchResult::new);
		return SearchUtil.prepareResponseForSearch(page, mapper);
	}
	
	@Override
	public SchoolDto addNewSchool(SchoolDto school, String userName) {
		
		School newSchool = new School()
				.setAddress(school.getAddress())
				.setCreatedBy(userName)
				.setModifiedBy(userName)
				.setName(school.getName())
				.setStatus(school.getStatus());
		
		schoolRepository.save(newSchool);
		SchoolDto schoolD = modelMapper.map(newSchool, SchoolDto.class).setUidStr(newSchool.getUid().toString());
		
		return schoolD;
	}
	
	@Override
	public SchoolDto updateSchool(SchoolDto schoolD, String userName) {
		
		Optional<School> school =  schoolRepository.findById(UUID.fromString(schoolD.getUidStr()));
		if(school.isPresent()) {
					school.get().setAddress(schoolD.getAddress())
					.setCreatedBy(userName)
					.setModifiedBy(userName)
					.setName(schoolD.getName())
					.setStatus(schoolD.getStatus());
			
			schoolRepository.save(school.get());
			
			return modelMapper.map(school, SchoolDto.class).setUidStr(school.toString());
		}
		throw exception(SCHOOL, ENTITY_NOT_FOUND, schoolD.getUidStr());	
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
