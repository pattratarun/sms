package com.pat.sms.global.config;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pat.sms.dto.response.Response;
import com.pat.sms.session.context.UserContext;
import com.pat.sms.user.dto.UserDto;
import com.pat.sms.user.service.UserService;
import com.pat.sms.user.v1.reqresp.UserPasswordChangeRequest;
import com.pat.sms.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This is a global controller written merely for showing the login and logout apis in the
 * swagger documentation allowing users to get the authorisation token from the same interface
 * and use it for executing the secured API operations.
 * <p>
 * Created by Tarun Pattra.
 */
@RestController
@RequestMapping("/api")
@Api(value = "sms-application", tags="Login Controller")
@SuppressWarnings("rawtypes")
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@ApiOperation("Login")
	@PostMapping("/auth")
	public void myLogin(@RequestBody @Valid LoginRequest loginRequest) {
		throw new IllegalStateException(
				"This method shouldn't be called. It's implemented by Spring Security filters.");
	}

	@GetMapping("/current/user")
	@ApiOperation(value = "User Details", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> getUserDetails(HttpServletRequest req) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDto userResp = userService.findUserByUsername(auth.getName());
		LOGGER.debug("Login with username - {}", userResp.getUsername());
  		String logUidStr = userService.createLoginLog(userResp.getUsername());
  		req.getSession().setAttribute("LOG_UID", logUidStr);
  		UserContext.setCurrentUser(userResp);
		return new ResponseEntity<>(Response.ok().setPayload(userResp), HttpStatus.OK);
	}

	@PostMapping("/current/user/password")
	@ApiOperation(value = "Change password", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<Response> changePassword(@RequestBody UserPasswordChangeRequest passReq) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDto userDto = userService.findUserByUsername(auth.getName());
		if (!CommonUtil.isEmpty(userDto)) {
			if (bCryptPasswordEncoder.encode(passReq.getOldPassword()).equals(userDto.getPassword())) {
				userService.changePassword(userDto, passReq.getNewPassword());
			} else {
				return new ResponseEntity<>(Response.accessDenied().setErrors("Old password is not valid"),
						HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(Response.ok().setPayload("Password change successfully"), HttpStatus.OK);
	}

	@ApiOperation("Logout")
    @PostMapping("/logout")
	public void meLogout() {
		throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
	}

	@Getter
	@Setter
	@Accessors(chain = true)
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	private static class LoginRequest {
		@NotNull(message = "{constraints.NotEmpty.message}")
		private String username;
		@NotNull(message = "{constraints.NotEmpty.message}")
		private String password;
	}
}
