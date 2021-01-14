package com.pat.sms.security.config;

/**
 * Created by Tarun Pattra.
 */
public interface SecurityConstants {
    String SECRET = "SecretKeyPattra";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String SIGN_UP_URL = "/users/sign-up";
    long EXPIRATION_TIME =  43200000; //12 hours  //864_000_000; // 10 days
}
