package com.pat.sms.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pat.sms.user.dto.PriviledgeDto;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.dto.UserRoleDto;
import com.pat.sms.user.service.UserService;
import com.pat.sms.util.CommonUtil;

/**
 * Created by Tarun Pattra.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserDto userDto = userService.findUserByUsername(userName);
        if (userDto != null) {
        	UserRoleDto roleDto  = userDto.getRole();
        	
        	Set<PriviledgeDto> priviledgeSet = new HashSet<>();
        	if(!CommonUtil.isEmpty(userDto.getPriviledges())) {
        		priviledgeSet.addAll(userDto.getPriviledges());
        	}
        	if(!CommonUtil.isEmpty(roleDto.getPriviledges())) {
        		priviledgeSet.addAll(roleDto.getPriviledges());
        	}
        	
            List<GrantedAuthority> authorities = getUserAuthority(priviledgeSet);
            
            return buildUserForAuthentication(userDto, authorities);
        } else {
            throw new UsernameNotFoundException("username " + userName + " does not exist.");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Collection<PriviledgeDto> userPriviledges) {
        Set<GrantedAuthority> roles = new HashSet<>();
        if(!CommonUtil.isEmpty(userPriviledges)) {
        	userPriviledges.forEach(role -> 
                roles.add(new SimpleGrantedAuthority(String.valueOf(role.getPriviledgeId())))
            );
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(UserDto user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
