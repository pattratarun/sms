package com.pat.sms.session.context;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author tp185057
 *
 */
public class ThreadContext {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadContext.class);

    private static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();


    /**
     * This method would return the current HttpServletRequest to the calling
     * method.
     * 
     * @return returns an object of current HttpServletRequest
     * @see HttpServletRequest
     * @see ThreadLocal
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest req = request.get();
        if (req == null) {
        	LOGGER.error("No Request exists");
            throw new IllegalStateException("No Request exists");
        }
        return req;
    }

    /**
     * Returns the current session, if any
     * @return
     *//*
    private static HttpSession getSession() {
        HttpServletRequest req = request.get();
        if (req == null) {
            return null;
        }
        return req.getSession();
    }*/

    /**
     * The method returns the time offset of the current user in minutes [from
     * GMT]
     * 
     * @return long The offset number of minutes
     */
    public static long getUserOffset() {
        Long lOffset = (Long) getRequest().getSession().getAttribute("lOffset");
        if (lOffset == null) {
            String offset = (String) getRequest().getSession().getAttribute(
                    "Offset");
            lOffset = (long) (new Integer(offset == null ? "0" : offset)) * 60000;
            getRequest().getSession().setAttribute("lOffset", lOffset);
        }
        return lOffset;

    }

    /**
     * this method makes the current request unavailable once the request has
     * been serviced and response has been committed.
     * 
     * @see HttpServletRequest
     * @see ThreadLocal
     */
    public static void requestCompleted() {
        if (request.get() != null) {
            request.set(null);
        }

    }

    /**
     * This method sets the current HttpServletRequest object into ThreadLocal
     * object.
     * 
     * @param newRequest
     *            is the refrence of the current request.
     * @see HttpServletRequest
     * @see ThreadLocal
     */
    public static void setRequest(HttpServletRequest newRequest) {
        if (request.get() == null) {
            request.set(newRequest);
        }
    }
}
