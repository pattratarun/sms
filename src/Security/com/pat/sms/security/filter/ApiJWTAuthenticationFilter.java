package com.pat.sms.security.filter;

import static com.pat.sms.security.config.SecurityConstants.EXPIRATION_TIME;
import static com.pat.sms.security.config.SecurityConstants.HEADER_STRING;
import static com.pat.sms.security.config.SecurityConstants.SECRET;
import static com.pat.sms.security.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pat.sms.model.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by Tarun Pattra.
 */
public class ApiJWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
    private final AuthenticationManager authenticationManager;

    public ApiJWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {
        try {
            User user = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		if (auth.getPrincipal() != null) {
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) auth
					.getPrincipal();
			String login = user.getUsername();
			if (login != null && login.length() > 0) {
				Claims claims = Jwts.claims().setSubject(login);
				List<String> roles = new ArrayList<>();
				user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));
				claims.put("roles", roles);
				String token = Jwts.builder().setClaims(claims)
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, SECRET).compact();
				res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
			}
		}
	}
    
    
}
