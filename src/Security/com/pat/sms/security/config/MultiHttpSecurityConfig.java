package com.pat.sms.security.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pat.sms.security.filter.ApiJWTAuthenticationFilter;
import com.pat.sms.security.filter.ApiJWTAuthorizationFilter;
import com.pat.sms.security.form.CustomLogoutHandler;

/**
 * Created by Tarun Pattra.
 */
@EnableWebSecurity
public class MultiHttpSecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    	
    	Map<String,String> mp = new HashMap<>();
    	//private static final String ENTITY_STUDENT = "student";
    	//private static final String ENTITY_USER = "user";
    	
    	
    	@Autowired
    	CustomLogoutHandler customLoutoutHandler;
    	
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private CustomUserDetailsService userDetailsService;
        
        
        /*{
        	mp.put("user", "/api/v1/user/**");
        	mp.put("student", "/api/v1/student/**");
        }*/

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
        }
        
        

        // @formatter:off
		protected void configure(HttpSecurity http) throws Exception {
					http.csrf().disable().antMatcher("/api/**").authorizeRequests()
					.antMatchers("/api/**/admin/**").hasRole("ADMIN")
					/*.antMatchers(HttpMethod.POST, mp.get(ENTITY_STUDENT)).hasAuthority("student-" + HttpMethod.POST)
					.antMatchers(HttpMethod.GET, mp.get(ENTITY_STUDENT)).hasAuthority("student-" + HttpMethod.GET)
					.antMatchers(HttpMethod.PUT, mp.get(ENTITY_STUDENT)).hasAuthority("student-" + HttpMethod.PUT)
					.antMatchers(HttpMethod.DELETE, mp.get(ENTITY_STUDENT)).hasAuthority("student-" + HttpMethod.DELETE)*/
					/*.antMatchers(HttpMethod.POST, mp.get(ENTITY_USER)).hasAuthority("user-" + HttpMethod.POST)
					.antMatchers(HttpMethod.GET, mp.get(ENTITY_USER)).hasAuthority("user-" + HttpMethod.GET)
					.antMatchers(HttpMethod.PUT, mp.get(ENTITY_USER)).hasAuthority("user-" + HttpMethod.PUT)
					.antMatchers(HttpMethod.DELETE, mp.get(ENTITY_USER)).hasAuthority("user-" + HttpMethod.DELETE)*/
					.anyRequest().authenticated()
					.and().exceptionHandling()
					.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
					.addFilter(new ApiJWTAuthenticationFilter(authenticationManager()))
					.addFilter(new ApiJWTAuthorizationFilter(authenticationManager()))
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout","POST"))
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                    .addLogoutHandler(customLoutoutHandler)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                    .and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
        // @formatter:on

    }

    /*@Order(2)
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);
        }

        // @formatter:off
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/dashboard/**").hasAuthority("ADMIN")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(customAuthenticationSuccessHandler)
                    .and()
                    .logout()
                    .permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/")
                    .and()
                    .exceptionHandling();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(
                    "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**",
                    "/resources/static/**", "/css/**", "/js/**", "/img/**", "/fonts/**",
                    "/images/**", "/scss/**", "/vendor/**", "/favicon.ico", "/auth/**", "/favicon.png",
                    "/v2/api-docs", "/configuration/ui", "/configuration/security",
                    "/webjars/**", "/swagger-resources/**", "/actuator", "/swagger-ui/**",
                    "/actuator/**", "/swagger-ui/index.html", "/swagger-ui/");
        }
        // @formatter:on
    }*/
    
}
