package com.pat.sms.session.context;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pat.sms.user.dto.UserDto;

/**
 * 
 * @author tp185057
 *
 */
public class UserContext extends ThreadContext {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserContext.class);

	private static final String CURRENT_USER_INFO = "sms.user";

	/**
	 * This method returns the current user (com.ncr.user.dataobject.User) set in
	 * the session
	 * 
	 * @return
	 */
	public static UserDto getCurrentUser() {
		if (getCurrentUserDto() == null) {
			return null;
		}
		return getCurrentUserDto();
	}

	/**
	 * This method returns the current user information(com.ncr.wot.user.UserDto)
	 * set in the session
	 * 
	 * @return
	 */
	public static UserDto getCurrentUserDto() {
		if (getRequest().getSession() != null) {
			return (UserDto) getRequest().getSession().getAttribute(CURRENT_USER_INFO);
		}
		return null;
	}

	public static Locale getLocale() {
		return getRequest().getLocale();
	}

	/**
	 * invalidate the current session, keeping original user intact
	 */
	public static void invalidateSession() {
		HttpSession session = getRequest().getSession();
		session.invalidate();
	}

	/**
	 * 
	 * @param user
	 */
	public static void setCurrentUser(UserDto user) {
		try {
			if (getRequest().getSession() == null) {
				throw new Exception("Session is null");
			}
			getRequest().getSession().setAttribute(CURRENT_USER_INFO, user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
