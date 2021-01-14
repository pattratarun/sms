package com.pat.sms.user.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pat.sms.exception.EntityType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Tarun Pattra.
 */
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	
	private EntityType entityType;
    private String username;
    private String password;
    private String status;
    private boolean isAdmin;
    private UserRoleDto role;
    private Set<PriviledgeDto> priviledges;
    
    private String roleId;
    private List<String> priviledgesIds;
    
    private String userLogUidStr;

}
