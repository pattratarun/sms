package com.pat.sms.security.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.pat.sms.user.service.UserService;

@Service
public class CustomLogoutHandler implements LogoutHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomLogoutHandler.class);
	
	@Autowired
	UserService userService;
	
	@Override
    public void logout(HttpServletRequest request, HttpServletResponse response, 
      Authentication authentication) {
    	LOGGER.debug ("logging out");
    	String logUid = (String) request.getSession().getAttribute("LOG_UID");
    	userService.logoutLog(logUid);
    }
}