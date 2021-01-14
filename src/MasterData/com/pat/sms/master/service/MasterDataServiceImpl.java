package com.pat.sms.master.service;

import static com.pat.sms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.pat.sms.util.DropDownKeys.KEY_ID_GENERATION;
import static com.pat.sms.util.StringConstants.COLON;
import static com.pat.sms.util.StringConstants.COMMA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pat.sms.exception.EntityType;
import com.pat.sms.exception.ExceptionType;
import com.pat.sms.exception.SMSException;
import com.pat.sms.master.v1.reqresp.LabelValueBean;
import com.pat.sms.master.v1.reqresp.SearchField;
import com.pat.sms.model.common.ApplicationConstant;
import com.pat.sms.model.common.DropdownMaster;
import com.pat.sms.model.common.SearchFieldMaster;
import com.pat.sms.model.student.School;
import com.pat.sms.repository.master.ApplicationConstantRepository;
import com.pat.sms.repository.master.DropdownRepository;
import com.pat.sms.repository.master.SearchFieldMasterRepository;
import com.pat.sms.repository.student.SchoolRepository;

@Component
@Transactional
public class MasterDataServiceImpl implements MasterDataService {
	
	@Autowired
    CacheManager cacheManager;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ApplicationConstantRepository constantRepository;
	
	@Autowired
	DropdownRepository dropdownRepository;	
	
	@Autowired
	SearchFieldMasterRepository searchFieldMasterRepository;
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	/*@Autowired
	private ModelMapper modelMapper;*/

	@Override
	@Cacheable(value="appConstants") 
	public Map<String, String> getAllConstants() {
		Map<String, String> constantsMap = new HashMap<>();
		Optional<Iterable<ApplicationConstant>> candidate = Optional.ofNullable(constantRepository.findAll());
		if (candidate.isPresent()) {
			constantsMap = StreamSupport.stream(candidate.get().spliterator(), false)
					.filter(c -> c.getEnv().equalsIgnoreCase(environment.getActiveProfiles()[0])).collect(Collectors
							.toMap(ApplicationConstant::getConstantKey, ApplicationConstant::getConstantValue));
		}

		return constantsMap;
	}
	
	@Override
	@Cacheable(value="dropdowns") 
	public Map<String, List<String>> getDropdown() {
		Map<String, List<String>> dropdownMap = new HashMap<>();
		Optional<Iterable<DropdownMaster>> dropdownList	 = Optional.ofNullable(dropdownRepository.findAll());
		if(dropdownList.isPresent()) {
			for(DropdownMaster d:dropdownList.get()) {
				if(d.getEnv().equalsIgnoreCase(environment.getActiveProfiles()[0])) {
					dropdownMap.put(d.getDropdownKey(), Arrays.asList(d.getDropdownValue().split(COMMA)));	
				}
			}
			return dropdownMap;
		}
		 throw exception(EntityType.CONSTANT, ENTITY_NOT_FOUND, "application data");
	}
	
	
	@Override
	@Cacheable(value="entityID", key="#user")
	public String generateID(String user) {
		String[] idAry = getAllConstants().get(KEY_ID_GENERATION).split(COMMA);
		 Optional<String> id =   Arrays.stream(idAry).filter(c -> c.contains(user)).findAny();
		 if(id.isPresent()) {
			 return id.get().split(COLON)[1];
		 }
		 return null;
	}
	
	@Override
	public List<LabelValueBean> getSchools() {
		Optional<Iterable<School>> school = Optional.ofNullable(schoolRepository.findAll());
		List<LabelValueBean> schooList = new ArrayList<>();
		if (school.isPresent()) {
			schooList = StreamSupport.stream(school.get().spliterator(), false)
					.map(c -> new LabelValueBean(c.getName(),c.getUid().toString()))
					.collect(Collectors.toList());
		}

		return schooList;
	}
	
	
	@Override
	public void resetAllCaches() {
        cacheManager.getCacheNames().stream()
          .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
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
	
    
    @Override
	public List<SearchField> getSearchFeilds(String searchName) throws JsonMappingException, JsonProcessingException {
		SearchFieldMaster search = searchFieldMasterRepository.findBySearchScreen(searchName);
		
        ObjectMapper objectMapper = new ObjectMapper();
        List<SearchField> searchList = objectMapper.readValue(search.getSearchfields(), new TypeReference<List<SearchField>>() {});
        
		return searchList;		
	}
}
