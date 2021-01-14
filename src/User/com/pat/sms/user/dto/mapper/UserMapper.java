package com.pat.sms.user.dto.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.pat.sms.model.user.User;
import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.dto.UserRoleDto;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra.
 */
@Component
public class UserMapper {

	public static UserDto toUserDto(User user) {
		UserRoleDto role = null;
		if(user.getRole()!=null) {
			role = new ModelMapper().map(user.getRole(), UserRoleDto.class);	
		}
		
		Set<PriviledgeDto> privs=null;
		if(!CommonUtil.isEmpty(user.getPriviledges())) {
			privs= new HashSet<PriviledgeDto>(user.getPriviledges().stream()
					.map(priv -> new ModelMapper().map(priv, PriviledgeDto.class))
					.collect(Collectors.toSet()));
		}
		
		return new UserDto().setUsername(user.getUsername())
				.setPassword(user.getPassword())
				.setRole(role)
				.setPriviledges(privs);
	}

}
