package com.pat.sms.user.service;

import static com.pat.sms.exception.EntityType.PRIVILEDGE;
import static com.pat.sms.exception.EntityType.ROLE;
import static com.pat.sms.exception.EntityType.USER;
import static com.pat.sms.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.pat.sms.exception.ExceptionType.ENTITY_NOT_FOUND;
import static com.pat.sms.util.DropDownKeys.KEY_DEFAULT_PASS;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pat.sms.exception.EntityType;
import com.pat.sms.exception.ExceptionType;
import com.pat.sms.exception.SMSException;
import com.pat.sms.master.service.MasterDataService;
import com.pat.sms.model.user.PrivilegeMaster;
import com.pat.sms.model.user.User;
import com.pat.sms.model.user.UserLoginLogs;
import com.pat.sms.model.user.UserRole;
import com.pat.sms.repository.user.PriviledgeRepository;
import com.pat.sms.repository.user.UserLoginLogsRepository;
import com.pat.sms.repository.user.UserRepository;
import com.pat.sms.repository.user.UserRoleRepository;
import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.dto.UserRoleDto;
import com.pat.sms.user.dto.mapper.PriviledgeMapper;
import com.pat.sms.user.dto.mapper.UserMapper;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra
 */
@Component
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	MasterDataService masterconstant;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PriviledgeRepository priviledgeRepository;
    
    @Autowired
    UserLoginLogsRepository userlogRep;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Override
    public UserDto findUserByUsername(String userName) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userName));
        if (user.isPresent()) {
            return UserMapper.toUserDto(user.get());
        }
        throw exception(USER, ENTITY_NOT_FOUND, userName);
    }
    
    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Override
    public String createLoginLog(String userName) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userName));
        if (user.isPresent()) {
        	 UserLoginLogs userLog = new UserLoginLogs().setUser(user.get());
             userlogRep.save(userLog);
             return userLog.getUid().toString();
        }
        throw exception(USER, ENTITY_NOT_FOUND, userName);
    }
    
    
    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Override
    public void logoutLog(String logUid) {
    	
    	if(logUid == null) return;
    	
        Optional<UserLoginLogs> log = userlogRep.findById(UUID.fromString(logUid));
        if (log.isPresent()) {
        	log.get().setLogoutTime(new Date());
             userlogRep.save(log.get());
        } else {
        	throw exception(USER, ENTITY_NOT_FOUND, logUid);	
        }
    }
    
    /**
     * 
     */
    @Override
    public UserRoleDto findRoleById(String roleId) {
        Optional<UserRole> role = roleRepository.findById(UUID.fromString(roleId));
        if (role.isPresent()) {
            return modelMapper.map(role.get(), UserRoleDto.class);
        }
        throw exception(ROLE, ENTITY_NOT_FOUND, roleId);
    }
    
    @Override
	public Set<PriviledgeDto> findPriviledgesById(List<String> priviledgesIds) {

    	Set<PrivilegeMaster> privileges = getPrivilegesFromStingID(priviledgesIds);
		if (!privileges.isEmpty()) {
			return privileges.stream()
					.map(p -> PriviledgeMapper.toPriviledgeDto(p))
					.collect(Collectors.toSet());
		}
		return Collections.emptySet();
	}
    
    
    
    private Set<PrivilegeMaster> getPrivilegesFromStingID(List<String> priviledgesIds) {
    	List<PrivilegeMaster> priviledges =  priviledgeRepository.findAllByPriviledgeIdIn(priviledgesIds);
    	Set<PrivilegeMaster> priv = new HashSet<>();
    	priviledges.forEach(priv::add);
		return priv;
    }
    
	@Override
	public boolean deleteUserById(String username) {
		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isPresent()) {
        	userRepository.deleteById(user.get().getUid());
            return true;
        }
        throw exception(USER, ENTITY_NOT_FOUND, username);
	}


	@Override
	public UserDto modifyUserProfile(UserDto userDto, String userName) {
		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));
		if (user.isPresent()) {
			Optional<UserRole> role = roleRepository.findById(UUID.fromString(userDto.getRoleId()));
			Set<PrivilegeMaster> privileges = getPrivilegesFromStingID(userDto.getPriviledgesIds());

			User userModel = user.get();
			userModel.setStatus(userDto.getStatus())
					.setRole(role.get())
					.setPriviledges(privileges)
					.setModifiedBy(userName);
			userRepository.save(userModel);
			return UserMapper.toUserDto(userRepository.save(userModel));
		}
		throw exception(USER, ENTITY_NOT_FOUND, userDto.getUsername());
	}
    
	/**
     * Change Password
     *
     * @param userDto
     * @param newPassword
     * @return
     */
    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userDto.getUsername()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getUsername());
    }

    /**
     * 
     */
    @Override
	public UserDto signup(UserDto userDto, String userName) {
		return UserMapper.toUserDto(createUser(userDto, userName));
	}
    
    
    @Override
    public User createUser(UserDto userDto, String userName) {
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
        	Optional<UserRole> role = null;
        	if(userDto.getEntityType() ==null) {
        		role = roleRepository.findById(UUID.fromString(userDto.getRoleId()));
        	} else {
        		role = Optional.ofNullable(roleRepository.findByRoleName(userDto.getEntityType().toString()));
        	}
        	
        	Set<PrivilegeMaster> privileges =null;
        	if(!CommonUtil.isEmpty(userDto.getPriviledgesIds())) {
        		privileges = getPrivilegesFromStingID(userDto.getPriviledgesIds());	
        	}
        	
        	String defaultPassword =masterconstant.getAllConstants().get(KEY_DEFAULT_PASS);
            user = new User()
                    .setUsername(userDto.getUsername())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()==null? defaultPassword: userDto.getPassword()))
                    .setStatus(userDto.getStatus())
                    .setIsAdmin(userDto.isAdmin())
                    .setRole(role.get())
                    .setPriviledges(privileges)
                    .setCreatedBy(userName)
					.setModifiedBy(userName);
            return userRepository.save(user);
        }
        throw exception(USER, DUPLICATE_ENTITY, userDto.getUsername());
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


    /**
     * 
     */
	@Override
	public List<UserRoleDto> getAllRoles() {
		List<UserRole> roles =  roleRepository.findAll();
		List<UserRoleDto> userRoles = roles.stream().map(u -> modelMapper.map(u, UserRoleDto.class) )
				.collect(Collectors.toList());
		return userRoles;
	}


	/**
	 * 
	 */
	@Override
	public List<PriviledgeDto> getAllPriviledge() {
		List<PrivilegeMaster> priviledges =  priviledgeRepository.findAll();
		List<PriviledgeDto> userPriviledges = priviledges.stream().map(u -> modelMapper.map(u, PriviledgeDto.class) )
				.collect(Collectors.toList());
		return userPriviledges;
	}


	/**
	 * 
	 */
	@Override
	public UserRoleDto createUserRole(String roleName, String status, String userName, List<String> priviledges) {
		UserRole userRole = roleRepository.findByRoleName(roleName);
		if (userRole == null) {
			Set<PrivilegeMaster> privileges = getPrivilegesFromStingID(priviledges);
			UserRole newRole = new UserRole()
					.setRoleName(roleName)
					.setStatus(status)
					.setPriviledges(privileges)
					.setCreatedBy(userName)
					.setModifiedBy(userName);
			roleRepository.save(newRole);
			return modelMapper.map(newRole, UserRoleDto.class);
		}
		throw exception(ROLE, DUPLICATE_ENTITY, roleName);
	}

	/**
	 * 
	 */
	@Override
	public PriviledgeDto createPriviledge(String priviledgeName, String priviledgeId, String userName) {
		PrivilegeMaster priv = priviledgeRepository.findByPriviledgeId(priviledgeId);
		if (priv == null) {
			PrivilegeMaster newPriviledge = new PrivilegeMaster()
					.setName(priviledgeName)
					.setPriviledgeId(priviledgeId)
					.setStatus("active")
					.setCreatedBy(userName)
					.setModifiedBy(userName);
			priviledgeRepository.save(newPriviledge);
			return  modelMapper.map(newPriviledge, PriviledgeDto.class);
		}
		throw exception(PRIVILEDGE, DUPLICATE_ENTITY, priviledgeId);
	}
	
}
