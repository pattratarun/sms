package com.pat.sms.user.dto.mapper;

import org.springframework.stereotype.Component;

import com.pat.sms.model.user.PrivilegeMaster;
import com.pat.sms.user.dto.PriviledgeDto;


@Component
public class PriviledgeMapper {

	public static PriviledgeDto toPriviledgeDto(PrivilegeMaster priv) {
		
		return new PriviledgeDto().setName(priv.getName()).setPriviledgeId(priv.getPriviledgeId());
	}
}
